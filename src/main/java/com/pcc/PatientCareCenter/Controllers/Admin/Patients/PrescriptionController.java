package com.pcc.PatientCareCenter.Controllers.Admin.Patients;

import com.pcc.PatientCareCenter.Database.PrescriptionHistory;
import com.pcc.PatientCareCenter.Database.User.Patient;
import com.pcc.PatientCareCenter.Model.*;
import com.pcc.PatientCareCenter.Views.Components.DCConnection.DataComponentConnection;
import com.pcc.PatientCareCenter.Views.Components.DCConnection.IntegerSpinnerConnection;
import com.pcc.PatientCareCenter.Views.Components.DCConnection.StringTextAreaConnection;
import com.pcc.PatientCareCenter.Views.Components.DCConnection.StringTextfieldConnection;
import com.pcc.PatientCareCenter.Views.Components.JRXMLPrinter.PccJRXmlLoader;
import com.pcc.PatientCareCenter.Views.GlobalsViews;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import net.sf.jasperreports.engine.JRException;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class PrescriptionController implements Initializable {

    public TextField name;
    public TextArea description;
    public ListView<Medicine> descriptionList;
    public Spinner<Integer> age;
    public Button addMedicine;
    public TextArea descriptionOther;
    public Button printAndSend;
    public Button print;
    public ToolBar history;
    public Button printNoStock;
    DataComponentConnection[] connections;
    DataComponentConnection[] connections1;
    private int fee;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connections1 = new DataComponentConnection[]{
                new StringTextfieldConnection(name),
                new IntegerSpinnerConnection(age, 0, 200, 0, 5),
                new StringTextAreaConnection(description)
        };
        connections = new DataComponentConnection[]{
                new StringTextfieldConnection(name),
                new IntegerSpinnerConnection(age, 0, 200, 0, 5),
                new DataComponentConnection() {
                    @Override
                    public void setData(Object data) {
                        //do nothing
                    }

                    @Override
                    public Object getData() {
                        return getMedicineDescription(descriptionList.getItems());
                    }
                },
                new StringTextAreaConnection(description)
        };
        descriptionList.setCellFactory(param -> new MedicineListCell());
        age.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 200, 0));
        addMedicine.setOnAction(event -> {
            Model.getInstance().getCommonViewFactory().getAdminViewFactory().getAdmin().showMedicineSelector();
        });
        print.setOnAction(event -> {
            try {
                if (confirmPrescription()) {
                    try {
                        print();
                    } catch (JRException | SQLException e) {
                        GlobalsViews.showErrorAlert(e.getLocalizedMessage());
                        throw new RuntimeException(e);
                    }
                }
            } catch (SQLException e) {
                GlobalsViews.showErrorAlert(e.getLocalizedMessage());
                throw new RuntimeException(e);
            }

        });
        printAndSend.setOnAction(event -> {
            try {
                if (confirmPrescription()) {
                    TextInputDialog dialog = new TextInputDialog("");
                    dialog.setTitle("Input Medical Fee");
                    dialog.setHeaderText("Enter medical fee");
                    dialog.setContentText("Rs. ");
                    dialog.showAndWait().ifPresent(input -> {
                        fee = Integer.parseInt(input);
                        int res = 0;
                        try {
                            res = WebhookSender.sendMessage(getSendMessage(fee));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        if (res < 300 && res >= 200)
                            GlobalsViews.showInformationAlert("Message sent!");
                        else
                            GlobalsViews.showWarningAlert("Error cannot send message:" + res);
                    });

                }
            } catch (SQLException e) {
                GlobalsViews.showErrorAlert("Could not sent message!\n" + e.getLocalizedMessage());
                throw new RuntimeException(e);
            }
            printValues();
        });
        printNoStock.setOnAction(event -> {
            try {
                if (confirmPrescription()) {
                    try {
                        printOutStock();
                    } catch (JRException | SQLException e) {
                        GlobalsViews.showErrorAlert(e.getLocalizedMessage());
                        throw new RuntimeException(e);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void setConfirmed() {
        confiremed = true;
    }

    boolean confiremed = false;

    private boolean confirmPrescription() throws SQLException {
        boolean b = GlobalsViews.showConfirmationAlert("Confirm the prescription!");
        if (b) {
            if (!confiremed) {
                Sql.getInstance().execute("""
                        INSERT INTO public.prescription(
                            description, patient_id, date)
                        VALUES (?, ?, ?);
                        """, getSendMessage(fee)
                        + "\nUnavailable medicines:-------------------------"
                        + getNonStockMedicineDescription(descriptionList.getItems())
                        + "\nDescription------------------------------------"
                        + descriptionOther.getText(), Patient.getCurrentPatient().getPatientIdByUserId(), LocalDate.now());
                confiremed = true;
            }
            for (Medicine med : getStock(descriptionList.getItems())) {
                med.getStock().removeStockMedicine(med.getTotalMedicine());
            }
        }
        return b;
    }

    String msgBorder = "-----------------------------------------------";

    public String getSendMessage(int fee) {
        StringBuilder msg = new StringBuilder();
        msg.append(msgBorder).append("\n");
        msg.append("Name:\t").append(name.getText());
        msg.append("\nAge:\t").append(age.getValueFactory().getValue()).append(" years");
        msg.append("\nAdd from our stock:----------------------------");
        msg.append(getStockMedicineDescription(descriptionList.getItems()));
        msg.append(String.format("\nPrice for medicines:\tRs. %.02f\nConsultant fee:\t%.02f", getPrice(descriptionList.getItems()), (double)fee));
        msg.append(String.format("\nTotal:\tRs. %.02f", getPrice(descriptionList.getItems())+(double)fee));
        msg.append("\n").append(msgBorder);
        return msg.toString();
    }

    private static Integer patient;

    public void loadForCurrentPatient() {
        try {
            confiremed = false;
            if (Patient.getCurrentPatient() == null) {
                clearFields();
                return;
            }
            Patient currentPatient = Patient.getCurrentPatient();
            if (patient != null) {
                if (patient == currentPatient.getPatientIdByUserId()) {
                    return;
                }
            } else {
                patient = currentPatient.getPatientIdByUserId();
            }
            clearFields();
            List<PrescriptionHistory> historyList = PrescriptionHistory.getPrescriptionHistories(currentPatient.getPatientIdByUserId());
            if (historyList != null) {
                for (PrescriptionHistory history : historyList) {
                    Button button = new Button("Prescription " + history.getId());
                    button.setOnAction(event -> showPrescriptionDialog(history));
                    this.history.getItems().add(button);
                }
                if (!historyList.isEmpty()) {
                    PrescriptionHistory latestHistory = historyList.get(historyList.size() - 1);
                    Button latestButton = new Button("Latest Prescription");
                    latestButton.setOnAction(event -> showPrescriptionDialog(latestHistory));
                    history.getItems().add(latestButton);
                }
            }
            name.setText(Patient.getCurrentPatient().getName());
            age.getValueFactory().setValue(Patient.getCurrentPatient().getAgeInYears());
            descriptionList.getItems().remove(0, descriptionList.getItems().size());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addToList(Medicine... list) {
        try {
            for (Medicine med : list) {
                boolean bool = false;
                for (Medicine medc : descriptionList.getItems()) {
                    if (medc.equals(med)) {
                        bool = true;
                        break;
                    }
                }
                if (bool) continue;
                System.out.println(med.hasStock(med.getTotalMedicine()));
                if (med.hasStock(med.getTotalMedicine()) && !med.isExpired()) {
                    descriptionList.getItems().add(med);
                } else {
                    description.appendText((description.getText().isEmpty() ? "" : "\n") + getShowName(med));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public double getPrice(ObservableList<Medicine> list) {
        Double totalPrise = 0.;
        try {
            for (Medicine med : list) {
                if (med.hasStock(med.getTotalMedicine()) && !med.isExpired()) {
                    totalPrise += med.calculatePrise();
                } else {
                    description.appendText(getShowName(med));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return totalPrise;
    }

    public List<Medicine> getStock(List<Medicine> list) throws SQLException {
        List<Medicine> medicineList = new ArrayList<>();
        for (Medicine med : list) {
            if (med.hasStock(med.getTotalMedicine()) && !med.isExpired()) {
                medicineList.add(med);
            }
        }
        return medicineList;
    }

    public void print() throws JRException, SQLException {
        PccJRXmlLoader jrXmlLoader = new PccJRXmlLoader("Prescription");
        jrXmlLoader.addParams(GlobalsViews.getLetterHead());
        jrXmlLoader.printWithDCConnection(
                Arrays.asList(
                        "patientName",
                        "patientAge",
                        "stockMedicine",
                        "otherMedicine"
                ),
                connections);
    }

    public void printOutStock() throws JRException, SQLException {
        PccJRXmlLoader jrXmlLoader = new PccJRXmlLoader("Prescription");
        jrXmlLoader.addParams(GlobalsViews.getLetterHead());
        jrXmlLoader.printWithDCConnection(
                Arrays.asList(
                        "patientName",
                        "patientAge",
                        "otherMedicine"
                ),
                connections1);
    }

    public String getMedicineDescription(List<Medicine> medicineList) {
        StringBuilder med = new StringBuilder();
        medicineList.forEach(medicine -> {
            med.append("\n").append(getShowName(medicine));
        });
        return med.toString();
    }

    public String getStockMedicineDescription(List<Medicine> medicineList) {
        StringBuilder meds = new StringBuilder();
        medicineList.forEach(med -> {
            try {
                if (med.hasStock(med.getTotalMedicine()) && !med.isExpired()) {
                    meds.append("\n").append(getShowName(med));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        return meds.toString();
    }

    public String getNonStockMedicineDescription(List<Medicine> medicineList) {
        StringBuilder meds = new StringBuilder();
        medicineList.forEach(med -> {
            try {
                if (med.hasStock(med.getTotalMedicine()) && !med.isExpired()) {
                    meds.append("\n").append(getShowName(med));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        return meds.toString();
    }

    private void showPrescriptionDialog(PrescriptionHistory history) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Prescription Details");
        dialog.setHeaderText("Prescription ID: " + history.getId());

        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().add(javafx.scene.control.ButtonType.OK);

        VBox content = new VBox(10);
        content.getChildren().addAll(
                new Label("Date: " + history.getDate()),
                new Label("Description:"),
                new TextArea(history.getDescription()) {{
                    setEditable(false);
                    setWrapText(true);
                }}
        );
        dialogPane.setContent(content);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.showAndWait();
    }

    public String getShowName(Medicine medicine) {
        return String.format("%s",medicine.getValues());
    }

    public void printValues(){
        System.out.println(getSendMessage(0));
        System.out.println("Price");
        System.out.println(getPrice(descriptionList.getItems()));
    }

    public void clearFields() {
        name.clear();
        description.clear();
        descriptionOther.clear();
        descriptionList.getItems().clear();
        age.getValueFactory().setValue(0);
        history.getItems().clear();
    }
}
