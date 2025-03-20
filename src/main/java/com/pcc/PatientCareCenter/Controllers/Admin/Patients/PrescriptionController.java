package com.pcc.PatientCareCenter.Controllers.Admin.Patients;

import com.google.zxing.qrcode.decoder.Mode;
import com.pcc.PatientCareCenter.Model.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class PrescriptionController implements Initializable {

    public TextField name;
    public TextArea description;
    public ListView descriptionList;
    public Button addMedicine;
    public TextArea descriptionOther;
    public Button printAndSend;
    public Button print;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addMedicine.setOnAction(event -> {
            Model.getInstance().getCommonViewFactory().getAdminViewFactory().getAdmin().showMedicineSelector();
        });
    }
}
