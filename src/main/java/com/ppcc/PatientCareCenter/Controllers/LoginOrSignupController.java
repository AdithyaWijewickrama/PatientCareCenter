package com.ppcc.PatientCareCenter.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.*;

public class LoginOrSignupController {
    @FXML
    public Label signupLink;
    public Label signupMessage;
    public Label forgotPasswordMessage;
    public VBox forgotPasswordPane;
    public VBox loginPane;
    public VBox signupPane;
    public StackPane stackPane;
    public TextField forgotPasswordEmail;
    public TextField loginEmail;
    public TextField loginPassword;
    public TextField signupEmail;
    public TextField signupPassword;
    public TextField signupConfirmPassword;
    public Button forgotPasswordButton;
    public ComboBox accountType;

    @FXML
    public void showSignup() {
        setVisibleChild(stackPane,signupPane);
    }

    @FXML
    public void showLogin() {
        setVisibleChild(stackPane,loginPane);
    }
    @FXML
    public void showForgotPassword() {
        setVisibleChild(stackPane,forgotPasswordPane);
    }

    @FXML
    public void login(){

    }

    public void sendCode(ActionEvent actionEvent) {

    }

    public static void setVisibleChild(StackPane parent, Pane showPane){
        parent.getChildren().forEach(node -> {
            node.setVisible(node==showPane);
        });
    }
}
