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

public class CertificateOfFitnessController implements Initializable {
    public TextField name;
    public Spinner<Integer> age;
    public RadioButton male;
    public ToggleGroup genderGroup;
    public RadioButton female;
    public RadioButton other;
    public TextField nic;
    public TextField address;
    public Spinner<Double> weight;
    public Spinner<Double> height;
    public Spinner<Double> bmi;
    public TextField bloodPressure;
    public Spinner<Integer> pulseRate;
    public TextArea examinationOfHeart;
    public TextArea respiratorySystem;
    public TextArea centralNervousSystem;
    public TextArea limbs;
    public TextArea urineExamination;
    public TextArea ecg;
    public TextArea chestXRay;
    public DatePicker examinedDate;
    public ToggleGroup physicalAbilityGroup;
    public Button printButton;
    DataComponentConnection[] connections;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connections = new DataComponentConnection[]{
                new StringTextfieldConnection(name),
                new IntegerSpinnerConnection(age, 0, 200, 18, 5),
                new ValueButtonGroupConnection(genderGroup),
                new StringTextfieldConnection(nic),
                new StringTextfieldConnection(address),
                new DoubleSpinnerConnection(weight, 0.5, 500, 50, 5),
                new DoubleSpinnerConnection(height, 0.1, 3, 1.5, 0.05),
                new DoubleSpinnerConnection(bmi, 1, 50, 1, 1),
                new StringTextfieldConnection(bloodPressure),
                new IntegerSpinnerConnection(pulseRate, 0, 200, 60, 5),
                new StringTextAreaConnection(examinationOfHeart),
                new StringTextAreaConnection(respiratorySystem),
                new StringTextAreaConnection(centralNervousSystem),
                new StringTextAreaConnection(limbs),
                new StringTextAreaConnection(urineExamination),
                new StringTextAreaConnection(ecg),
                new StringTextAreaConnection(chestXRay),
                new DateDatePickerConnection(examinedDate),
                new ValueButtonGroupConnection(physicalAbilityGroup),
        };
        weight.valueProperty().addListener(event -> {
            setBmi();
        });
        height.valueProperty().addListener(event -> {
            setBmi();
        });
        bmi.setEditable(false);
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

    public void setBmi() {
        try {
            this.bmi.getValueFactory().setValue(getBmi(weight.getValue(), height.getValue()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Double getBmi(double weight, double height) {
        return weight / Math.pow(height, 2.);
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
        PccJRXmlLoader jrXmlLoader = new PccJRXmlLoader("CertificateOfFitness");
        jrXmlLoader.addParams(GlobalsViews.getLetterHead());
        jrXmlLoader.printWithDCConnection(
                Arrays.asList(
                        "patientName",
                        "patientAge",
                        "patientGender",
                        "patientNic",
                        "patientAddress",
                        "weight",
                        "height",
                        "bmi",
                        "bloodPressure",
                        "pulseRate",
                        "examinationOfHeart",
                        "respiratorySystem",
                        "centralNervousSystem",
                        "limbs",
                        "urineExamination",
                        "ecg",
                        "chestXRay",
                        "examinedDate",
                        "physicalAbility"
                ),
                connections);
    }
}
