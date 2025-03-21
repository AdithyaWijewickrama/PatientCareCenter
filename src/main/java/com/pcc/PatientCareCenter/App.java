package com.pcc.PatientCareCenter;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.pcc.PatientCareCenter.Model.Model;
import javafx.application.Application;
import javafx.stage.Stage;

import javax.swing.*;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
        Model.getInstance().getCommonViewFactory().getLoginOrSignupViewFactory().showLoginOrSignupWindow();
//        new PccTable("/Components/Table.fxml").showOnWindow();
    }

}
