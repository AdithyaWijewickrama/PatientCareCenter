package com.pcc.PatientCareCenter.Views;

import com.pcc.PatientCareCenter.Views.Stages.AdminStages;

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

    public void showGeneralDetails(){
        admin.showGeneralDetails();
    }
}
