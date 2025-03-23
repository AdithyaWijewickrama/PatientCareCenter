package com.pcc.PatientCareCenter.Views;

import com.pcc.PatientCareCenter.Database.Server.DatabaseConfigManager;
import com.pcc.PatientCareCenter.Model.PasswordEncryptor;
import com.pcc.PatientCareCenter.Model.Sql;
import javafx.application.Application;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

public class DatabaseConfigDialog extends Application {

    @Override
    public void start(Stage primaryStage) {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setWidth(300);
        dialog.setWidth(300);
        dialog.setTitle("Database Configuration");
        dialog.setHeaderText("Enter Database Credentials");

        ButtonType loginButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField urlField = new TextField();
        urlField.setPromptText("Database URL");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        grid.add(new Label("URL:"), 0, 0);
        grid.add(urlField, 1, 0);
        grid.add(new Label("Username:"), 0, 1);
        grid.add(usernameField, 1, 1);
        grid.add(new Label("Password:"), 0, 2);
        grid.add(passwordField, 1, 2);

        dialog.getDialogPane().setContent(grid);

        dialog.getDialogPane().lookupButton(loginButtonType).setDisable(true);

        urlField.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean disable = newValue.trim().isEmpty() || usernameField.getText().trim().isEmpty() || passwordField.getText().trim().isEmpty();
            dialog.getDialogPane().lookupButton(loginButtonType).setDisable(disable);
        });

        usernameField.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean disable = newValue.trim().isEmpty() || urlField.getText().trim().isEmpty() || passwordField.getText().trim().isEmpty();
            dialog.getDialogPane().lookupButton(loginButtonType).setDisable(disable);
        });

        passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean disable = newValue.trim().isEmpty() || urlField.getText().trim().isEmpty() || usernameField.getText().trim().isEmpty();
            dialog.getDialogPane().lookupButton(loginButtonType).setDisable(disable);
        });

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(urlField.getText(), usernameField.getText() + ":" + passwordField.getText());
            }
            return null;
        });

        dialog.showAndWait().ifPresent(result -> {
            System.out.println("ksfjkaf");
            String url = result.getKey();
            String[] credentials = result.getValue().split(":");
            String username = credentials[0];
            String password = credentials[1];
            try {
                DatabaseConfigManager.writeConfig(url, username, PasswordEncryptor.encrypt(password));
                Sql sql = new Sql(url, username, password);
                sql.connect();
                Sql.setInstance(sql);
            } catch (Exception e) {
                boolean b = GlobalsViews.showConfirmationAlert(e.getLocalizedMessage());
                if (b) {
                    System.exit(0);
                }
            }
            System.out.println("Database URL: " + url);
            System.out.println("Username: " + username);
            System.out.println("Password: " + password);
        });
    }
}