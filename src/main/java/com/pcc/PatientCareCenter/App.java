package com.pcc.PatientCareCenter;

import com.pcc.PatientCareCenter.Database.Server.DatabaseConfigManager;
import com.pcc.PatientCareCenter.Model.Model;
import com.pcc.PatientCareCenter.Model.PasswordEncryptor;
import com.pcc.PatientCareCenter.Model.Sql;
import com.pcc.PatientCareCenter.Views.DatabaseConfigDialog;
import javafx.application.Application;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Map;

public class App extends Application {


    @Override
    public void start(Stage primaryStage) {
        Map<String, String> map = DatabaseConfigManager.readConfig();
        try {
            String url = map.get("url");
            String user = map.get("username");
            String password = PasswordEncryptor.decrypt(map.get("password"));
            Sql sql = new Sql(url, user, password);
            try {
                sql.connect();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
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
