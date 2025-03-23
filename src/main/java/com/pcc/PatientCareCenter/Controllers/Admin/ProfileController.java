package com.pcc.PatientCareCenter.Controllers.Admin;

import com.pcc.PatientCareCenter.Controllers.AdminControllers;
import com.pcc.PatientCareCenter.Database.User.Admin.Doctor;
import com.pcc.PatientCareCenter.Views.Components.DCConnection.*;
import com.pcc.PatientCareCenter.Views.GlobalsViews;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {
    public TextField name;
    public TextField occupation;
    public Button saveButton;
    ResultConnection resultConnection;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DataComponentConnection[] connections = new DataComponentConnection[]{
                new StringTextfieldConnection(name),
                new StringTextfieldConnection(occupation)
        };
        resultConnection = new ResultConnection(connections);
        saveButton.setOnAction(event -> {
            try {
                update();
                AdminControllers.getPatientsController().updateFrame();
                GlobalsViews.showInformationAlert("Updated successfully!");
            } catch (SQLException e) {
                GlobalsViews.showErrorAlert(e.getLocalizedMessage());
                throw new RuntimeException(e);
            }
        });
        try {
            load();
        } catch (SQLException e) {
            GlobalsViews.showErrorAlert(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    public void load() throws SQLException {
        resultConnection.setSelect(new SQLQuery("SELECT name,occupation FROM doctor WHERE doctor_id=?", QueryReturnType.ROW, new Object[]{Doctor.getCurrentDoctor().getDoctorId()}));
        resultConnection.loadDataFromDatabase();
    }

    public void update() throws SQLException {
        resultConnection.setUpdate(new SQLQuery("UPDATE doctor SET name=?,occupation=? WHERE doctor_id=?", QueryReturnType.ROW, new Object[]{Doctor.getCurrentDoctor().getDoctorId()}));
        resultConnection.updateToDataBase();
    }
}
