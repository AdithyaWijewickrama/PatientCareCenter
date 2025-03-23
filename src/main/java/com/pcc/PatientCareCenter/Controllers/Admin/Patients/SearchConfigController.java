package com.pcc.PatientCareCenter.Controllers.Admin.Patients;

import com.pcc.PatientCareCenter.Controllers.AdminControllers;
import com.pcc.PatientCareCenter.Views.Components.DCConnection.*;
import com.pcc.PatientCareCenter.Views.GlobalsViews;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class SearchConfigController implements Initializable {
    public CheckBox id;
    public CheckBox name;
    public CheckBox dateOfBirth;
    public CheckBox mobileNumber;
    public CheckBox whatsappNumber;
    public CheckBox address;
    public CheckBox gender;
    public CheckBox maritalStatus;
    public CheckBox nationality;
    public CheckBox languagePreference;
    public CheckBox city;
    public Button applyButton;
    DataComponentConnection[] connections;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connections = new DataComponentConnection[]{
                new BooleanCheckboxConnection(id),
                new BooleanCheckboxConnection(name),
                new BooleanCheckboxConnection(dateOfBirth),
                new BooleanCheckboxConnection(mobileNumber),
                new BooleanCheckboxConnection(whatsappNumber),
                new BooleanCheckboxConnection(address),
                new BooleanCheckboxConnection(gender),
                new BooleanCheckboxConnection(maritalStatus),
                new BooleanCheckboxConnection(nationality),
                new BooleanCheckboxConnection(languagePreference),
                new BooleanCheckboxConnection(city),
        };
        ResultConnection resultConnection = getResultConnection();
        applyButton.setOnAction(event -> {
            try {
                resultConnection.updateToDataBase();
                AdminControllers.getPatientsController().tableLoad();
            } catch (SQLException e) {
                GlobalsViews.showErrorAlert(e.getLocalizedMessage());
                throw new RuntimeException(e);
            }
        });
    }

    public void load() throws SQLException {
        getResultConnection().loadDataFromDatabase();
    }

    private ResultConnection getResultConnection() {
        ResultConnection resultConnection = new ResultConnection(connections);
        resultConnection.setUpdate(new SQLQuery("""
                INSERT INTO search_config (id, value) VALUES
                    ('id', ?),
                    ('name' ,?),
                    ('dateOfBirth', ?),
                    ('mobileNumber', ?),
                    ('whatsappNumber', ?),
                    ('address', ?),
                    ('gender', ?),
                    ('maritalStatus', ?),
                    ('nationality', ?),
                    ('languagePreference', ?),
                    ('city', ?)
                ON CONFLICT (id) DO UPDATE SET value = EXCLUDED.value;
                """, QueryReturnType.NONE));
        resultConnection.setSelect(new SQLQuery("""
                SELECT value FROM search_config
                """, QueryReturnType.COLUMN));
        return resultConnection;
    }

    public List<Boolean> getValues() throws SQLException {
        return getResultConnection().getList(getResultConnection().getSelect()).stream().map(obj -> (Boolean) obj).collect(Collectors.toList());
    }

    public List<String> getColumns() {
        List<Boolean> values;
        try {
            values = getValues();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        List<String> columns = Arrays.asList(
                "pd.patient_id::TEXT",
                "pd.name",
                "pd.date_of_birth::TEXT",
                "pcd.mobile_number",
                "pcd.whatsapp_number",
                "pcd.street_address",
                "pd.gender",
                "pd.marital_status",
                "pd.nationality",
                "pd.language_preference",
                "pcd.city");
        List<Boolean> finalValues = values;
        return columns.stream().filter(s -> finalValues.get(columns.indexOf(s))).toList();
    }

    public List<String> getColumnsWithNaming() {
        List<Boolean> values;
        try {
            values = getValues();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        List<String> columns = Arrays.asList(
                "pd.patient_id AS \"Patient Id\"",
                "pd.name AS \"Name\"",
                "pd.date_of_birth AS \"Date of birth\"",
                "pcd.mobile_number AS \"Mobile Number\"",
                "pcd.whatsapp_number AS \"Whatsapp Number\"",
                "pcd.street_address AS \"Address\"",
                "pd.gender AS \"Gender\"",
                "pd.marital_status AS \"Marital status\"",
                "pd.nationality AS \"Nationality\"",
                "pd.language_preference AS \"Language preference\"",
                "pcd.city AS \"City\"");
        List<Boolean> finalValues = values;
        System.out.println(columns);
        System.out.println(values);
        return columns.stream().filter(s -> finalValues.get(columns.indexOf(s))).toList();
    }
}
