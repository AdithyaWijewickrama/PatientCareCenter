package com.pcc.PatientCareCenter;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.pcc.PatientCareCenter.Database.Server.DatabaseConfigManager;
import com.pcc.PatientCareCenter.Model.Model;
import com.pcc.PatientCareCenter.Model.PasswordEncryptor;
import com.pcc.PatientCareCenter.Model.Sql;
import com.pcc.PatientCareCenter.Views.DatabaseConfigDialog;
import javafx.application.Application;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.sql.SQLException;
import java.util.Map;

public class App extends Application {

    private static final Logger log = LoggerFactory.getLogger(App.class);

    @Override
    public void start(Stage primaryStage) {
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
        Map<String, String> map = DatabaseConfigManager.readConfig();
        try {
            String url = map.get("url");
            String user = map.get("username");
            String password = PasswordEncryptor.decrypt(map.get("password"));
            Sql sql = new Sql(url, user, password);
            try {
                sql.connect();
            } catch (SQLException e) {
                log.error("e: ", e);
                DatabaseConfigDialog configDialog=new DatabaseConfigDialog();
                configDialog.start(new Stage());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Model.getInstance().getCommonViewFactory().getLoginOrSignupViewFactory().showLoginOrSignupWindow();
//        new PccTable("/Components/Table.fxml").showOnWindow();
    }


}
