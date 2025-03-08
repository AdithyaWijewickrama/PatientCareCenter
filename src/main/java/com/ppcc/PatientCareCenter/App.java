package com.ppcc.PatientCareCenter;

import com.ppcc.PatientCareCenter.Model.Model;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        Model.getInstance().getViewFactory().showLoginOrSignup();
    }

}
