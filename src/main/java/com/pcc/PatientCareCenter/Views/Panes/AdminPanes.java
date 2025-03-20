package com.pcc.PatientCareCenter.Views.Panes;

import com.pcc.PatientCareCenter.Controllers.Admin.Patients.*;
import com.pcc.PatientCareCenter.Controllers.Admin.Patients.UltrasoundScanFormController;
import com.pcc.PatientCareCenter.Controllers.Admin.PharmacyStock.MedicineSelectorController;
import com.pcc.PatientCareCenter.Controllers.Admin.PharmacyStock.PharmacyStockController;
import com.pcc.PatientCareCenter.Controllers.Admin.PharmacyStock.StockDetailsController;
import com.pcc.PatientCareCenter.Controllers.Admin.ProfileController;
import com.pcc.PatientCareCenter.Controllers.Admin.SettingsController;
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

    //Controllers
    private static final PatientsController patientsController = new PatientsController();
    private static final GeneralDetailsController generalDetailsController = new GeneralDetailsController();
    private static final PrescriptionController prescriptionController = new PrescriptionController();
    private static final SearchConfigController searchConfigController = new SearchConfigController();
    private static final MedicalCertificateController medicalCertificateController = new MedicalCertificateController();
    private static final CertificateOfFitnessController certificateOfFitnessController = new CertificateOfFitnessController();
    private static final ProfileController profileController = new ProfileController();
    private static final SettingsController settingsController = new SettingsController();
    private static final ClaimFormController claimFormController = new ClaimFormController();
    private static final UltrasoundScanFormController ultrasoundScanController = new UltrasoundScanFormController();
    private static final StockDetailsController STOCK_DETAILS_CONTROLLER=new StockDetailsController();
    private static final PharmacyStockController PHARMACY_STOCK_CONTROLLER=new PharmacyStockController();
    private static final MedicineSelectorController MEDICINE_SELECTOR_CONTROLLER=new MedicineSelectorController();

    public AdminPanes() {
    }

    public Parent getPatientsPane() {
        if (patientsPane == null) {
            patientsPane = GlobalsViews.loadFxml(FXML_PATH + "Patients/Patients.fxml", patientsController);
        }
        return patientsPane;
    }

    public Parent getGeneralDetails() {
        if (generalDetails == null) {
            generalDetails = GlobalsViews.loadFxml(FXML_PATH + "Patients/GeneralDetails.fxml", generalDetailsController);
        }
        return generalDetails;
    }

    public Parent getPrescription() {
        if (prescription == null) {
            prescription = GlobalsViews.loadFxml(FXML_PATH + "Patients/Prescription.fxml", prescriptionController);
        }
        return prescription;
    }

    public Parent getSearchConfig() {
        if (searchConfig == null) {
            searchConfig = GlobalsViews.loadFxml(FXML_PATH + "Patients/SearchConfig.fxml", searchConfigController);
        }
        return searchConfig;
    }

    public Parent getProfile() {
        if (profile == null) {
            profile = GlobalsViews.loadFxml(FXML_PATH + "Profile.fxml", profileController);
        }
        return profile;
    }

    public Parent getSettings() {
        if (settings == null) {
            settings = GlobalsViews.loadFxml(FXML_PATH + "Settings.fxml", settingsController);
        }
        return settings;
    }

    public Parent getMedicalCertificate() {
        if (medicalCertificate == null) {
            medicalCertificate = GlobalsViews.loadFxml(FXML_PATH + "Patients/MedicalCertificate.fxml", medicalCertificateController);
        }
        return medicalCertificate;
    }

    public Parent getCertificateOfFitness() {
        if (certificateOfFitness == null) {
            certificateOfFitness = GlobalsViews.loadFxml(FXML_PATH + "Patients/CertificateOfFitness.fxml", certificateOfFitnessController);
        }
        return certificateOfFitness;
    }

    public Parent getClaimForm() {
        if (claimForm == null) {
            claimForm = GlobalsViews.loadFxml(FXML_PATH + "Patients/ClaimForm.fxml", claimFormController);
        }
        return claimForm;
    }

    public Parent getStockDetails() {
        if (stockDetails == null) {
            stockDetails = GlobalsViews.loadFxml(FXML_PATH + "PharmacyStock/StockDetails.fxml", STOCK_DETAILS_CONTROLLER);
        }
        return stockDetails;
    }

    public Parent getMedicineSelector() {
        if (stockDetails == null) {
            stockDetails = GlobalsViews.loadFxml(FXML_PATH + "PharmacyStock/MedicineSelector.fxml", MEDICINE_SELECTOR_CONTROLLER);
        }
        return stockDetails;
    }

    public Parent getUltrasoundScanForm() {
        if (ultrasoundScan == null) {
            ultrasoundScan = GlobalsViews.loadFxml(FXML_PATH + "Patients/UltrasoundScanForm.fxml", ultrasoundScanController);
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
            pharmacyStockPane = GlobalsViews.loadFxml(FXML_PATH + "PharmacyStock/PharmacyStock.fxml",PHARMACY_STOCK_CONTROLLER);
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

    public static PatientsController getPatientsController() {
        return patientsController;
    }

    public static PharmacyStockController getPharmacyStockController(){
        return PHARMACY_STOCK_CONTROLLER;
    }

    public static PrescriptionController getPrescriptionController() {
        return prescriptionController;
    }

    public static MedicalCertificateController getMedicalCertificateController() {
        return medicalCertificateController;
    }

    public static CertificateOfFitnessController getCertificateOfFitnessController() {
        return certificateOfFitnessController;
    }

    public static ClaimFormController getClaimFormController() {
        return claimFormController;
    }

    public static ProfileController getProfileController() {
        return profileController;
    }

    public static SettingsController getSettingsController() {
        return settingsController;
    }

    public static GeneralDetailsController getGeneralDetailsController() {
        return generalDetailsController;
    }

    public static AdminPanes getAdminPanes() {
        return adminPanes;
    }

    public static SearchConfigController getSearchConfigController() {
        return searchConfigController;
    }

    public static StockDetailsController getStockDetailsController(){
        return STOCK_DETAILS_CONTROLLER;
    }
}
