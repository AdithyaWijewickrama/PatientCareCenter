package com.ppcc.PatientCareCenter.Controllers.LoginOrSignup;

import com.ppcc.PatientCareCenter.Database.User.Admin.Admin;
import com.ppcc.PatientCareCenter.Database.User.User;
import com.ppcc.PatientCareCenter.Views.Components.TextFieldElements;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class DoctorDetailsController extends LoginOrSignupController implements Initializable {

    public TextField doctorDetailsName;
    public TextField doctorDetailsOccupation;
    public Label doctorDetailsMessage;
    public Button doctorDetailsButton;
    public Label loginLink;

    public void createAccount() {
        try {
            User.setCurrentUser(User.createUser(SignupController.getSignupEmail(), SignupController.getSignupPassword(), SignupController.getAccountType()));
            Admin adminAccount = Admin.createAdminAccount(User.getCurrentUser(), doctorDetailsButton.getText(), doctorDetailsOccupation.getText());
            Admin.setCurrentAdmin(adminAccount);
            showLogin();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new TextFieldElements(doctorDetailsName, doctorDetailsButton, loginLink, "Name").setRequired().setAlphabetic();
        new TextFieldElements(doctorDetailsOccupation, doctorDetailsButton, loginLink, "Occupation").setRequired().setAlphabetic();
        setLoginLink(loginLink);
        doctorDetailsButton.setOnAction(event -> createAccount());
    }
}
