package com.pcc.PatientCareCenter.Views.Stages;

import com.pcc.PatientCareCenter.Views.GlobalsViews;
import com.pcc.PatientCareCenter.Views.Panes.AdminPanes;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.sql.SQLException;

public class AdminStages extends StageViewFactory {
    private BorderPane mainStage;
    private StringProperty currentPane = new SimpleStringProperty("");
    private Stage adminStage;

    public AdminStages(StringProperty stageTitle, Image stageImage) {
        this.stageTitle = stageTitle;
        this.stageImage = stageImage;
        this.mainStage = (BorderPane) AdminPanes.getInstance().getMainStage();
        mainStage.setLeft(AdminPanes.getInstance().getNavigationPane());
//        mainStage.setCenter(AdminPanes.getInstance().getPatientsPane());
        this.currentPane.addListener(event -> {
            switch (currentPane.getValue()) {
                case "Patients" -> mainStage.setCenter(AdminPanes.getInstance().getPatientsPane());
                case "PatientView" -> mainStage.setCenter(AdminPanes.getInstance().getPatientViewPane());
                case "PharmacyStock" -> mainStage.setCenter(AdminPanes.getInstance().getPharmacyStockPane());
                case "DataAnalyze" -> mainStage.setCenter(AdminPanes.getInstance().getDataAnalyzePane());
                case "Budget" -> mainStage.setCenter(AdminPanes.getInstance().getBudgetPane());
                case "Profile" -> showProfile();
                case "Settings" -> showSettings();
            }
            System.out.println(currentPane.getValue());
        });
    }

    public void setCurrentPane(String pane) {
        currentPane.setValue(pane);
    }

    public AdminStages() {
        this(new SimpleStringProperty(GlobalsViews.APP_NAME), GlobalsViews.APP_ICON);
    }

    public Stage getAdminWindow() {
        setCurrentPane("Patients");
        if (adminStage == null) {
            adminStage = createStage(mainStage);
        }
        return adminStage;
    }

    public void showAdminWindow() {
        getAdminWindow().show();
    }

    public void showGeneralDetails() {
        Dialog<?> dialog = GlobalsViews.getDialog();
        dialog.getDialogPane().setContent(AdminPanes.getInstance().getGeneralDetails());
        dialog.onCloseRequestProperty().addListener(event -> {
            AdminPanes.getPatientsController().tableLoad();
        });
        dialog.show();
    }

    public void showPrescription() {
        Dialog<?> dialog = GlobalsViews.getDialog();
        dialog.getDialogPane().setContent(AdminPanes.getInstance().getPrescription());
        dialog.show();
    }

    public void showSearchConfig() {
        Dialog<?> dialog = GlobalsViews.getDialog();
        dialog.getDialogPane().setContent(AdminPanes.getInstance().getSearchConfig());
        dialog.show();
    }

    public void showMedicalCertificate() {
        Dialog<?> dialog = GlobalsViews.getDialog();
        dialog.getDialogPane().setContent(AdminPanes.getInstance().getMedicalCertificate());
        dialog.show();
    }

    public void showCertificateOfFitness() {
        Dialog<?> dialog = GlobalsViews.getDialog();
        dialog.getDialogPane().setContent(AdminPanes.getInstance().getCertificateOfFitness());
        dialog.show();
    }

    public void showProfile() {
        Dialog<?> dialog = GlobalsViews.getDialog();
        dialog.getDialogPane().setContent(AdminPanes.getInstance().getProfile());
        dialog.onCloseRequestProperty().addListener(event -> {
            try {
                AdminPanes.getPatientsController().updateFrame();
            } catch (SQLException e) {
                GlobalsViews.showErrorAlert(e.getLocalizedMessage());
                throw new RuntimeException(e);
            }
        });
        dialog.show();
    }

    public void showSettings() {
        Dialog<?> dialog = GlobalsViews.getDialog();
        dialog.getDialogPane().setContent(AdminPanes.getInstance().getSettings());
        dialog.show();
    }

    public void showClaimForm() {
        Dialog<?> dialog = GlobalsViews.getDialog();
        dialog.getDialogPane().setContent(AdminPanes.getInstance().getClaimForm());
        dialog.show();
    }

    public void showUltrasoundScanForm() {
        Dialog<?> dialog = GlobalsViews.getDialog();
        dialog.getDialogPane().setContent(AdminPanes.getInstance().getUltrasoundScanForm());
        dialog.show();
    }

    public void showStockDetails() {
        Dialog<?> dialog = GlobalsViews.getDialog();
        dialog.getDialogPane().setContent(AdminPanes.getInstance().getStockDetails());
        dialog.show();
    }

    public void showMedicineSelector() {
        Dialog<?> dialog = GlobalsViews.getDialog();
        dialog.getDialogPane().setContent(AdminPanes.getInstance().getMedicineSelector());
        dialog.show();
    }
}
