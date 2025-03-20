package com.pcc.PatientCareCenter.Database.User;

import com.pcc.PatientCareCenter.Model.Sql;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class Patient {
    private static Patient currentPatient;
    private final int patientId;
    private ResultSet data;

    public Patient(int patientId) {
        this.patientId = patientId;
    }

    public ResultSet loadAndGetData() throws SQLException {
        load();
        return data;
    }

    public ResultSet getData() throws SQLException {
        if (data == null) {
            data = Sql.getInstance().executeQuery("SELECT * FROM public.patient_demographics WHERE patient_id=?", patientId);
            data.next();
        }
        return data;
    }

    public String getName() throws SQLException {
        return data.getString("name");
    }

    public String getGender() throws SQLException {
        return data.getString("gender");
    }
    public Patient load() throws SQLException {
        data = Sql.getInstance().executeQuery("SELECT * FROM public.patient_demographics WHERE patient_id=?", patientId);
        data.next();
        return this;
    }

    public int getAgeInYears() throws SQLException {
        LocalDate dateOfBirth = ((Date) loadAndGetData().getObject("date_of_birth")).toLocalDate();
        Period period = Period.between(dateOfBirth, LocalDate.now());
        return period.getYears();
    }

    public int getPatientId() {
        return patientId;
    }

    public List<Object> getDataList() throws SQLException {
        List<Object> list=new ArrayList<>();
        for (int i = 1; i <= data.getMetaData().getColumnCount(); i++) {
            list.add(data.getObject(i));
        }
        return list;
    }

    public static Patient getCurrentPatient() {
        return currentPatient;
    }

    public static void setCurrentPatient(Patient currentPatient) {
        Patient.currentPatient = currentPatient;
        try {
            PatientContactDetails.setPatientContactDetails(new PatientContactDetails(currentPatient.patientId));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getPatientId(int userId) throws SQLException {
        return (int) Sql.getInstance().getObject("SELECT patient_id FROM patient_demographics WHERE user_id=?", userId);
    }

    public static Patient getPatient(int userId) throws SQLException {
        return new Patient(getPatientId(userId));
    }

}
