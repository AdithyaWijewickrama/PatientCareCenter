package com.pcc.PatientCareCenter.Controllers.Admin.Patients;

import com.google.zxing.WriterException;
import com.pcc.PatientCareCenter.Database.User.Admin.Doctor;
import com.pcc.PatientCareCenter.Database.User.Patient;
import com.pcc.PatientCareCenter.Model.Model;
import com.pcc.PatientCareCenter.Model.Sql;
import com.pcc.PatientCareCenter.Views.Components.BarCode;
import com.pcc.PatientCareCenter.Views.Components.PccTable.ButtonElements;
import com.pcc.PatientCareCenter.Views.Components.PccTable.DynamicTableRow;
import com.pcc.PatientCareCenter.Views.Components.PccTable.PatientsButtonCell;
import com.pcc.PatientCareCenter.Views.Components.PccTable.PccTable;
import com.pcc.PatientCareCenter.Views.GlobalsViews;
import com.pcc.PatientCareCenter.Views.Panes.AdminPanes;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class PatientsController implements Initializable {
    public TableView<DynamicTableRow> patientsTable;
    public TextField searchTextField;
    public Label doctorName;
    public Label doctorOccupation;
    public Label userAddress;
    public Label userName;
    public Label userId;
    public Text appName;
    public ImageView userBarCode;
    public Button addPatientButton;
    public ToggleButton writePrescriptionButton;
    public Button medicalCertificateButton;
    public Button certificateOfFitnessButton;
    public Button claimReportButton;
    public Button searchConfigButton;
    public Button ultrasoundScanButton;

    private PccTable pccTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pccTable = new PccTable(patientsTable);
        searchTextField.textProperty().addListener(event -> {
            try {
                tableLoad(getTableQuery());
            } catch (SQLException e) {
                GlobalsViews.showErrorAlert(e.getLocalizedMessage());
                throw new RuntimeException(e);
            }
        });
        try {
            tableLoad(getTableQuery());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        patientsTable.getSelectionModel().selectedIndexProperty().addListener((e) -> {
            patientSelected();
        });
        addPatientButton.setOnAction(event -> {
            Model.getInstance().getCommonViewFactory().getAdminViewFactory().getAdmin().showGeneralDetails();
            AdminPanes.getGeneralDetailsController().setGeneralDetailsType(GeneralDetailsType.INSERT);

        });
        searchConfigButton.setOnAction(event -> {
            Model.getInstance().getCommonViewFactory().getAdminViewFactory().getAdmin().showSearchConfig();
            try {
                AdminPanes.getSearchConfigController().load();
            } catch (SQLException e) {
                GlobalsViews.showErrorAlert(e.getLocalizedMessage());
                throw new RuntimeException(e);
            }
        });
        medicalCertificateButton.setOnAction(event -> {
            Model.getInstance().getCommonViewFactory().getAdminViewFactory().getAdmin().showMedicalCertificate();
            try {
                AdminPanes.getMedicalCertificateController().loadForCurrentPerson();
            } catch (SQLException e) {
                GlobalsViews.showErrorAlert(e.getLocalizedMessage());
                throw new RuntimeException(e);
            }
        });
        certificateOfFitnessButton.setOnAction(event -> {
            Model.getInstance().getCommonViewFactory().getAdminViewFactory().getAdmin().showCertificateOfFitness();
            try {
                AdminPanes.getCertificateOfFitnessController().loadForCurrentPerson();
            } catch (SQLException e) {
                GlobalsViews.showErrorAlert(e.getLocalizedMessage());
                throw new RuntimeException(e);
            }
        });
        writePrescriptionButton.setOnAction(event -> {
            Model.getInstance().getCommonViewFactory().getAdminViewFactory().getAdmin().showPrescription();
            try {
                AdminPanes.getPrescriptionController().loadForCurrentPatient();
            } catch (Exception e) {
                GlobalsViews.showErrorAlert(e.getLocalizedMessage());
                throw new RuntimeException(e);
            }
        });
        claimReportButton.setOnAction(event -> {
            Model.getInstance().getCommonViewFactory().getAdminViewFactory().getAdmin().showClaimForm();
            try {
                AdminPanes.getClaimFormController().loadForCurrentPerson();
            } catch (SQLException e) {
                GlobalsViews.showErrorAlert(e.getLocalizedMessage());
                throw new RuntimeException(e);
            }
        });
        ultrasoundScanButton.setOnAction(event -> {
            Model.getInstance().getCommonViewFactory().getAdminViewFactory().getAdmin().showUltrasoundScanForm();
            AdminPanes.getUltrasoundScanController().load();
        });
        try {
            updateFrame();
        } catch (SQLException e) {
            GlobalsViews.showErrorAlert(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    public void updateFrame() throws SQLException {
        Doctor.getCurrentDoctor().load();
        doctorName.setText(Doctor.getCurrentDoctor().getName());
        doctorOccupation.setText(Doctor.getCurrentDoctor().getOccupation());
    }

    //amadawalage@gmail.com
    private Patient selectedPatient;

    public void tableLoad(ResultSet resultSet) throws SQLException {
        List<TableColumn<DynamicTableRow, ?>> columns = PccTable.getColumns(resultSet);
        pccTable.clear();
        pccTable.setTableColumns(columns);
        pccTable.addTableColumn(PccTable.getNodeColumn("Action", cell -> new PatientsButtonCell(getButtonSet())));
        pccTable.resultSetToPccTable(resultSet);
    }

    public void tableLoad(){
        try {
            tableLoad(getTableQuery());
            setSelectedPatient(null);
        } catch (SQLException e) {
            GlobalsViews.showErrorAlert(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    public void setSelectedPatient(Patient p){
        selectedPatient=p;
    }

    public Patient getSelectedPatient(){
        if(patientsTable.getSelectionModel().getSelectedItem()==null)return null;
        int patientId = (int) patientsTable.getSelectionModel().getSelectedItem().getData("Patient Id");
        try {
            return new Patient(patientId);
        } catch (SQLException e) {
            GlobalsViews.showErrorAlert(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    public ResultSet getTableQuery() throws SQLException {
        StringBuilder sql = new StringBuilder(String.format("""
                SELECT\s
                    %s
                FROM\s
                    public.doctors_of_patients dop
                JOIN\s
                    public.patient_demographics pd ON dop.patient_id = pd.patient_id
                JOIN\s
                    public.patient_contact_details pcd ON pcd.patient_id = pd.patient_id
                WHERE\s
                    dop.doctor_id = ?\s
                    AND pd.status = 'Active'
                """, String.join(",", getColumnsWithNaming())));
        System.out.println(sql);
        String searchString = searchTextField.getText();
        if (searchString.isEmpty())
            return Sql.getInstance().executeQuery(sql + ";", Doctor.getCurrentDoctor().getDoctorId());
        List<String> columns = getColumns();
        sql.append("AND (\n\t");
        for (String column : columns) {
            sql.append(column).append(" ILIKE ").append("'%").append(searchString).append("%'");
            if (columns.indexOf(column) < columns.size() - 1) {
                sql.append("\n\tOR ");
            } else {
                sql.append("\n);");
            }
        }
        sql.trimToSize();
        return Sql.getInstance().executeQuery(sql.toString(), Doctor.getCurrentDoctor().getDoctorId());
    }

    private static List<String> getColumns() {
        return AdminPanes.getSearchConfigController().getColumns();
    }

    private static List<String> getColumnsWithNaming() {
        return AdminPanes.getSearchConfigController().getColumnsWithNaming();
    }

    private Button[] getButtonSet() {
        String iconSize = "20";
        Button editButton = new Button();
        Button deleteButton = new Button();
        Button viewButton = new Button();
        editButton.setOnAction(event -> {
            Patient.setCurrentPatient(selectedPatient);
            Model.getInstance().getCommonViewFactory().getAdminViewFactory().showGeneralDetails();
            AdminPanes.getGeneralDetailsController().setGeneralDetailsType(GeneralDetailsType.UPDATE);
        });
        deleteButton.setOnAction(event -> {
            Patient.setCurrentPatient(selectedPatient);
            Model.getInstance().getCommonViewFactory().getAdminViewFactory().showGeneralDetails();
            AdminPanes.getGeneralDetailsController().setGeneralDetailsType(GeneralDetailsType.DELETE);
        });
        ButtonElements.bindIconFillProperty(editButton, "edit-button", new FontAwesomeIconView(FontAwesomeIcon.EDIT, iconSize));
        ButtonElements.bindIconFillProperty(deleteButton, "delete-button", new FontAwesomeIconView(FontAwesomeIcon.TRASH, iconSize));
        ButtonElements.bindIconFillProperty(viewButton, "view-button", new FontAwesomeIconView(FontAwesomeIcon.EYE, iconSize));
        return new Button[]{viewButton, editButton, deleteButton};
    }

    public void patientSelected() {
        if(patientsTable.getSelectionModel().getSelectedItem()==null)return;
        int patientId = (int) patientsTable.getSelectionModel().getSelectedItem().getData("Patient Id");
        try {
            selectedPatient = new Patient(patientId);
            selectedPatient.load();
            Patient.setCurrentPatient(selectedPatient);
            BufferedImage bufferedImage = BarCode.generateQRCodeImage(selectedPatient.getDataList().toString(), (int) userBarCode.getFitWidth(), (int) userBarCode.getFitHeight());
            userBarCode.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
            userName.setText(selectedPatient.loadAndGetData().getString("name"));
            this.userId.setText(String.valueOf(patientId));
            userAddress.setText(selectedPatient.loadAndGetData().getString("gender"));
        } catch (SQLException | WriterException e) {
            GlobalsViews.showErrorAlert(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }
}
