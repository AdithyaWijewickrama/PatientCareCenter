package com.pcc.PatientCareCenter.Controllers.Admin;

import com.pcc.PatientCareCenter.Database.Defaults;
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
    public TextField ppName;
    public TextField ppAddress;
    public TextField ppEmail;
    public TextField ppTelephone;
    public TextField postgresUrl;
    public TextField postgresUsername;
    public PasswordField postgresPassword;
    public TextField webhook;
    public Button ppSaveButton;
    public Button saveUserDetailsButton;
    public Button postgresSaveButton;
    public Button webhookButton;
    DataComponentConnection[] connUserDetails;
    DataComponentConnection[] connPPDetails;
    ResultConnection rsUserDetails;
    ResultConnection rsPPDetails;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connUserDetails = new DataComponentConnection[]{
                new StringTextfieldConnection(email),
                new StringPasswordFieldConnection(password)
        };
        rsUserDetails = new ResultConnection(connUserDetails);
        rsUserDetails.setSelect(new SQLQuery("SELECT email,password FROM public.user WHERE user_id=?", QueryReturnType.ROW, new Object[]{User.getCurrentUser().getUserId()}));
        saveUserDetailsButton.setOnAction(event -> {
            try {
                updateUserDetails();
                GlobalsViews.showInformationAlert("Update successfully!");
            } catch (Exception e) {
                GlobalsViews.showErrorAlert(e.getLocalizedMessage());
                throw new RuntimeException(e);
            }
        });
        connPPDetails = new DataComponentConnection[]{
                new StringTextfieldConnection(ppName),
                new StringTextfieldConnection(ppAddress),
                new StringTextfieldConnection(ppEmail),
                new StringTextfieldConnection(ppTelephone)
        };
        rsPPDetails = new ResultConnection(connPPDetails);
        rsPPDetails.setSelect(new SQLQuery("SELECT name, address, email, telephone FROM pp_details WHERE doctor_id=?;", QueryReturnType.ROW, new Object[]{Doctor.getCurrentDoctor().getDoctorId()}));
        ppSaveButton.setOnAction(event -> {
            try {
                upDatePPDetails();
                GlobalsViews.showInformationAlert("Update successfully!");
            } catch (SQLException e) {
                GlobalsViews.showErrorAlert(e.getLocalizedMessage());
                throw new RuntimeException(e);
            }
        });
        postgresSaveButton.setOnAction(event -> {
            try {
                updateDb();
                GlobalsViews.showInformationAlert("Update successfully!");
            } catch (SQLException e) {
                GlobalsViews.showErrorAlert(e.getLocalizedMessage());
                throw new RuntimeException(e);
            }
        });
        webhookButton.setOnAction(event -> {
            try {
                updateWebhook();
                GlobalsViews.showInformationAlert("Update successfully!");
            } catch (SQLException e) {
                GlobalsViews.showErrorAlert(e.getLocalizedMessage());
                throw new RuntimeException(e);
            }
        });
        try {
            loadSettings();
        } catch (SQLException e) {
            GlobalsViews.showErrorAlert(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    public void loadDb() throws SQLException {
        postgresUrl.setText(Defaults.getDefault("DB_URL"));
        postgresUsername.setText(Defaults.getDefault("DB_USERNAME"));
        postgresPassword.setText(Defaults.getDefault("DB_PASSWORD"));
    }

    public void updateDb() throws SQLException {
        Defaults.setDefault("DB_URL", postgresUrl.getText());
        Defaults.setDefault("DB_USERNAME", postgresUsername.getText());
        Defaults.setDefault("DB_PASSWORD", postgresPassword.getText());
    }

    public void loadWebhook() throws SQLException {
        webhook.setText(Defaults.getDefault("WEBHOOK_URL"));
    }

    public void updateWebhook() throws SQLException {
        Defaults.setDefault("WEBHOOK_URL", webhook.getText());
    }

    public void loadSettings() throws SQLException {
        rsUserDetails.loadDataFromDatabase();
        rsPPDetails.loadDataFromDatabase();
        loadDb();
        loadWebhook();
        clearNewPassWordFields();
    }

    public void updateUserDetails() throws Exception {
        if (newPassword.getText().isEmpty() && confirmNewPassword.getText().isEmpty()) {
        } else if (!confirmPassword()) {
            throw new Exception("Passwords doesn't match or aren't valid password");
        }
        password.setText(newPassword.getText());
        rsUserDetails.setUpdate(new SQLQuery("UPDATE \"user\" SET email=?,password=? WHERE user_id=?", QueryReturnType.ROW, new Object[]{User.getCurrentUser().getUserId()}));
        rsUserDetails.updateToDataBase();
        rsUserDetails.loadDataFromDatabase();
        clearNewPassWordFields();
    }

    public void upDatePPDetails() throws SQLException {
        rsPPDetails.setSelect(new SQLQuery("SELECT name, address, email, telephone FROM public.pp_details WHERE doctor_id=?;", QueryReturnType.ROW, new Object[]{Doctor.getCurrentDoctor().getDoctorId()}));
        rsPPDetails.setUpdate(new SQLQuery("UPDATE pp_details SET name=?,address=?,email=?,telephone=? WHERE doctor_id=?", QueryReturnType.NONE, new Object[]{Doctor.getCurrentDoctor().getDoctorId()}));
        rsPPDetails.updateToDataBase();
        rsPPDetails.loadDataFromDatabase();
    }

    public boolean confirmPassword() {
        return newPassword.getText().equals(confirmNewPassword.getText()) && Model.getInstance().getPasswordValidatorForSignup().isValid(newPassword.getText());
    }

    public void clearNewPassWordFields() {
        newPassword.setText("");
        confirmNewPassword.setText("");
    }
}
