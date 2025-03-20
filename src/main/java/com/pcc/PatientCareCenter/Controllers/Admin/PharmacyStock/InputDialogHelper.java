package com.pcc.PatientCareCenter.Controllers.Admin.PharmacyStock;

import com.pcc.PatientCareCenter.Model.Medicine;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.Optional;

public class InputDialogHelper {

    public static Optional<Medicine.InputValues> showInputDialog() {
        // Create a custom dialog
        Dialog<Medicine.InputValues> dialog = new Dialog<>();
        dialog.setTitle("Input Dialog");
        dialog.setHeaderText("Please enter the following inputs:");

        // Set the button types (OK and Cancel)
        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        // Create the input fields
        Spinner<Integer> frequencySpinner = new Spinner<>(1, 100, 1); // Min: 1, Max: 100, Initial: 1
        Spinner<Integer> daysSpinner = new Spinner<>(0, 31, 0); // Min: 0, Max: 31, Initial: 0
        Spinner<Integer> weeksSpinner = new Spinner<>(0, 52, 0); // Min: 0, Max: 52, Initial: 0
        Spinner<Integer> monthsSpinner = new Spinner<>(0, 12, 0); // Min: 0, Max: 12, Initial: 0

        // Create a grid layout for the inputs
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        grid.add(new Label("Frequency:"), 0, 0);
        grid.add(frequencySpinner, 1, 0);
        grid.add(new Label("Days:"), 0, 1);
        grid.add(daysSpinner, 1, 1);
        grid.add(new Label("Weeks:"), 0, 2);
        grid.add(weeksSpinner, 1, 2);
        grid.add(new Label("Months:"), 0, 3);
        grid.add(monthsSpinner, 1, 3);

        // Add the grid to the dialog
        dialog.getDialogPane().setContent(grid);

        // Convert the result to an InputValues object when the OK button is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                int frequency = frequencySpinner.getValue();
                int days = daysSpinner.getValue();
                int weeks = weeksSpinner.getValue();
                int months = monthsSpinner.getValue();
                return new Medicine.InputValues(frequency, days, weeks, months);
            }
            return null;
        });

        // Show the dialog and wait for the user's response
        return dialog.showAndWait();
    }

}