package com.pcc.PatientCareCenter.Views.Stages;

import com.pcc.PatientCareCenter.Views.GlobalsViews;
import com.pcc.PatientCareCenter.Views.Panes.AdminPanes;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

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
            }
            System.out.println(currentPane.getValue());
        });
//        setCurrentPane("Patients");
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
        Dialog<Object> dialog = new Dialog<>();
        dialog.getDialogPane().getStylesheets().add(getClass().getResource("/com/pcc/PatientCareCenter/Styles/globals.css").toExternalForm());
        dialog.getDialogPane().setContent(AdminPanes.getInstance().getGeneralDetails());
//        Stage dialogStage = new Stage();
//        dialogStage.initStyle(StageStyle.UNDECORATED); // Remove all decorations
//        dialogStage.setScene(new Scene(new HBox()));
//        dialogStage.getScene().getStylesheets().add(getClass().getResource("/com/ppcc/PatientCareCenter/Styles/globals.css").toExternalForm());
//        dialog.initOwner(dialogStage);
//        dialog.getDialogPane().getButtonTypes().add(ButtonType.APPLY);
//        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.showAndWait();
    }
}
