package com.pcc.PatientCareCenter.Controllers.LoginOrSignup;

import com.pcc.PatientCareCenter.Controllers.LoginOrSignupControllers;
import com.pcc.PatientCareCenter.Database.PPDetails;
import com.pcc.PatientCareCenter.Database.User.Admin.Doctor;
import com.pcc.PatientCareCenter.Database.User.User;
import com.pcc.PatientCareCenter.Views.Components.TextFieldElements;
import com.pcc.PatientCareCenter.Views.GlobalsViews;
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
            User.setCurrentUser(User.createUser(LoginOrSignupControllers.getSignupController().getSignupEmail(), LoginOrSignupControllers.getSignupController().getSignupPassword(), LoginOrSignupControllers.getSignupController().getAccountType()));
            Doctor doctorAccount = Doctor.createDoctorAccount(User.getCurrentUser(), doctorDetailsName.getText(), doctorDetailsOccupation.getText());
            PPDetails.setCurrentPP(PPDetails.createPPpDetails(doctorAccount.getName().toUpperCase() + " PATIENT CARE CENTER", null, User.getCurrentUser().getEmail(), null, doctorAccount.getDoctorId()));
            Doctor.setCurrentDoctor(doctorAccount);
            showLogin();
        } catch (Exception e) {
            GlobalsViews.showErrorAlert(e.getLocalizedMessage());
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
