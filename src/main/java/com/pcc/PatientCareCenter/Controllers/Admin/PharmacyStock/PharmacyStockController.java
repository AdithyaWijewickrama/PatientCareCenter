package com.pcc.PatientCareCenter.Controllers.Admin.PharmacyStock;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

import java.net.URL;
import java.util.ResourceBundle;

public class PharmacyStockController implements Initializable {
    public Label pharmacyStock;
    public TextField searchTextField;
    public ToggleButton writePrescriptionButton;
    public FontAwesomeIconView addMedicineButton;
    public ToggleButton writePrescriptionButton1;
    public TableView tableView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
