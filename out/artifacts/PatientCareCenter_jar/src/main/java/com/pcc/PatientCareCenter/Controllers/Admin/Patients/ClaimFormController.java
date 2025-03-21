package com.pcc.PatientCareCenter.Controllers.Admin.Patients;

import com.pcc.PatientCareCenter.Database.User.Patient;
import com.pcc.PatientCareCenter.Views.Components.DCConnection.*;
import com.pcc.PatientCareCenter.Views.Components.JRXMLPrinter.PccJRXmlLoader;
import com.pcc.PatientCareCenter.Views.GlobalsViews;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import net.sf.jasperreports.engine.JRException;

import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ClaimFormController implements Initializable {
    public TextField name;
    public Spinner<Double> paidAmount;
    public Button printButton;
    DataComponentConnection[] connections;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connections = new DataComponentConnection[]{
                new StringTextfieldConnection(name),
                new DoubleSpinnerConnection(paidAmount, 0, Double.MAX_VALUE, 1000, 100),};
        printButton.setOnAction(event -> {
            try {
                print();
            } catch (JRException | SQLException e) {
                GlobalsViews.showErrorAlert(e.getLocalizedMessage());
                throw new RuntimeException(e);
            }
        });
        try {
            loadForCurrentPerson();
        } catch (SQLException e) {
            GlobalsViews.showErrorAlert(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    public void loadForCurrentPerson() throws SQLException {
        Patient patient = Patient.getCurrentPatient();
        if(patient==null){
            name.setText("");
            return;
        }
        name.setText(patient.getName());
    }

    public void print() throws JRException, SQLException {
        PccJRXmlLoader jrXmlLoader = new PccJRXmlLoader("ClaimForm");
        jrXmlLoader.addParams(GlobalsViews.getLetterHead());
        jrXmlLoader.printWithDCConnection(
                Arrays.asList(
                        "name",
                        "paidAmount"
                ),
                connections);
    }
}
