package com.pcc.PatientCareCenter.Controllers.Admin.PharmacyStock;

import com.pcc.PatientCareCenter.Database.Stock;
import com.pcc.PatientCareCenter.Model.FrequencyType;
import com.pcc.PatientCareCenter.Model.Medicine;
import com.pcc.PatientCareCenter.Model.MedicineType;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

public class InputDialogHelper {

    public static Optional<Medicine.InputValues> showInputDialog(Stock med) throws SQLException {
        Dialog<Medicine.InputValues> dialog = new Dialog<>();
        dialog.setTitle("Select medicine");
        dialog.setHeaderText(med.getName() + " " + med.getQuantity() + " in stock");

        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelAllButton = new ButtonType("Cancel all", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL,cancelAllButton);

        Spinner<Integer> daysSpinner = new Spinner<>(0, 31, 0);
        Spinner<Integer> noOfDosesPerMedicine = new Spinner<>(1, 100000, 1, 5);
        TextField dose = new TextField();
        dose.setText(med.getStrength().toString());

        Spinner<Integer> weeksSpinner = new Spinner<>(0, 52, 0);
        Spinner<Integer> monthsSpinner = new Spinner<>(0, 12, 0);

        ComboBox<String> frequencyCombo = new ComboBox<>(FXCollections.observableArrayList(Arrays.stream(FrequencyType.values()).map(FrequencyType::getName).toList()));
        frequencyCombo.getSelectionModel().select(FrequencyType.BD.getName());
        frequencyCombo.valueProperty().addListener((observable, oldValue, newValue) -> {
            boolean b = !newValue.equals(FrequencyType.WEEKLY.getName());
            daysSpinner.setVisible(b);
            if (b) {
                daysSpinner.getValueFactory().setValue(0);
            }
        });
        String multiply="No. of "+med.getMedicineType().getDisplayName().toLowerCase(Locale.ROOT)+"s per dose:";
        String divide="Divide "+med.getMedicineType().getDisplayName().toLowerCase(Locale.ROOT)+" in to:";
        Label label=new Label(multiply);
        ComboBox<String> doseMultipleSelector = new ComboBox<>(FXCollections.observableArrayList("1 or more","divide"));
        doseMultipleSelector.getSelectionModel().select(FrequencyType.BD.getName());
        doseMultipleSelector.valueProperty().addListener((observable, oldValue, newValue) -> {
            boolean b = newValue.equals("1 or more");
            if(b){
                label.setText(multiply);
            }else{
                label.setText(divide);
            }
        });
        if(med.getMedicineType()== MedicineType.TABLET){
            doseMultipleSelector.getSelectionModel().select("1 or more");
        }else
            doseMultipleSelector.getSelectionModel().select("Divide");
        dose.setEditable(false);
        daysSpinner.setEditable(true);
        monthsSpinner.setEditable(true);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        int row = 0;
        grid.add(new Label("Frequency:"), 0, row++);
        grid.add(frequencyCombo, 1, 0);
        grid.add(new Label("Dose(" + med.getUnit() + "):"), 0, row++);
        grid.add(dose, 1, row - 1);
        grid.add(new Label("Dose multiply by:"), 0, row++);
        grid.add(doseMultipleSelector, 1, row - 1);
        grid.add(label, 0, row++);
        grid.add(noOfDosesPerMedicine, 1, row - 1);
        grid.add(new Label("Days:"), 0, row++);
        grid.add(daysSpinner, 1, row - 1);
        grid.add(new Label("Weeks:"), 0, row++);
        grid.add(weeksSpinner, 1, row - 1);
        grid.add(new Label("Months:"), 0, row++);
        grid.add(monthsSpinner, 1, row - 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                int days = daysSpinner.valueProperty().getValue();
                int weeks = weeksSpinner.valueProperty().getValue();
                int months = monthsSpinner.valueProperty().getValue();
                Medicine.InputValues inputValues = new Medicine.InputValues(med,FrequencyType.getFrequencyType(frequencyCombo.getValue()), (doseMultipleSelector.getSelectionModel().getSelectedItem().equals("1 or more")?noOfDosesPerMedicine.valueProperty().getValue():1./noOfDosesPerMedicine.valueProperty().getValue()), days, weeks, months);
                System.out.println(inputValues);
                return inputValues;
            }else if(dialogButton==cancelAllButton){
                return new Medicine.InputValues(null,null,0,0,0,0);
            }
            return null;
        });
        return dialog.showAndWait();
    }

}