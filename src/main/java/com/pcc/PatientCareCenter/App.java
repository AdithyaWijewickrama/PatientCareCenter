package com.pcc.PatientCareCenter;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.pcc.PatientCareCenter.Database.Server.DatabaseConfigManager;
import com.pcc.PatientCareCenter.Database.Server.RunSQLFile;
import com.pcc.PatientCareCenter.Model.Model;
import com.pcc.PatientCareCenter.Model.PasswordEncryptor;
import com.pcc.PatientCareCenter.Model.Sql;
import com.pcc.PatientCareCenter.Views.DatabaseConfigDialog;
import com.pcc.PatientCareCenter.Views.GlobalsViews;
import javafx.application.Application;
import javafx.stage.Stage;

import javax.swing.*;
import java.util.Map;

public class App extends Application {
    Map<String, String> map;
    Sql sql;
    public static final String DB_NAME = "pcc_db";
    public static final String DB_PASSWORD = "PccPassword123";
    public static final String DB_USERNAME = "pcc";

    @Override
    public void start(Stage primaryStage) {
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
        System.out.println(createPccLocalDataBase());
        readConfigToSqlInstance();
        try {
            if (map != null) {
                try {
                    tryStartingApp();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                    boolean b = GlobalsViews.showConfirmationAlert("Can not connected to local database server yet\nYou can connect to the server by inserting URL,Username & Password\nAnd make sure you have postgresql installed in your local system");
                    if (b) {
                        DatabaseConfigDialog configDialog = new DatabaseConfigDialog();
                        configDialog.start(new Stage());
                        readConfigToSqlInstance();
                        tryStartingApp();
                    }
                    throw new RuntimeException(e);
                }
            } else {
                boolean b = GlobalsViews.showConfirmationAlert("You haven't connected to local database server yet\nYou can connect to the server by inserting URL,Username & Password");
                if (b) {
                    DatabaseConfigDialog configDialog = new DatabaseConfigDialog();
                    configDialog.start(new Stage());
                    readConfigToSqlInstance();
                    tryStartingApp();
                }
            }
        } catch (Exception e) {
            GlobalsViews.showErrorAlert(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private boolean createPccLocalDataBase() {
        try {
            Sql defaultInstance = Sql.getDefaultInstance();
            defaultInstance.execute(String.format("""
                    CREATE DATABASE %s;
                    """, DB_NAME));
            defaultInstance.execute(String.format("""
                    CREATE ROLE %s WITH
                        LOGIN
                        PASSWORD '%s'
                        CREATEDB
                        CREATEROLE
                        INHERIT
                        SUPERUSER
                        REPLICATION
                        BYPASSRLS;
                    """, DB_USERNAME, DB_PASSWORD));
            try {
                DatabaseConfigManager.writeConfig("jdbc:postgresql://localhost:5432/" + DB_NAME, DB_USERNAME, PasswordEncryptor.encrypt(DB_PASSWORD));
                readConfigToSqlInstance();
                sql.connect();
                sql.execute(String.format("""
                        GRANT ALL PRIVILEGES ON DATABASE %s TO pcc;
                        
                        GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO pcc;
                        
                        GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO pcc;
                        
                        GRANT ALL PRIVILEGES ON ALL FUNCTIONS IN SCHEMA public TO pcc;
                        
                        GRANT USAGE ON SCHEMA public TO pcc;
                        
                        ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL PRIVILEGES ON TABLES TO pcc;
                        
                        ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL PRIVILEGES ON SEQUENCES TO pcc;
                        
                        ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL PRIVILEGES ON FUNCTIONS TO pcc;
                        
                        GRANT CREATE ON DATABASE %s TO pcc;
                        """.replaceAll("TO pcc", "TO " + DB_USERNAME), DB_NAME, DB_NAME));
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        } catch (Exception e) {
            if(e.getMessage().contains(String.format("database \"%s\" already exists",DB_NAME))){
                return true;
            }
            throw new RuntimeException(e);
        }
        return true;
    }

    private void tryStartingApp() throws Exception {
        readConfigToSqlInstance();
        if (sql == null) throw new Exception("Database connection failed!");
        sql.connect();
        if(!sql.getUrl().equals("jdbc:postgresql://localhost:5432/" + DB_NAME))
            RunSQLFile.runSQLFile(sql.getConnection());
        startApp();
    }

    private void startApp() {
        Model.getInstance().getCommonViewFactory().getLoginOrSignupViewFactory().showLoginOrSignupWindow();
    }

    private void readConfigToSqlInstance() {
        map = DatabaseConfigManager.readConfig();
        try {
            if (map == null) return;
            String url = map.get("url");
            String user = map.get("username");
            String password = PasswordEncryptor.decrypt(map.get("password"));
            sql = new Sql(url, user, password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

