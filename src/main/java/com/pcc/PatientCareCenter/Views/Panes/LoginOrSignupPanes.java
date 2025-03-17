package com.pcc.PatientCareCenter.Views.Panes;

import com.pcc.PatientCareCenter.Controllers.LoginOrSignup.ForgotPasswordController;
import com.pcc.PatientCareCenter.Controllers.LoginOrSignup.LoginController;
import com.pcc.PatientCareCenter.Controllers.LoginOrSignup.SignupController;
import com.pcc.PatientCareCenter.Controllers.LoginOrSignup.VerifyEmailController;
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

    public synchronized Parent getBadgePane() {
        if (badgePane == null) {
            badgePane = GlobalsViews.loadFxml(FXML_PATH + "/Badge.fxml");
        }
        return badgePane;
    }

    public Parent getVerifyEmailPane() {
        if (verifyEmailPane == null) {
            verifyEmailPane = GlobalsViews.loadFxml(FXML_PATH + "/VerifyEmail.fxml",new VerifyEmailController());
        }
        return verifyEmailPane;
    }

    public synchronized Parent getForgotPasswordPane() {
        if (forgotPasswordPane == null) {
            return GlobalsViews.loadFxml(FXML_PATH + "/ForgotPassword.fxml",new ForgotPasswordController());
        }
        return forgotPasswordPane;
    }

    public synchronized Parent getLoginPane() {
        if (loginPane == null) {
            loginPane = GlobalsViews.loadFxml(FXML_PATH + "/Login.fxml",new LoginController());
        }
        return loginPane;
    }

    public synchronized Parent getSignupPane() {
        if (signupPane == null) {
            signupPane = GlobalsViews.loadFxml(FXML_PATH + "/Signup.fxml",new SignupController());
        }
        return signupPane;
    }

    public synchronized Parent getMainStage() {
        if (mainStage == null) {
            mainStage = GlobalsViews.loadFxml(FXML_PATH + "/MainStage.fxml");
        }
        return mainStage;
    }
    public synchronized Parent getDoctorDetails() {
        if (doctorDetails == null) {
            doctorDetails = GlobalsViews.loadFxml(FXML_PATH + "/DoctorDetails.fxml");
        }
        return doctorDetails;
    }

    public synchronized static LoginOrSignupPanes getInstance() {
        if(instance==null){
            instance=new LoginOrSignupPanes();
        }
        return instance;
    }
}
