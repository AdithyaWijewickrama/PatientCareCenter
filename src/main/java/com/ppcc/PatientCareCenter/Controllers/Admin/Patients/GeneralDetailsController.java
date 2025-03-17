package com.ppcc.PatientCareCenter.Controllers.Admin.Patients;

import com.ppcc.PatientCareCenter.Views.Components.DCConnection.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

public class GeneralDetailsController implements Initializable {
    public TextField name;
    public DatePicker dateOfBirth;
    public RadioButton male;
    public RadioButton female;
    public RadioButton other;
    public ToggleGroup genderGroup;
    public RadioButton unmarried;
    public RadioButton married;
    public RadioButton divorced;
    public ToggleGroup maritalStatusGroup;
    public ComboBox<String> nationality;
    public ComboBox<String> languagePreference;
    public TextField streetAddress;
    public TextField mobileNumber;
    public TextField whatsappNumber;
    public TextField lanNumber;
    public ComboBox<String> country;
    public ComboBox<String> city;
    public ComboBox<String> province;
    public TextField postalCode;
    public Button saveButton;


    ResultConnection resultConnection;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeData();
        province.setOnAction(e -> updateCityComboBox());
        province.setValue("Central Province");
        city.setValue("Matale");
        DataComponentConnection[] connections = {
                new StringTextfieldConnection(name),
                new DateDatePickerConnection(dateOfBirth),
                new ValueButtonGroupConnection(genderGroup),
                new ValueButtonGroupConnection(maritalStatusGroup),
                new ValueComboBoxConnection<>(nationality),
                new ValueComboBoxConnection<>(languagePreference),
                new StringTextfieldConnection(mobileNumber),
                new StringTextfieldConnection(whatsappNumber),
                new StringTextfieldConnection(lanNumber),
                new StringTextfieldConnection(streetAddress),
                new ValueComboBoxConnection<>(country),
                new ValueComboBoxConnection<>(province),
                new ValueComboBoxConnection<>(city),
                new StringTextfieldConnection(postalCode),
        };
        resultConnection = new ResultConnection(
                """
                        patient_demographics pd
                        JOIN
                        user_contact_details ucd
                        ON pd.user_id=ucd.user_id
                        """,
                new String[]{
                        "pd.name",
                        "pd.date_of_birth",
                        "pd.gender",
                        "pd.marital_status",
                        "pd.nationality",
                        "pd.language_preference",
                        "ucd.mobile_numer",
                        "ucd.lan_number",
                        "ucd.whatsapp_number",
                        "ucd.street_address",
                        "ucd.city",
                        "ucd.province",
                        "ucd.postal_code",
                        "ucd.country"
                },
                "pd.user_id=?",
                null,
                connections
        );
    }

    public void

    HashMap<String, ObservableList<String>> citiesByProvince;

    private void initializeData() {
        citiesByProvince = new HashMap<>();
        citiesByProvince.put("Western Province", FXCollections.observableArrayList("Colombo", "Gampaha", "Kalutara"));
        citiesByProvince.put("Central Province", FXCollections.observableArrayList("Kandy", "Nuwara Eliya", "Matale"));
        citiesByProvince.put("Southern Province", FXCollections.observableArrayList("Galle", "Matara", "Hambantota"));
        citiesByProvince.put("Northern Province", FXCollections.observableArrayList("Jaffna", "Vavuniya", "Kilinochchi", "Mullaitivu", "Mannar"));
        citiesByProvince.put("Eastern Province", FXCollections.observableArrayList("Trincomalee", "Batticaloa", "Ampara"));
        citiesByProvince.put("North Western Province", FXCollections.observableArrayList("Kurunegala", "Puttalam"));
        citiesByProvince.put("North Central Province", FXCollections.observableArrayList("Anuradhapura", "Polonnaruwa"));
        citiesByProvince.put("Uva Province", FXCollections.observableArrayList("Badulla", "Monaragala"));
        citiesByProvince.put("Sabaragamuwa Province", FXCollections.observableArrayList("Ratnapura", "Kegalle"));
    }

    private void updateCityComboBox() {
        String selectedProvince = province.getValue();
        city.getItems().clear();
        if (selectedProvince != null && citiesByProvince.containsKey(selectedProvince)) {
            city.setItems(citiesByProvince.get(selectedProvince));
        }
    }
}