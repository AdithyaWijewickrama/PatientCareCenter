package com.pcc.PatientCareCenter.Controllers.LoginOrSignup;

import com.pcc.PatientCareCenter.Database.PPDetails;
import com.pcc.PatientCareCenter.Database.User.Admin.Doctor;
import com.pcc.PatientCareCenter.Database.User.User;
import com.pcc.PatientCareCenter.Model.Model;
import com.pcc.PatientCareCenter.Views.Components.MessageType;
import com.pcc.PatientCareCenter.Views.Components.PccMessage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController extends LoginOrSignupController implements Initializable {
    @FXML
    public Button loginButton;
    public TextField loginEmail;
    public TextField loginPassword;
    public Label loginMessage;
    public Label signupLink;

    @FXML
    public void login() {
        String email = loginEmail.getText();
        String password = loginPassword.getText();
        System.out.println(email + "\t" + password);
        if (password.isEmpty() || email.isEmpty()) {
            PccMessage.showMessage(loginMessage, "Enter your email and password!", MessageType.MESSAGE_TYPE_INFO);
        } else {
            try {
                User user = User.getUser(email);
                if (user != null) {
                    Doctor.setCurrentAdmin(Doctor.getAdmin(user.getUserId()));
                    PPDetails.setCurrentPP(PPDetails.getPpDetailsOfDoctor(Doctor.getCurrentDoctor().getDoctorId()));
                    User.setCurrentUser(user);
                    enterApplication(user);
                } else {
                    PccMessage.showMessage(loginMessage, "Email or password invalid!", MessageType.MESSAGE_TYPE_ERROR);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void enterApplication(User user) {
        switch (user.getUserType()) {
            case DOCTOR -> {
                Model.getInstance().getCommonViewFactory().getLoginOrSignupViewFactory().getLoginOrSignup().getLoginOrSignupWindow().close();
                Model.getInstance().getCommonViewFactory().getAdminViewFactory().showAdminWindow();
            }
            case PATIENT -> System.out.println("Patient login successful!");
//                    Model.getInstance().getCommonViewFactory().getClientViewFactory();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginButton.setOnAction((event) -> login());
        setSignupLink(signupLink);
    }
}
