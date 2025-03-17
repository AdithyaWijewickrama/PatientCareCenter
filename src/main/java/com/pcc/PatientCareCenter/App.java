package com.pcc.PatientCareCenter;

import com.pcc.PatientCareCenter.Model.Model;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        Model.getInstance().getCommonViewFactory().getLoginOrSignupViewFactory().showLoginOrSignupWindow();
//        new PccTable("/Components/Table.fxml").showOnWindow();
    }

}
