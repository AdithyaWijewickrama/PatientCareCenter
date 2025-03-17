package com.ppcc.PatientCareCenter.Controllers.Admin;

import com.ppcc.PatientCareCenter.Database.User.Admin.Admin;
import com.ppcc.PatientCareCenter.Database.User.PatientDemographics;
import com.ppcc.PatientCareCenter.Database.User.User;
import com.ppcc.PatientCareCenter.Model.Sql;
import com.ppcc.PatientCareCenter.Views.Components.PccTable.ButtonElements;
import com.ppcc.PatientCareCenter.Views.Components.PccTable.DynamicTableRow;
import com.ppcc.PatientCareCenter.Views.Components.PccTable.PatientsButtonCell;
import com.ppcc.PatientCareCenter.Views.Components.PccTable.PccTable;
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

    private User user;
    private PatientDemographics pdemo;

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
        ButtonElements.bindIconFillProperty(editButton, "edit-button", new FontAwesomeIconView(FontAwesomeIcon.EDIT, iconSize));
        ButtonElements.bindIconFillProperty(deleteButton, "delete-button", new FontAwesomeIconView(FontAwesomeIcon.TRASH, iconSize));
        ButtonElements.bindIconFillProperty(viewButton, "view-button", new FontAwesomeIconView(FontAwesomeIcon.EYE, iconSize));
        return new Button[]{viewButton, editButton, deleteButton};
    }

    public void patientSelected(Event event) {
        int userId = (int) patientsTable.getSelectionModel().getSelectedItem().getData("user_id");
        try {
            user = User.getUser(userId);
            pdemo = new PatientDemographics(PatientDemographics.getPatientId(user.getUserId()));
            userName.setText(pdemo.getData().getString("name"));
            this.userId.setText(String.valueOf(userId));
//            userAddress.setText(pdemo.getData().getString("address"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
