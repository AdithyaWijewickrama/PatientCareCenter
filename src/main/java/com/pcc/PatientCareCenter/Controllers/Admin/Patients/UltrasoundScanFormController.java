package com.pcc.PatientCareCenter.Controllers.Admin.Patients;

import com.pcc.PatientCareCenter.Views.Components.DCConnection.*;
import com.pcc.PatientCareCenter.Views.Components.JRXMLPrinter.PccJRXmlLoader;
import com.pcc.PatientCareCenter.Views.GlobalsViews;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import net.sf.jasperreports.engine.JRException;

import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;

public class UltrasoundScanFormController implements Initializable {
    public TextField name;
    public ComboBox<String> hospital;
    public ComboBox<String> scanType;
    public TextArea description;
    public Button printButton;
    DataComponentConnection[] connections;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scanType.getItems().addAll(
                "Abdominal",
                "Pelvic",
                "Obstetric",
                "Transvaginal",
                "Transrectal",
                "Breast",
                "Thyroid",
                "Carotid",
                "Echocardiogram (Cardiac)",
                "Doppler",
                "Musculoskeletal",
                "Renal",
                "Bladder",
                "Prostate",
                "Scrotal",
                "Ophthalmic",
                "Vascular",
                "Fetal",
                "Hip (for infants)",
                "Endoscopic (EUS)"
        );
        hospital.getItems().addAll("Matale","Kandy");
        connections = new DataComponentConnection[]{
                new StringTextfieldConnection(name),
                new ValueComboBoxConnection<>(hospital),
                new ValueComboBoxConnection<>(scanType),
                new StringTextAreaConnection(description)
        };
        printButton.setOnAction(event -> {
            try {
                print();
            } catch (JRException | SQLException e) {
                GlobalsViews.showErrorAlert(e.getLocalizedMessage());
                throw new RuntimeException(e);
            }
        });
    }

    public void print() throws JRException, SQLException {
        PccJRXmlLoader jrXmlLoader = new PccJRXmlLoader("UltrasoundScanForm");
        jrXmlLoader.addParams(GlobalsViews.getLetterHead());
        jrXmlLoader.printWithDCConnection(
                Arrays.asList(
                        "otherDoctorName",
                        "hospital",
                        "scanType",
                        "description"
                ),
                connections);
    }
}
