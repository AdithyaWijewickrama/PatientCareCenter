package com.ppcc.PatientCareCenter.Views.Stages;

import com.ppcc.PatientCareCenter.Views.GlobalsViews;
import com.ppcc.PatientCareCenter.Views.Panes.AdminPanes;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AdminStages extends StageViewFactory {
    private BorderPane mainStage;
    private StringProperty currentPane = new SimpleStringProperty("");

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
        return createStage(mainStage);
    }

    public void showAdminWindow() {
        setCurrentPane("Patients");
        createStage(mainStage).show();
    }
}
