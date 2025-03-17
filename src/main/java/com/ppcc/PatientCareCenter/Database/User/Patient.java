package com.ppcc.PatientCareCenter.Database.User;

import com.ppcc.PatientCareCenter.Model.Sql;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Patient {
    private static Patient currentPatient;
    private final int patientId;
    private ResultSet data;

    public Patient(int patientId) {
        this.patientId = patientId;
    }

    public ResultSet getData() throws SQLException {
        if (data == null) {
            data = Sql.getInstance().executeQuery("SELECT * FROM patient_demographics WHERE patient_id=?", patientId);
        }

        return data;
    }

    public static int getPatientId(int userId) throws SQLException {
        return (int) Sql.getInstance().getObject("SELECT patient_id FROM patient_demographics WHERE user_id=?", userId);
    }

    public static Patient getCurrentPatient() {
        return currentPatient;
    }

    public static void setCurrentPatient(Patient currentPatient) {
        Patient.currentPatient = currentPatient;
    }
}
