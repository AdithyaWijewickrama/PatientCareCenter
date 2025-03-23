package com.pcc.PatientCareCenter.Controllers.Admin.Patients;

import com.pcc.PatientCareCenter.Database.User.Patient;
import com.pcc.PatientCareCenter.Database.User.PatientContactDetails;
import com.pcc.PatientCareCenter.Views.Components.DCConnection.*;
import com.pcc.PatientCareCenter.Views.Components.JRXMLPrinter.PccJRXmlLoader;
import com.pcc.PatientCareCenter.Views.GlobalsViews;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import net.sf.jasperreports.engine.JRException;

import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;

public class MedicalCertificateController implements Initializable {

    public TextField name;
    public Spinner<Integer> age;
    public ToggleGroup genderGroup;
    public TextField nic;
    public TextField placeOfWork;
    public TextArea designation;
    public TextArea diagnosis;
    public DatePicker leavingDate;
    public Spinner<Integer> numberOfDaysForLeave;
    public TextField address;
    DataComponentConnection[] connections;
    public Button printButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connections = new DataComponentConnection[]{
                new StringTextfieldConnection(name),
                new IntegerSpinnerConnection(age, 0, 200, 18, 5),
                new ValueButtonGroupConnection(genderGroup),
                new StringTextfieldConnection(nic),
                new StringTextfieldConnection(address),
                new StringTextfieldConnection(placeOfWork),
                new StringTextAreaConnection(designation),
                new StringTextAreaConnection(diagnosis),
                new DateDatePickerConnection(leavingDate),
                new IntegerSpinnerConnection(numberOfDaysForLeave, 1, 200, 7, 5),
        };
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
        if (patient == null) {
            name.setText("");
            age.getValueFactory().setValue(0);
            address.setText("");
            return;
        }
        name.setText(patient.getName());
        age.getValueFactory().setValue(patient.getAgeInYears());
        address.setText(PatientContactDetails.getCurrentPatientsContactDetails().getAddress());
        new ValueButtonGroupConnection(genderGroup).setData(patient.getGender());
    }

    public void print() throws JRException, SQLException {
        PccJRXmlLoader jrXmlLoader = new PccJRXmlLoader("MedicalCertificate");
        jrXmlLoader.addParams(GlobalsViews.getLetterHead());
        jrXmlLoader.printWithDCConnection(
                Arrays.asList(
                        "patientName",
                        "patientAge",
                        "patientGender",
                        "patientNic",
                        "patientAddress",
                        "workPlace",
                        "patientDesignation",
                        "patientDiagnosis",
                        "fromDate",
                        "daysForLeave"
                ),
                connections);
    }
}
