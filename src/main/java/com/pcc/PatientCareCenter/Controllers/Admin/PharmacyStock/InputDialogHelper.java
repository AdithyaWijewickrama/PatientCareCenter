package com.pcc.PatientCareCenter.Controllers.Admin.PharmacyStock;

import com.pcc.PatientCareCenter.Database.Stock;
import com.pcc.PatientCareCenter.Model.FrequencyType;
import com.pcc.PatientCareCenter.Model.Medicine;
import com.pcc.PatientCareCenter.Model.MedicineType;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ObservableValueBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import javax.management.ListenerNotFoundException;
import javax.security.auth.callback.Callback;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.sql.SQLException;
import java.util.*;

public class InputDialogHelper {

    public static Optional<Medicine.InputValues> showInputDialog(Stock med) throws SQLException {
        Dialog<Medicine.InputValues> dialog = new Dialog<>();
        dialog.setTitle("Select medicine");
        dialog.setHeaderText(med.getLocalizedName() + " " + med.getQuantity() + " in stock");

        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelAllButton = new ButtonType("Cancel all", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL, cancelAllButton);

        Spinner<Integer> daysSpinner = new Spinner<>(0, 31, 0);
        Spinner<Integer> noOfDosesPerMedicine = new Spinner<>(1, 100000, 1, 1);
        Spinner<Double> dose = new Spinner<>();

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
        String multiply = "No. of " + med.getMedicineType().getDisplayName().toLowerCase(Locale.ROOT) + "s per dose:";
        String divide = "Divide " + med.getMedicineType().getDisplayName().toLowerCase(Locale.ROOT) + " in to:";
        Label label = new Label(multiply);
        ComboBox<String> doseMultipleSelector = new ComboBox<>(FXCollections.observableArrayList("1 or more", "divide"));
        doseMultipleSelector.getSelectionModel().select(FrequencyType.BD.getName());

        javafx.beans.value.ChangeListener nodListner = new javafx.beans.value.ChangeListener() {
            /**
             * @param observableValue
             * @param o
             * @param t1
             */
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                System.out.println("no do chamged");
                if(noOfDosesPerMedicine.isDisable())return;
                int nod = noOfDosesPerMedicine.getValue();
                if (nod != 0) {
                    try {
                        dose.getValueFactory().valueProperty().setValue((double) (nod * med.getStrength()));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        };
        javafx.beans.value.ChangeListener dListner = new javafx.beans.value.ChangeListener() {
            /**
             * @param observableValue
             * @param o
             * @param t1
             */
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                if(dose.isDisable())return;
                System.err.println("-------------dose changed");
                double dos = dose.getValue();
                if (dos != 0) {
                    try {
                        noOfDosesPerMedicine.getValueFactory().valueProperty().setValue((int) (med.getStrength() / dos));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };

        doseMultipleSelector.valueProperty().addListener((observable, oldValue, newValue) -> {
            boolean b = newValue.equals("1 or more");
            if (b) {
                try {
                    dose.valueProperty().removeListener(dListner);
                    noOfDosesPerMedicine.valueProperty().removeListener(nodListner);
                    dose.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(med.getStrength(), 100000, med.getStrength(), med.getStrength()));
                    noOfDosesPerMedicine.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100000, 1, 1));
                    dose.setDisable(true);
                    noOfDosesPerMedicine.setDisable(false);
                    noOfDosesPerMedicine.valueProperty().addListener(nodListner);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                label.setText(multiply);
            } else {
                try {
                    dose.valueProperty().removeListener(dListner);
                    noOfDosesPerMedicine.valueProperty().removeListener(nodListner);
                    dose.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, med.getStrength(), med.getStrength() / 100., med.getStrength() / 100.));
                    noOfDosesPerMedicine.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 100, 1));
                    dose.setDisable(false);
                    noOfDosesPerMedicine.setDisable(true);
                    dose.valueProperty().addListener(dListner);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                label.setText(divide);
            }
        });
        if (med.getMedicineType() == MedicineType.TABLET) {
            doseMultipleSelector.getSelectionModel().select("1 or more");
        } else {
            doseMultipleSelector.getSelectionModel().select("Divide");
        }
        dose.getValueFactory().valueProperty().addListener(observable -> {
            System.out.println("=============");
            System.out.println(dose.getValueFactory().getValue());
            System.out.println(dose.valueProperty().getValue());
            System.out.println(dose.getValue());
            System.out.println("=============");
        });
        daysSpinner.setEditable(true);
        noOfDosesPerMedicine.setEditable(true);
        monthsSpinner.setEditable(true);
        dose.setEditable(true);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        int row = 0;
        grid.add(new Label("Frequency:"), 0, row++);
        grid.add(frequencyCombo, 1, 0);
        grid.add(new Label("Dose multiply by:"), 0, row++);
        grid.add(doseMultipleSelector, 1, row - 1);
        grid.add(new Label("Per dose contains:"), 0, row++);
        grid.add(dose, 1, row - 1);
        grid.add(new Label(med.getUnit()), 3, row - 1);
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
                Medicine.InputValues inputValues = new Medicine.InputValues(med, FrequencyType.getFrequencyType(frequencyCombo.getValue()), (doseMultipleSelector.getSelectionModel().getSelectedItem().equals("1 or more") ? noOfDosesPerMedicine.valueProperty().getValue() : 1. / noOfDosesPerMedicine.valueProperty().getValue()), days, weeks, months);
                System.out.println(inputValues);
                return inputValues;
            } else if (dialogButton == cancelAllButton) {
                return new Medicine.InputValues(null, null, 0, 0, 0, 0);
            }
            return null;
        });
        return dialog.showAndWait();
    }


}

class CustomListenerManager {
    private final List<ChangeListener> listeners = new ArrayList<>();

    public void addListener(Spinner<Integer> spinner, ChangeListener listener) {
        spinner.valueProperty().addListener((javafx.beans.value.ChangeListener<? super Integer>) listener);
        listeners.add(listener);
    }

    public void removeListener(Spinner<Integer> spinner, ChangeListener listener) {
        spinner.valueProperty().removeListener((javafx.beans.value.ChangeListener<? super Integer>) listener);
        listeners.remove(listener);
    }

    public void removeAllListeners(Spinner<Integer> spinner) {
        for (ChangeListener listener : listeners) {
            spinner.valueProperty().removeListener((javafx.beans.value.ChangeListener<? super Integer>) listener);
        }
        listeners.clear();
    }
}