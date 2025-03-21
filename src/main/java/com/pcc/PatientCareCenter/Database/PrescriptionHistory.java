package com.pcc.PatientCareCenter.Database;

import com.pcc.PatientCareCenter.Model.Sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionHistory implements DBObject {
    int id;
    String description;
    LocalDate date;
    int patientId;
    ResultSet data;

    private PrescriptionHistory(int id) throws SQLException {
        this.id=id;
        load();
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getPatientId() {
        return patientId;
    }

    @Override
    public void load() throws SQLException {
        data = Sql.getInstance().executeQuery("SELECT * FROM prescription WHERE id=?", id);
        data.next();
        id=data.getInt("id");
        description=data.getString("description");
        date=data.getDate("date").toLocalDate();
        patientId=data.getInt("patient_id");
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
    public static List<PrescriptionHistory> getPrescriptionHistories(int patientId) throws SQLException {
        List<PrescriptionHistory> prescriptionHistories=new ArrayList<>();
        List<Object> column = Sql.getInstance().getColumn("SELECT id FROM prescription WHERE patient_id=?", patientId);
        if(column==null)return null;
        column.forEach(o -> {
            try {
                prescriptionHistories.add(new PrescriptionHistory((int)o));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        return prescriptionHistories;
    }
}
