package com.pcc.PatientCareCenter.Controllers;

import com.pcc.PatientCareCenter.Controllers.Admin.LetterHeadController;
import com.pcc.PatientCareCenter.Controllers.Admin.Patients.*;
import com.pcc.PatientCareCenter.Controllers.Admin.PharmacyStock.MedicineSelectorController;
import com.pcc.PatientCareCenter.Controllers.Admin.PharmacyStock.PharmacyStockController;
import com.pcc.PatientCareCenter.Controllers.Admin.PharmacyStock.StockDetailsController;
import com.pcc.PatientCareCenter.Controllers.Admin.ProfileController;
import com.pcc.PatientCareCenter.Controllers.Admin.SettingsController;

public class AdminControllers {

    //Controllers
    public static final PatientsController PATIENTS_CONTROLLER = new PatientsController();
    public static final GeneralDetailsController GENERAL_DETAILS_CONTROLLER = new GeneralDetailsController();
    public static final PrescriptionController PRESCRIPTION_CONTROLLER = new PrescriptionController();
    public static final SearchConfigController SEARCH_CONFIG_CONTROLLER = new SearchConfigController();
    public static final MedicalCertificateController MEDICAL_CERTIFICATE_CONTROLLER = new MedicalCertificateController();
    public static final CertificateOfFitnessController CERTIFICATE_OF_FITNESS_CONTROLLER = new CertificateOfFitnessController();
    public static final ProfileController PROFILE_CONTROLLER = new ProfileController();
    public static final SettingsController settingsController = new SettingsController();
    public static final ClaimFormController claimFormController = new ClaimFormController();
    public static final UltrasoundScanFormController ultrasoundScanController = new UltrasoundScanFormController();
    public static final StockDetailsController STOCK_DETAILS_CONTROLLER = new StockDetailsController();
    public static final PharmacyStockController PHARMACY_STOCK_CONTROLLER = new PharmacyStockController();
    public static final MedicineSelectorController MEDICINE_SELECTOR_CONTROLLER = new MedicineSelectorController();
    public static final LetterHeadController LETTER_HEAD_CONTROLLER=new LetterHeadController();

    public static PatientsController getPatientsController() {
        return PATIENTS_CONTROLLER;
    }

    public static PharmacyStockController getPharmacyStockController() {
        return PHARMACY_STOCK_CONTROLLER;
    }

    public static PrescriptionController getPrescriptionController() {
        return PRESCRIPTION_CONTROLLER;
    }

    public static MedicalCertificateController getMedicalCertificateController() {
        return MEDICAL_CERTIFICATE_CONTROLLER;
    }

    public static CertificateOfFitnessController getCertificateOfFitnessController() {
        return CERTIFICATE_OF_FITNESS_CONTROLLER;
    }

    public static ClaimFormController getClaimFormController() {
        return claimFormController;
    }

    public static UltrasoundScanFormController getUltrasoundScanController() {
        return ultrasoundScanController;
    }

    public static LetterHeadController getLetterHeadController(){
        return  LETTER_HEAD_CONTROLLER;
    }

    public static ProfileController getProfileController() {
        return PROFILE_CONTROLLER;
    }

    public static SettingsController getSettingsController() {
        return settingsController;
    }

    public static GeneralDetailsController getGeneralDetailsController() {
        return GENERAL_DETAILS_CONTROLLER;
    }
}
