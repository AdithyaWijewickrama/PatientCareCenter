package com.ppcc.PatientCareCenter.Views.Panes;

import com.ppcc.PatientCareCenter.Controllers.Admin.PatientsController;
import com.ppcc.PatientCareCenter.Views.GlobalsViews;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

import static com.ppcc.PatientCareCenter.Views.GlobalsViews.FXML_PATH;

public class AdminPanes extends PaneViewFactory {
    private static final String FXML_PATH=GlobalsViews.FXML_PATH+"/Admin/";
    private static AdminPanes adminPanes;

    private Parent patientsPane;
    private Parent mainStage;
    private Parent patientViewPane;
    private Parent dataAnalyzePane;
    private Parent pharmacyStockPane;
    private Parent budgetPane;
    private Parent navigationPane;
    private Parent generalDetails;

    public AdminPanes() {
    }

    public Parent getPatientsPane() {
        if (patientsPane == null) {
            patientsPane = GlobalsViews.loadFxml(FXML_PATH + "Patients/Patients.fxml",new PatientsController());
        }
        return patientsPane;
    }

    public Parent getGeneralDetails() {
        if (generalDetails == null) {
            generalDetails = GlobalsViews.loadFxml(FXML_PATH + "Patients/GeneralDetails.fxml",new PatientsController());
        }
        return generalDetails;
    }

    public Parent getNavigationPane() {
        if (navigationPane == null) {
            navigationPane = GlobalsViews.loadFxml(FXML_PATH + "Navigation.fxml");
        }
        return navigationPane;
    }

    public Parent getPatientViewPane() {
        if (patientViewPane == null) {
            patientViewPane = GlobalsViews.loadFxml(FXML_PATH + "PatientView.fxml");
        }
        return patientViewPane;
    }

    public Parent getDataAnalyzePane() {
        if (dataAnalyzePane == null) {
            dataAnalyzePane = GlobalsViews.loadFxml(FXML_PATH + "DataAnalyze.fxml");
        }
        return dataAnalyzePane;
    }

    public Parent getBudgetPane() {
        if (budgetPane == null) {
            budgetPane = GlobalsViews.loadFxml(FXML_PATH + "Budget.fxml");
        }
        return budgetPane;
    }

    public Parent getPharmacyStockPane() {
        if (pharmacyStockPane == null) {
            pharmacyStockPane = GlobalsViews.loadFxml(FXML_PATH + "PharmacyStock.fxml");
        }
        return pharmacyStockPane;
    }

    public Parent getMainStage() {
        if (mainStage == null) {
            mainStage = GlobalsViews.loadFxml(FXML_PATH + "MainStage.fxml");
        }
        return mainStage;
    }

    public static AdminPanes getInstance() {
        if (adminPanes == null) {
            adminPanes = new AdminPanes();
        }
        return adminPanes;
    }

    public static void main(String[] args) {
        AdminPanes a = new AdminPanes();
        System.out.println(a.getPatientsPane());
        System.out.println(a.getPatientsPane());
    }
}
