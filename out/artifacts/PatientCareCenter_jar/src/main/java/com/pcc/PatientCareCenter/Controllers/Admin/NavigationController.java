package com.pcc.PatientCareCenter.Controllers.Admin;

import com.pcc.PatientCareCenter.Model.Model;
import com.pcc.PatientCareCenter.Views.GlobalsViews;
import com.pcc.PatientCareCenter.Views.Panes.AdminPanes;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.SQLException;
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
        AdminPanes.getPharmacyStockController().tableLoad();

    }

    public void onBudgetPane() {
        Model.getInstance().getCommonViewFactory().getAdminViewFactory().showBudgetPane();

    }

    public void onProfileDialog() {
        Model.getInstance().getCommonViewFactory().getAdminViewFactory().getAdmin().showProfile();
        try {
            AdminPanes.getProfileController().load();
        } catch (SQLException e) {
            GlobalsViews.showErrorAlert(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    public void onSettingsDialog() {
        Model.getInstance().getCommonViewFactory().getAdminViewFactory().getAdmin().showSettings();
        try {
            AdminPanes.getSettingsController().loadSettings();
        } catch (SQLException e) {
            GlobalsViews.showErrorAlert(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

}
