package com.pcc.PatientCareCenter.Controllers.Admin.Patients;

import com.pcc.PatientCareCenter.Database.User.Patient;
import com.pcc.PatientCareCenter.Views.Components.DCConnection.*;
import com.pcc.PatientCareCenter.Views.GlobalsViews;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

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
                        resultConnection.clear();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
            case INSERT -> {
                saveButton.setText("Insert");
                prepareToInsert();
                setAction((action) -> {
                    try {
                        resultConnection.insertToDataBase();
                        GlobalsViews.showInformationAlert("Inserted successfully!");
                        setGeneralDetailsType(GeneralDetailsType.UPDATE);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }
    }

    /*
    * """
                            SELECT pd.name,pd.date_of_birth,pd.gender,pd.marital_status,pd.nationality,pd.language_preference,ucd.mobile_number,ucd.whatsapp_number,ucd.lan_number,ucd.street_address,ucd.country,ucd.province,ucd.city,ucd.postal_code FROM patient_demographics pd
                            JOIN
                            user_contact_details ucd
                            ON pd.user_id=ucd.user_id
                             WHERE pd.user_id=%d""", Patient.getCurrentPatient().getPatientId()),
                    String.format("""
                            BEGIN TRANSACTION;
                            INSERT INTO patient_demographics pd
                                (name,
                                date_of_birth,
                                gender,
                                marital_status,
                                nationality,
                                language_preference)
                                VALUES (?,?,?,?,?,?)
                            RETURNING pd.user_id;
                            INSERT INTO user_contact_details ucd
                                (mobile_number,
                                whatsapp_number,
                                lan_number,
                                street_address,
                                country,
                                province,
                                city,
                                postal_code)
                                VALUES(?,?,?,?,?,?,?,?);
                            COMMIT;
                            """),
                    String.format("""
                            WITH updated_patient AS (
                                UPDATE patient_demographics pd
                                SET
                                    name = ?,
                                    date_of_birth = ?,
                                    gender = ?,
                                    marital_status = ?,
                                    nationality = ?,
                                    language_preference = ?
                                FROM user_contact_details ucd
                                WHERE pd.user_id = ucd.user_id
                                  AND pd.user_id = %d
                                RETURNING pd.user_id
                            )
                            UPDATE user_contact_details ucd
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
                            WHERE ucd.user_id = up.user_id;
                            """, Patient.getCurrentPatient().getPatientId()),
                    String.format("""

                            """)
    *
    * */
    public void setAction(EventHandler<ActionEvent> eventHandler) {
        saveButton.setOnAction(eventHandler);
    }

    public void prepareToInsert() {
        resultConnection.clear();
        resultConnection.setInsert(new SQLQuery("""
                BEGIN TRANSACTION;
                INSERT INTO patient_demographics pd
                    (name,
                    date_of_birth,
                    gender,
                    marital_status,
                    nationality,
                    language_preference)
                    VALUES (?,?,?,?,?,?)
                RETURNING pd.user_id;
                INSERT INTO user_contact_details ucd
                    (mobile_number,
                    whatsapp_number,
                    lan_number,
                    street_address,
                    country,
                    province,
                    city,
                    postal_code)
                    VALUES(?,?,?,?,?,?,?,?);
                COMMIT;
                """,QueryReturnType.ROW));
    }

    public void loadDataForCurrentPatient() {
        if (Patient.getCurrentPatient() == null) {
            return;
        }
        try {
            resultConnection.setSelect(new SQLQuery(String.format("""
                    SELECT pd.name,pd.date_of_birth,pd.gender,pd.marital_status,pd.nationality,pd.language_preference,ucd.mobile_number,ucd.whatsapp_number,ucd.lan_number,ucd.street_address,ucd.country,ucd.province,ucd.city,ucd.postal_code FROM patient_demographics pd
                    JOIN
                    user_contact_details ucd
                    ON pd.user_id=ucd.user_id
                     WHERE pd.user_id=%d""", Patient.getCurrentPatient().getPatientId()),QueryReturnType.ROW));
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
                        FROM user_contact_details ucd
                        WHERE pd.user_id = ucd.user_id
                          AND pd.user_id = %d
                        RETURNING pd.user_id
                    )
                    UPDATE user_contact_details ucd
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
                    WHERE ucd.user_id = up.user_id;
                    """, Patient.getCurrentPatient().getPatientId()),QueryReturnType.NONE));
            resultConnection.loadDataFromDatabase();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

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