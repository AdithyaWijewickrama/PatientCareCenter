package com.pcc.PatientCareCenter.Controllers.Admin.PharmacyStock;

import com.pcc.PatientCareCenter.Database.Stock;
import com.pcc.PatientCareCenter.Model.FrequencyType;
import com.pcc.PatientCareCenter.Model.Medicine;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class InputDialogHelper {

    public static Optional<Medicine.InputValues> showInputDialog(Stock med) throws SQLException {
        Dialog<Medicine.InputValues> dialog = new Dialog<>();
        dialog.setTitle("Input Dialog");
        dialog.setHeaderText(med.getName() + " " + med.getQuantity() + " available");

        ComboBox<String> frequencyCombo = new ComboBox<>(FXCollections.observableArrayList(Arrays.stream(FrequencyType.values()).map(FrequencyType::getName).toList()));
        frequencyCombo.getSelectionModel().select(FrequencyType.BD.getName());

        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        Spinner<Integer> daysSpinner = new Spinner<>(0, 31, 0);
        Spinner<Integer> noOfDosesPerMedicine = new Spinner<>(1, 100000, 1, 5);
        Spinner<Integer> dose = new Spinner<>(1, med.getStrength(), med.getStrength(), 5);
        dose.setEditable(false);

        Spinner<Integer> weeksSpinner = new Spinner<>(0, 52, 0);
        Spinner<Integer> monthsSpinner = new Spinner<>(0, 12, 0);
        frequencyCombo.valueProperty().addListener((observable, oldValue, newValue) -> {
            boolean b = !newValue.equals(FrequencyType.WEEKLY.getName());
            daysSpinner.setVisible(b);
            if (b)
                daysSpinner.getValueFactory().setValue(0);
        });
        dose.setEditable(false);
        daysSpinner.setEditable(true);
        monthsSpinner.setEditable(true);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        dose.valueProperty().addListener((observable, oldValue, newValue) -> {
            try {
                noOfDosesPerMedicine.getValueFactory().setValue(med.getStrength() / newValue);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        int row = 0;
        grid.add(new Label("Frequency:"), 0, row++);
        grid.add(frequencyCombo, 1, row - 1);
        grid.add(new Label("Dose(" + med.getUnit() + "):"), 0, row++);
        grid.add(dose, 1, row - 1);
        grid.add(new Label("No. of doses per pack:"), 0, row++);
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
                Medicine.InputValues inputValues = new Medicine.InputValues(med,FrequencyType.getFrequencyType(frequencyCombo.getValue()), noOfDosesPerMedicine.valueProperty().getValue(), days, weeks, months);
                System.out.println(inputValues);
                return inputValues;
            }
            return null;
        });
        return dialog.showAndWait();
    }

}