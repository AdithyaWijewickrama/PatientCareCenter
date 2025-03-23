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
import java.util.Objects;

public class App extends Application {
    Map<String, String> map;
    Sql sql;

    @Override
    public void start(Stage primaryStage) {
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
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

    private void tryStartingApp() throws Exception {
            readConfigToSqlInstance();
            if(sql==null)throw new Exception("Database connection failed!");
            sql.connect();
            RunSQLFile.runSQLFile(sql.getConnection(), Objects.requireNonNull(App.class.getResource("/com/pcc/PatientCareCenter/Database/Servers/pccserver.sql")).getFile());
            startApp();
    }

    private void startApp() {
        Model.getInstance().getCommonViewFactory().getLoginOrSignupViewFactory().showLoginOrSignupWindow();
    }

    private void readConfigToSqlInstance() {
        map = DatabaseConfigManager.readConfig();
        try {
            if(map==null)return;
            String url = map.get("url");
            String user = map.get("username");
            String password = PasswordEncryptor.decrypt(map.get("password"));
            sql = new Sql(url, user, password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

