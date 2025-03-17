package com.pcc.PatientCareCenter.Controllers.LoginOrSignup;

import com.pcc.PatientCareCenter.Model.Model;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LoginOrSignupController {

    @FXML
    public void showSignup() {
        Model.getInstance().getCommonViewFactory().getLoginOrSignupViewFactory().showSignupPane();
    }

    @FXML
    public void showLogin() {
        Model.getInstance().getCommonViewFactory().getLoginOrSignupViewFactory().showLoginPane();
    }
    @FXML
    public void showDoctorDetails() {
        Model.getInstance().getCommonViewFactory().getLoginOrSignupViewFactory().showDoctorDetails();
    }

    @FXML
    public void showForgotPassword() {
        Model.getInstance().getCommonViewFactory().getLoginOrSignupViewFactory().showForgotPasswordPane();
    }

    public void setLoginLink(Label label){
        label.setOnMouseClicked(event -> showLogin());
    }
    public void setSignupLink(Label label){
        label.setOnMouseClicked(event -> {
            showSignup();
            System.out.println("Signup");
        });
    }
    public void setForgotPasswordLink(Label label){
        label.setOnMouseClicked(event -> showForgotPassword());
    }

}
