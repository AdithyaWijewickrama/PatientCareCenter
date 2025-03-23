package com.pcc.PatientCareCenter.Views.Panes;

import com.pcc.PatientCareCenter.Controllers.Admin.Patients.*;
import com.pcc.PatientCareCenter.Controllers.Admin.PharmacyStock.StockDetailsController;
import com.pcc.PatientCareCenter.Controllers.AdminControllers;
import com.pcc.PatientCareCenter.Views.GlobalsViews;
import javafx.scene.Parent;

public class AdminPanes extends PaneViewFactory {
    private static final String FXML_PATH = GlobalsViews.FXML_PATH + "/Admin/";
    private static AdminPanes adminPanes;

    private Parent patientsPane;
    private Parent mainStage;
    private Parent patientViewPane;
    private Parent dataAnalyzePane;
    private Parent pharmacyStockPane;
    private Parent budgetPane;
    private Parent navigationPane;
    private Parent generalDetails;
    private Parent prescription;
    private Parent searchConfig;
    private Parent medicalCertificate;
    private Parent certificateOfFitness;
    private Parent profile;
    private Parent settings;
    private Parent claimForm;
    private Parent ultrasoundScan;
    private Parent stockDetails;
    private Parent medicineSelector;

    public AdminPanes() {
    }

    public Parent getPatientsPane() {
        if (patientsPane == null) {
            patientsPane = GlobalsViews.loadFxml(FXML_PATH + "Patients/Patients.fxml", AdminControllers.PATIENTS_CONTROLLER);
        }
        return patientsPane;
    }

    public Parent getGeneralDetails() {
        if (generalDetails == null) {
            generalDetails = GlobalsViews.loadFxml(FXML_PATH + "Patients/GeneralDetails.fxml", AdminControllers.GENERAL_DETAILS_CONTROLLER);
        }
        return generalDetails;
    }

    public Parent getPrescription() {
        if (prescription == null) {
            prescription = GlobalsViews.loadFxml(FXML_PATH + "Patients/Prescription.fxml", AdminControllers.PRESCRIPTION_CONTROLLER);
        }
        return prescription;
    }

    public Parent getSearchConfig() {
        if (searchConfig == null) {
            searchConfig = GlobalsViews.loadFxml(FXML_PATH + "Patients/SearchConfig.fxml", AdminControllers.SEARCH_CONFIG_CONTROLLER);
        }
        return searchConfig;
    }

    public Parent getProfile() {
        if (profile == null) {
            profile = GlobalsViews.loadFxml(FXML_PATH + "Profile.fxml", AdminControllers.PROFILE_CONTROLLER);
        }
        return profile;
    }

    public Parent getSettings() {
        if (settings == null) {
            settings = GlobalsViews.loadFxml(FXML_PATH + "Settings.fxml", AdminControllers.settingsController);
        }
        return settings;
    }

    public Parent getMedicalCertificate() {
        if (medicalCertificate == null) {
            medicalCertificate = GlobalsViews.loadFxml(FXML_PATH + "Patients/MedicalCertificate.fxml", AdminControllers.MEDICAL_CERTIFICATE_CONTROLLER);
        }
        return medicalCertificate;
    }

    public Parent getCertificateOfFitness() {
        if (certificateOfFitness == null) {
            certificateOfFitness = GlobalsViews.loadFxml(FXML_PATH + "Patients/CertificateOfFitness.fxml", AdminControllers.CERTIFICATE_OF_FITNESS_CONTROLLER);
        }
        return certificateOfFitness;
    }

    public Parent getClaimForm() {
        if (claimForm == null) {
            claimForm = GlobalsViews.loadFxml(FXML_PATH + "Patients/ClaimForm.fxml", AdminControllers.claimFormController);
        }
        return claimForm;
    }

    public Parent getStockDetails() {
        if (stockDetails == null) {
            stockDetails = GlobalsViews.loadFxml(FXML_PATH + "PharmacyStock/StockDetails.fxml", AdminControllers.STOCK_DETAILS_CONTROLLER);
        }
        return stockDetails;
    }

    public Parent getMedicineSelector() {
        if (medicineSelector == null) {
            medicineSelector = GlobalsViews.loadFxml(FXML_PATH + "PharmacyStock/MedicineSelector.fxml", AdminControllers.MEDICINE_SELECTOR_CONTROLLER);
        }
        return medicineSelector;
    }

    public Parent getUltrasoundScanForm() {
        if (ultrasoundScan == null) {
            ultrasoundScan = GlobalsViews.loadFxml(FXML_PATH + "Patients/UltrasoundScanForm.fxml", AdminControllers.ultrasoundScanController);
        }
        return ultrasoundScan;
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
            dataAnalyzePane = GlobalsViews.loadFxml(FXML_PATH + "AnalyzeData.fxml");
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
            pharmacyStockPane = GlobalsViews.loadFxml(FXML_PATH + "PharmacyStock/PharmacyStock.fxml", AdminControllers.PHARMACY_STOCK_CONTROLLER);
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

    public static AdminPanes getAdminPanes() {
        return adminPanes;
    }

    public static SearchConfigController getSearchConfigController() {
        return AdminControllers.SEARCH_CONFIG_CONTROLLER;
    }

    public static StockDetailsController getStockDetailsController(){
        return AdminControllers.STOCK_DETAILS_CONTROLLER;
    }
}
