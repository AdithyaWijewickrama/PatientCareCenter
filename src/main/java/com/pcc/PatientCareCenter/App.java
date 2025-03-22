package com.pcc.PatientCareCenter;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.pcc.PatientCareCenter.Database.Server.DatabaseConfigManager;
import com.pcc.PatientCareCenter.Model.Model;
import com.pcc.PatientCareCenter.Model.PasswordEncryptor;
import com.pcc.PatientCareCenter.Model.Sql;
import com.pcc.PatientCareCenter.Views.DatabaseConfigDialog;
import com.pcc.PatientCareCenter.Views.GlobalsViews;
import javafx.application.Application;
import javafx.stage.Stage;

import javax.swing.*;
import java.sql.SQLException;
import java.util.Map;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
        Map<String, String> map = DatabaseConfigManager.readConfig();
        try {
            if (map != null) {
                String url = map.get("url");
                String user = map.get("username");
                String password = PasswordEncryptor.decrypt(map.get("password"));
                Sql sql = new Sql(url, user, password);
                try {
                    sql.connect();
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                    boolean b = GlobalsViews.showConfirmationAlert("Can not connected to local database server yet\nYou can connect to the server by inserting URL,Username & Password");
                    if (b) {
                        DatabaseConfigDialog configDialog = new DatabaseConfigDialog();
                        configDialog.start(new Stage());
                    }
                }
            } else {
                boolean b = GlobalsViews.showConfirmationAlert("You haven't connected to local database server yet\nYou can connect to the server by inserting URL,Username & Password");
                if (b) {
                    DatabaseConfigDialog configDialog = new DatabaseConfigDialog();
                    configDialog.start(new Stage());
                }
            }
            Model.getInstance().getCommonViewFactory().getLoginOrSignupViewFactory().showLoginOrSignupWindow();
        } catch (Exception e) {
            GlobalsViews.showErrorAlert(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
