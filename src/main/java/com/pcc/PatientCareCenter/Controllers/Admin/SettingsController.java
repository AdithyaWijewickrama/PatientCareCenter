package com.pcc.PatientCareCenter.Controllers.Admin;

import com.pcc.PatientCareCenter.Database.User.Admin.Doctor;
import com.pcc.PatientCareCenter.Database.User.User;
import com.pcc.PatientCareCenter.Model.Model;
import com.pcc.PatientCareCenter.Views.Components.DCConnection.*;
import com.pcc.PatientCareCenter.Views.GlobalsViews;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {
    public TextField email;
    public PasswordField password;
    public PasswordField newPassword;
    public PasswordField confirmNewPassword;
    public Button saveUserDetailsButton;
    public PasswordField ppName;
    public PasswordField ppAddress;
    public PasswordField ppEmail;
    public PasswordField ppTelephone;
    public Button ppSaveButton;
    DataComponentConnection[] connections1;
    ResultConnection resultConnection1;
    ResultConnection resultConnection2;
    DataComponentConnection[] connections2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connections1 = new DataComponentConnection[]{
                new StringTextfieldConnection(email),
                new StringTextfieldConnection(password),
                new StringTextfieldConnection(newPassword),
        };
        resultConnection1 = new ResultConnection(connections1);
        resultConnection1.setSelect(new SQLQuery("SELECT email,password FROM user WHERE user_id=?", QueryReturnType.ROW, new Object[]{User.getCurrentUser().getUserId()}));
        saveUserDetailsButton.setOnAction(event -> {
            try {
                updateUserDetails();
            } catch (SQLException e) {
                GlobalsViews.showErrorAlert(e.getLocalizedMessage());
                throw new RuntimeException(e);
            }
        });
        connections2 = new DataComponentConnection[]{
                new StringTextfieldConnection(ppName),
                new StringTextfieldConnection(ppAddress),
                new StringTextfieldConnection(ppEmail),
                new StringTextfieldConnection(ppTelephone),
        };
        resultConnection2= new ResultConnection(connections2);
        resultConnection2.setSelect(new SQLQuery("SELECT name, address, email, telephone FROM public.pp_details WHERE ;", QueryReturnType.ROW, new Object[]{Doctor.getCurrentDoctor().getDoctorId()}));
        ppSaveButton.setOnAction(event -> {
            try {
                updateUserDetails();
            } catch (SQLException e) {
                GlobalsViews.showErrorAlert(e.getLocalizedMessage());
                throw new RuntimeException(e);
            }
        });
    }

    public void updateUserDetails() throws SQLException {
        if (newPassword.getText().isEmpty() && confirmNewPassword.getText().isEmpty()) {
            resultConnection1.setUpdate(new SQLQuery("UPDATE user SET email=? WHERE user_id=?", QueryReturnType.ROW, new Object[]{email.getText(), User.getCurrentUser().getUserId()}));
        } else if (confirmPassword()) {
            resultConnection1.setUpdate(new SQLQuery("UPDATE user SET email=?,password=? WHERE user_id=?", QueryReturnType.ROW, new Object[]{email.getText(), password.getText(), User.getCurrentUser().getUserId()}));
        }
        resultConnection1.updateToDataBase();
        resultConnection1.loadDataFromDatabase();
        newPassword.setText("");
        confirmNewPassword.setText("");
    }

    public boolean confirmPassword() {
        return newPassword.getText().equals(confirmNewPassword.getText()) && Model.getInstance().getPasswordValidatorForSignup().isValid(newPassword.getText());
    }
}
