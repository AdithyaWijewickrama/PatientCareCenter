package com.pcc.PatientCareCenter.Views.Panes;

import com.pcc.PatientCareCenter.Controllers.LoginOrSignupControllers;
import com.pcc.PatientCareCenter.Views.GlobalsViews;
import javafx.scene.Parent;

public class LoginOrSignupPanes extends PaneViewFactory {
    private final String FXML_PATH = GlobalsViews.FXML_PATH + "/LoginOrSignup";

    private Parent loginPane;
    private Parent mainStage;
    private Parent badgePane;
    private Parent signupPane;
    private Parent doctorDetails;
    private Parent forgotPasswordPane;
    private Parent verifyEmailPane;
    private static LoginOrSignupPanes instance;

    public LoginOrSignupPanes(){}

    public Parent getBadgePane() {
        if (badgePane == null) {
            badgePane = GlobalsViews.loadFxml(FXML_PATH + "/Badge.fxml");
        }
        return badgePane;
    }

    public Parent getVerifyEmailPane() {
        if (verifyEmailPane == null) {
            verifyEmailPane = GlobalsViews.loadFxml(FXML_PATH + "/VerifyEmail.fxml", LoginOrSignupControllers.VERIFY_EMAIL_CONTROLLER);
        }
        return verifyEmailPane;
    }

    public Parent getForgotPasswordPane() {
        if (forgotPasswordPane == null) {
            return GlobalsViews.loadFxml(FXML_PATH + "/ForgotPassword.fxml", LoginOrSignupControllers.FORGOT_PASSWORD_CONTROLLER);
        }
        return forgotPasswordPane;
    }

    public Parent getLoginPane() {
        if (loginPane == null) {
            loginPane = GlobalsViews.loadFxml(FXML_PATH + "/Login.fxml", LoginOrSignupControllers.LOGIN_CONTROLLER);
        }
        return loginPane;
    }

    public Parent getSignupPane() {
        if (signupPane == null) {
            signupPane = GlobalsViews.loadFxml(FXML_PATH + "/Signup.fxml", LoginOrSignupControllers.SIGNUP_CONTROLLER);
        }
        return signupPane;
    }

    public Parent getMainStage() {
        if (mainStage == null) {
            mainStage = GlobalsViews.loadFxml(FXML_PATH + "/MainStage.fxml");
        }
        return mainStage;
    }
    public Parent getDoctorDetails() {
        if (doctorDetails == null) {
            doctorDetails = GlobalsViews.loadFxml(FXML_PATH + "/DoctorDetails.fxml");
        }
        return doctorDetails;
    }

    public static LoginOrSignupPanes getInstance() {
        if(instance==null){
            instance=new LoginOrSignupPanes();
        }
        return instance;
    }
}
