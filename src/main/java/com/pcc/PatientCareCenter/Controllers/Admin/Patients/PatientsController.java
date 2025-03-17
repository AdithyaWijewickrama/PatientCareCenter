package com.pcc.PatientCareCenter.Controllers.Admin.Patients;

import com.pcc.PatientCareCenter.Database.User.Admin.Admin;
import com.pcc.PatientCareCenter.Database.User.Patient;
import com.pcc.PatientCareCenter.Model.Model;
import com.pcc.PatientCareCenter.Model.Sql;
import com.pcc.PatientCareCenter.Views.Components.PccTable.ButtonElements;
import com.pcc.PatientCareCenter.Views.Components.PccTable.DynamicTableRow;
import com.pcc.PatientCareCenter.Views.Components.PccTable.PatientsButtonCell;
import com.pcc.PatientCareCenter.Views.Components.PccTable.PccTable;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class PatientsController implements Initializable {
    public TableView<DynamicTableRow> patientsTable;
    public ComboBox<String> statusComboBox;
    public TextField searchTextField;
    public Label doctorName;
    public Label doctorOccupation;
    public Label userAddress;
    public Label userName;
    public Label userId;
    public Text appName;
    public ImageView userBarCode;

    private PccTable pccTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        doctorName.setText(Admin.getCurrentAdmin().getName());
        doctorOccupation.setText(Admin.getCurrentAdmin().getOccupation());
        pccTable = new PccTable(patientsTable);
        try {
            tableLoad();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        patientsTable.setOnMouseClicked(this::patientSelected);

//        patientsTable.getSelectionModel().getTableView().set
    }

    private Patient selectedPatient;

    private void tableLoad() throws SQLException {
        try (ResultSet resultSet = Sql.getInstance().executeQuery("""
                SELECT\s
                    pd.patient_id AS 'Patient Id',\s
                    pd.name AS Name,\s
                    u.email AS Email
                FROM\s
                    doctors_of_patients dop
                JOIN\s
                    patient_demographics pd ON dop.patient_id = pd.patient_id
                JOIN\s
                    user u ON pd.user_id = u.user_id
                WHERE\s
                    dop.doctor_id = ?\s
                    AND u.status = 'Active';
                """, Admin.getCurrentAdmin().getAdminId())) {
            List<TableColumn<DynamicTableRow, ?>> columns = PccTable.getColumns(resultSet);
            pccTable.setTableColumns(columns);
            pccTable.addTableColumn(PccTable.getNodeColumn("Action", cell -> new PatientsButtonCell(getButtonSet())));
            pccTable.resultSetToPccTable(resultSet);
        }


    }

    private Button[] getButtonSet() {
        String iconSize = "20";
        Button editButton = new Button();
        Button deleteButton = new Button();
        Button viewButton = new Button();
        editButton.setOnAction(event -> {
            Patient.setCurrentPatient(selectedPatient);
            Model.getInstance().getCommonViewFactory().getAdminViewFactory().showGeneralDetails();
        });
        ButtonElements.bindIconFillProperty(editButton, "edit-button", new FontAwesomeIconView(FontAwesomeIcon.EDIT, iconSize));
        ButtonElements.bindIconFillProperty(deleteButton, "delete-button", new FontAwesomeIconView(FontAwesomeIcon.TRASH, iconSize));
        ButtonElements.bindIconFillProperty(viewButton, "view-button", new FontAwesomeIconView(FontAwesomeIcon.EYE, iconSize));
        return new Button[]{viewButton, editButton, deleteButton};
    }

    public void patientSelected(Event event) {
        int patientId = (int) patientsTable.getSelectionModel().getSelectedItem().getData("Patient Id");
        try {
            selectedPatient = new Patient(patientId);
            userName.setText(selectedPatient.getData().getString("name"));
            this.userId.setText(String.valueOf(patientId));
            userAddress.setText(selectedPatient.getData().getString("gender"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
