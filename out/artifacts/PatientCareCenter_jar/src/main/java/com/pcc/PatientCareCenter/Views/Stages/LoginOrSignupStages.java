package com.pcc.PatientCareCenter.Views.Stages;

import com.pcc.PatientCareCenter.Views.GlobalsViews;
import com.pcc.PatientCareCenter.Views.Panes.LoginOrSignupPanes;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class LoginOrSignupStages extends StageViewFactory {

    private BorderPane mainStage;
    private StringProperty currentPane = new SimpleStringProperty("Login");

    public LoginOrSignupStages(StringProperty stageTitle, Image stageImage) {
        this.stageTitle = stageTitle;
        this.stageImage = stageImage;
        this.mainStage = (BorderPane) LoginOrSignupPanes.getInstance().getMainStage();
        mainStage.setLeft(LoginOrSignupPanes.getInstance().getBadgePane());
        mainStage.setCenter(LoginOrSignupPanes.getInstance().getLoginPane());
        this.currentPane.addListener(event -> {
            switch (currentPane.getValue()) {
                case "Login" -> mainStage.setCenter(LoginOrSignupPanes.getInstance().getLoginPane());
                case "Signup" -> mainStage.setCenter(LoginOrSignupPanes.getInstance().getSignupPane());
                case "ForgotPassword" -> mainStage.setCenter(LoginOrSignupPanes.getInstance().getForgotPasswordPane());
                case "VerifyEmail" -> mainStage.setCenter(LoginOrSignupPanes.getInstance().getVerifyEmailPane());
                case "DoctorDetails" -> mainStage.setCenter(LoginOrSignupPanes.getInstance().getDoctorDetails());
            }
            System.out.println(currentPane.getValue());
        });
        setCurrentPane("Login");
    }

    public void setCurrentPane(String pane) {
        currentPane.setValue(pane);
    }

    public LoginOrSignupStages() {
        this(new SimpleStringProperty(GlobalsViews.APP_NAME), GlobalsViews.APP_ICON);
    }

    private Stage window;

    public Stage getLoginOrSignupWindow() {
        if (window == null) {
            window = createStage(mainStage);
        }
        return window;
    }
}
