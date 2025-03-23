package com.pcc.PatientCareCenter.Controllers.LoginOrSignup;

import com.pcc.PatientCareCenter.Database.User.UserType;
import com.pcc.PatientCareCenter.Model.Model;
import com.pcc.PatientCareCenter.Views.Components.MessageType;
import com.pcc.PatientCareCenter.Views.Components.PccMessage;
import com.pcc.PatientCareCenter.Views.Panes.LoginOrSignupPanes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class SignupController extends LoginOrSignupController implements Initializable {
    @FXML
    public Label loginLink;
    public Label signupMessage;
    public TextField signupEmail;
    public PasswordField signupPassword;
    public TextField signupConfirmPassword;
    public Button signupButton;
    public ComboBox<String> accountType;
    //FXML

    @FXML
    public boolean signup() {
        String email = signupEmail.getText();
        String userType = accountType.getValue();
        System.out.println(userType);
        if (!isValidEmail(email)) {
            PccMessage.showMessage(signupMessage, "Enter a valid email address", MessageType.MESSAGE_TYPE_ERROR);
        } else if (confirmPassword() == null) {
            PccMessage.showMessage(signupMessage, "Enter password and confirm", MessageType.MESSAGE_TYPE_ERROR);
        } else if (!confirmPassword()) {
            PccMessage.showMessage(signupMessage, "Passwords does not match", MessageType.MESSAGE_TYPE_ERROR);
        } else if (!isValidPassword()) {
            PccMessage.showMessage(signupMessage, "Password is not valid", MessageType.MESSAGE_TYPE_ERROR);
        } else if (userType == null) {
            PccMessage.showMessage(signupMessage, "Select account type", MessageType.MESSAGE_TYPE_ERROR);
        } else {
            try {
                showDoctorDetails();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    public String getSignupEmail() {
        return signupEmail.getText();
    }

    public String getSignupPassword() {
        return signupPassword.getText();
    }

    public UserType getAccountType() {
        return UserType.getUserTypeByName(
                accountType.getValue()
        );
    }

    public boolean isValidEmail(String email) {
        return email.matches("^(?:[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:\\\\[\\x00-\\x7F]|[^\\\\\"])*\")@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,}$");
    }

    public boolean isValidPassword() {
        return Model.getInstance().getPasswordValidatorForSignup().isValid(signupPassword.getText());
    }

    public Boolean confirmPassword() {
        String password = signupPassword.getText();
        String confirmPassword = signupConfirmPassword.getText();
        if (password.isEmpty() || confirmPassword.isEmpty()) return null;
        return password.equals(confirmPassword);
    }

    //FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> comboList = FXCollections.observableArrayList("Doctor", "Patient");
        accountType.setItems(comboList);
        signupButton.setOnAction((event) -> signup());
        loginLink.setOnMouseClicked((event -> showLogin()));
    }
}
