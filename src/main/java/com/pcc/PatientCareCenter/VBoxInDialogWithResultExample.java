package com.pcc.PatientCareCenter;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VBoxInDialogWithResultExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        Button openDialogButton = new Button("Open Dialog");
        openDialogButton.setOnAction(event -> showDialog());

        VBox root = new VBox(openDialogButton);
        Scene scene = new Scene(root, 300, 200);

        primaryStage.setTitle("VBox in Dialog with Result Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showDialog() {
        // Create input fields
        TextField nameField = new TextField();
        TextField ageField = new TextField();
        ComboBox<String> genderComboBox = new ComboBox<>();
        genderComboBox.getItems().addAll("Male", "Female", "Other");

        // Create a VBox with the input fields
        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(
                new Label("Name:"), nameField,
                new Label("Age:"), ageField,
                new Label("Gender:"), genderComboBox
        );

        // Create a dialog
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Custom Dialog");
        dialog.setHeaderText("Please enter your details:");

        // Set the VBox as the content of the dialog
        dialog.getDialogPane().setContent(vbox);

        // Add buttons to the dialog
        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        // Handle the result when OK is clicked
        dialog.setResultConverter(buttonType -> {
            if (buttonType == okButtonType) {
                return "Name: " + nameField.getText() + ", Age: " + ageField.getText() + ", Gender: " + genderComboBox.getValue();
            }
            return null;
        });

        // Show the dialog and print the result
        dialog.showAndWait().ifPresent(result -> System.out.println("User entered: " + result));
    }

    public static void main(String[] args) {
        launch(args);
    }
}