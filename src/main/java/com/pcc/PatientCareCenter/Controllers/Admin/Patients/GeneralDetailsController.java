package com.pcc.PatientCareCenter.Controllers.Admin.Patients;

import com.pcc.PatientCareCenter.Database.User.Admin.Doctor;
import com.pcc.PatientCareCenter.Database.User.Patient;
import com.pcc.PatientCareCenter.Model.Sql;
import com.pcc.PatientCareCenter.Views.Components.DCConnection.*;
import com.pcc.PatientCareCenter.Views.GlobalsViews;
import com.pcc.PatientCareCenter.Views.Panes.AdminPanes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javax.print.Doc;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
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

    private GeneralDetailsType generalDetailsType;

    ResultConnection resultConnection;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeData();
        province.setItems(FXCollections.observableArrayList(citiesByProvince.keySet()));
        country.setItems(FXCollections.observableArrayList("Sri lanka"));
        country.getSelectionModel().select("Sri lanka");
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
        resultConnection = new ResultConnection(connections);
        setGeneralDetailsType(GeneralDetailsType.UPDATE);
    }


    public void setGeneralDetailsType(GeneralDetailsType generalDetailsType) {
        this.generalDetailsType = generalDetailsType;
        resultConnection.clear();
        switch (generalDetailsType) {
            case UPDATE -> {
                loadDataForCurrentPatient();
                saveButton.setText("Update");
                setAction((action) -> {
                    try {
                        resultConnection.updateToDataBase();
                        GlobalsViews.showInformationAlert("Updated successfully!");
                        AdminPanes.getPatientsController().tableLoad();
                    } catch (SQLException e) {
                        GlobalsViews.showErrorAlert(e.getLocalizedMessage());
                        throw new RuntimeException(e);
                    }
                });
            }
            case INSERT -> {
                saveButton.setText("Insert");
                prepareToInsert();
                setAction((action) -> {
                    try {
                        int patientId = (int) resultConnection.insertToDataBase();
                        GlobalsViews.showInformationAlert("Inserted successfully!\nPatient id:\t"+patientId);
                        AdminPanes.getPatientsController().tableLoad();
                        Sql.getInstance().execute("""
                                INSERT INTO doctors_of_patients VALUES(?,?)
                                """, patientId, Doctor.getCurrentDoctor().getDoctorId());
                        setGeneralDetailsType(GeneralDetailsType.UPDATE);
                    } catch (SQLException e) {
                        GlobalsViews.showErrorAlert(e.getLocalizedMessage());
                        throw new RuntimeException(e);
                    }
                });
            }
            case DELETE -> {
                loadDataForCurrentPatient();
                saveButton.setText("Delete");
                setAction((action) -> {
                    try {
                        if (GlobalsViews.showWarningAlert("Are you sure want to delete!")) {
                            Patient.getCurrentPatient().deletePatient();
                            GlobalsViews.showInformationAlert("Deleted successfully!");
                            AdminPanes.getPatientsController().tableLoad();
                            setGeneralDetailsType(GeneralDetailsType.UPDATE);
                        }
                    } catch (SQLException e) {
                        GlobalsViews.showErrorAlert(e.getLocalizedMessage());
                        throw new RuntimeException(e);
                    }
                });
            }
        }
    }

    public void setAction(EventHandler<ActionEvent> eventHandler) {
        saveButton.setOnAction(eventHandler);
    }

    private void prepareToInsert() {
        resultConnection.clear();
        resultConnection.setInsert(new SQLQuery("""
               WITH inserted_patient AS (
                   INSERT INTO patient_demographics\s
                       (name, date_of_birth, gender, marital_status, nationality, language_preference)
                   VALUES\s
                       (?, ?, ?, ?, ?, ?)
                   RETURNING patient_id
               )
               INSERT INTO patient_contact_details\s
                   (patient_id, mobile_number, whatsapp_number, lan_number, street_address, country, province, city, postal_code)
               SELECT\s
                   patient_id, ?, ?, ?, ?, ?, ?, ?, ?
               FROM inserted_patient RETURNING patient_id;""", QueryReturnType.SINGLE_VALUE));
    }

    public void loadDataForCurrentPatient() {
        if (Patient.getCurrentPatient() == null) {
            return;
        }
        try {
            resultConnection.setSelect(new SQLQuery(String.format("""
                    SELECT
                        pd.name,
                        pd.date_of_birth,
                        pd.gender,pd.marital_status,
                        pd.nationality,
                        pd.language_preference,
                        pcd.mobile_number,
                        pcd.whatsapp_number,
                        pcd.lan_number,
                        pcd.street_address,
                        pcd.country,
                        pcd.province,
                        pcd.city,
                        pcd.postal_code
                    FROM patient_demographics pd
                    JOIN
                    patient_contact_details pcd
                    ON pd.patient_id=pcd.patient_id
                     WHERE pd.patient_id=%d""", Patient.getCurrentPatient().getPatientId()), QueryReturnType.ROW));
            resultConnection.setUpdate(new SQLQuery(String.format("""
                    WITH updated_patient AS (
                        UPDATE patient_demographics pd
                        SET
                            name = ?,
                            date_of_birth = ?,
                            gender = ?,
                            marital_status = ?,
                            nationality = ?,
                            language_preference = ?
                        FROM patient_contact_details pcd
                        WHERE pd.patient_id = pcd.patient_id
                          AND pd.patient_id = %d
                        RETURNING pd.patient_id
                    )
                    UPDATE patient_contact_details pcd
                    SET
                        mobile_number = ?,
                        whatsapp_number = ?,
                        lan_number = ?,
                        street_address = ?,
                        country = ?,
                        province = ?,
                        city = ?,
                        postal_code = ?
                    FROM updated_patient up
                    WHERE pcd.patient_id = up.patient_id;
                    """, Patient.getCurrentPatient().getPatientId()), QueryReturnType.NONE));
            resultConnection.loadDataFromDatabase();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    HashMap<String, ObservableList<String>> citiesByProvince;

    private void initializeData() {
        nationality.getItems().addAll("Sri Lankan", "Indian", "American", "British", "Canadian", "Australian", "Other");
        languagePreference.getItems().addAll("Sinhala", "English", "Tamil", "Other");
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