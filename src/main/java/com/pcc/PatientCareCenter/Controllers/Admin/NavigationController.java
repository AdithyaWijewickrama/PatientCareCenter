package com.pcc.PatientCareCenter.Controllers.Admin;

import com.pcc.PatientCareCenter.Model.Model;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class NavigationController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void onPatientPane() {
        Model.getInstance().getCommonViewFactory().getAdminViewFactory().showPatientsPane();
    }

    public void onPatientViewPane() {
        Model.getInstance().getCommonViewFactory().getAdminViewFactory().showPatientViewPane();

    }

    public void onAnalyzeDataPane() {
        Model.getInstance().getCommonViewFactory().getAdminViewFactory().showDataAnalyzePane();

    }

    public void onPharmacyStockPane() {
        Model.getInstance().getCommonViewFactory().getAdminViewFactory().showPharmacyStockPane();

    }

    public void onBudgetPane() {
        Model.getInstance().getCommonViewFactory().getAdminViewFactory().showBudgetPane();

    }

    public void onProfileDialog() {
        Model.getInstance().getCommonViewFactory().getAdminViewFactory().getAdmin().showProfile();
    }
    public void onSettingsDialog() {
        Model.getInstance().getCommonViewFactory().getAdminViewFactory().getAdmin().showSettings();
    }

}
