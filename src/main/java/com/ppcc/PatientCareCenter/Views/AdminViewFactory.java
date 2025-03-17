package com.ppcc.PatientCareCenter.Views;

import com.ppcc.PatientCareCenter.Views.Panes.AdminPanes;
import com.ppcc.PatientCareCenter.Views.Stages.AdminStages;
import com.ppcc.PatientCareCenter.Views.Stages.StageViewFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class AdminViewFactory {
    private AdminStages admin;

    public AdminViewFactory() {
        admin = new AdminStages();
    }

    public void showAdminWindow() {
        admin.showAdminWindow();
    }

    public void showPatientsPane() {
        admin.setCurrentPane("Patients");
    }

    public void showPatientViewPane() {
        admin.setCurrentPane("PatientView");
    }

    public void showDataAnalyzePane() {
        admin.setCurrentPane("DataAnalyze");
    }

    public void showPharmacyStockPane() {
        admin.setCurrentPane("PharmacyStock");
    }

    public void showBudgetPane() {
        admin.setCurrentPane("Budget");
    }
}
