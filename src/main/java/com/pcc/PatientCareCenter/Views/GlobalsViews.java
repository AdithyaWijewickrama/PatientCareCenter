package com.pcc.PatientCareCenter.Views;

import com.pcc.PatientCareCenter.Database.User.Admin.Doctor;
import com.pcc.PatientCareCenter.Model.Sql;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class GlobalsViews {
    public static final int STAGE_WIDTH = 600;
    public static final int STAGE_HEIGHT = 400;
    public static final String APP_NAME = "Patient Care Center";
    public static final Color APP_COLOR = Color.NAVY;
    public static final Image APP_ICON = new Image(Objects.requireNonNull(GlobalsViews.class.getResource("/com/pcc/PatientCareCenter/Images/logo.png")).toString());
    public static final java.awt.Image APP_ICON_IMAGE = new ImageIcon(Objects.requireNonNull(GlobalsViews.class.getResource("/com/pcc/PatientCareCenter/Images/logo.png")).toString()).getImage();
    public static final String FXML_PATH = "/com/pcc/PatientCareCenter/Fxml";

    public static Stage createPrimaryStage(Parent pane, StringProperty title, Image icon) {
        Scene scene;
        scene = new Scene(pane);
        Stage stage = new Stage();
        stage.getIcons().add(icon);
        stage.setTitle(title.getValue());
        title.addListener(observable -> {
            stage.setTitle(title.getValue());
        });
        stage.setScene(scene);
        return stage;
    }

    public static Map<String, Object> getLetterHead() throws SQLException {
        Map<String, Object> map = new HashMap<>();
        List<Object> row = Sql.getInstance().getRow("""
                SELECT
                    pp.name,
                    pp.address,
                    d.name,
                    pp.email,
                    pp.telephone
                FROM
                    pp_details pp
                JOIN
                    doctor d ON d.doctor_id=pp.doctor_id
                WHERE d.doctor_id=?;""", Doctor.getCurrentDoctor().getDoctorId());
        int i = 0;
        for (String key : new String[]{"appName", "address", "doctorName", "email", "telephone"}) {
            map.put(key, row.get(i++));
        }
        return map;
    }

    public static synchronized Parent loadFxml(String fxmlPath) {
        try {
            return new FXMLLoader(GlobalsViews.class.getResource(fxmlPath)).load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized Parent loadFxml(String fxmlPath, Object controller) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(GlobalsViews.class.getResource(fxmlPath));
            fxmlLoader.setController(controller);
            return fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Image getImage(FontAwesomeIcon icon) {
        FontAwesomeIconView iconView = new FontAwesomeIconView(icon);
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(APP_COLOR);
        return iconView.snapshot(params, null);
    }

    public static Dialog<?> getDialog() {
        Dialog<?> dialog = new Dialog<>();
        dialog.getDialogPane().getStylesheets().add(GlobalsViews.class.getResource("/com/pcc/PatientCareCenter/Styles/globals.css").toExternalForm());
        Window window = dialog.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(event -> window.hide());
        ((Stage) dialog.getDialogPane().getScene().getWindow()).getIcons().add(APP_ICON);
        return dialog;
    }

    public static void showInformationAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getDialogPane().setContentText(message);
        addIconToAlert(alert);
        alert.show();
    }

    public static boolean showConfirmationAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.getDialogPane().setContentText(message);
        addIconToAlert(alert);
        Optional<ButtonType> buttonType = alert.showAndWait();
        return buttonType.filter(type -> type == ButtonType.OK).isPresent();
    }

    public static void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.getDialogPane().setContentText(message);
        addIconToAlert(alert);
        alert.show();
    }

    public static void addIconToAlert(Alert alert) {
        ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(APP_ICON);
    }

    public static void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.getDialogPane().setContentText(message);
        addIconToAlert(alert);
        alert.show();
    }

    public static boolean showWarningAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.getDialogPane().setContentText(message);
        addIconToAlert(alert);
        return alert.showAndWait().isPresent();
    }
}
