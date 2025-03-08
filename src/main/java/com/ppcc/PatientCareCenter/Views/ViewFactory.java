package com.ppcc.PatientCareCenter.Views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewFactory {
    public void showLoginOrSignup() {
//        System.out.println(ViewFactory.class.getResource("/com/ppcc/PatientCareCenter/Fxml/login-or-signup.fxml"));
        FXMLLoader fxmlFile = new FXMLLoader(ViewFactory.class.getResource("/com/ppcc/PatientCareCenter/Fxml/login-or-signup.fxml"));
        createStage(fxmlFile);
    }

    public void showClientWindow() {
        FXMLLoader fxmlFile = new FXMLLoader(getClass().getResource("Fxml/Client/client.fxml"));
        createStage(fxmlFile);
    }

    public void showAdminWindow() {
        FXMLLoader fxmlFile = new FXMLLoader(getClass().getResource("Fxml/Admin/admin.fxml"));
        createStage(fxmlFile);
    }

    public static void createStage(FXMLLoader fxmlFile) {
        Scene scene = null;
        try {
            scene = new Scene(fxmlFile.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
}
