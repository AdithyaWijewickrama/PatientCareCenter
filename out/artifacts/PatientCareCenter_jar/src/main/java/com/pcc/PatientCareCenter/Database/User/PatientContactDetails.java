package com.pcc.PatientCareCenter.Database.User;

import com.pcc.PatientCareCenter.Database.DBObject;
import com.pcc.PatientCareCenter.Model.Sql;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientContactDetails implements DBObject {
    ResultSet data;
    int patientId;
    private static PatientContactDetails patientContactDetails;
    public PatientContactDetails(int patientId) throws SQLException {
        this.patientId = patientId;
        load();
    }

    public String getAddress() throws SQLException {
        return data.getString("street_address");
    }

    @Override
    public void load() throws SQLException {
        data = Sql.getInstance().executeQuery("SELECT * FROM patient_contact_details WHERE patient_id=?", patientId);
        data.next();
    }

    @Override
    public ResultSet loadAndGetData() throws SQLException {
        load();
        return data;
    }

    @Override
    public ResultSet getData() throws SQLException {
        if (data == null) {
            load();
        }
        return data;
    }

    public static void setPatientContactDetails(PatientContactDetails patientContactDetails) {
        PatientContactDetails.patientContactDetails = patientContactDetails;
    }

    public static PatientContactDetails getCurrentPatientsContactDetails(){
        return patientContactDetails;
    }
}
