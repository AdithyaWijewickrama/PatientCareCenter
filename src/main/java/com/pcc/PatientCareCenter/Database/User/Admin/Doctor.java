package com.pcc.PatientCareCenter.Database.User.Admin;

import com.pcc.PatientCareCenter.Database.DBObject;
import com.pcc.PatientCareCenter.Database.User.User;
import com.pcc.PatientCareCenter.Database.User.UserType;
import com.pcc.PatientCareCenter.Model.Sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Doctor implements DBObject {
    private static Doctor currentDoctor;
    private ResultSet resultSet;
    private int doctorId;

    private Doctor(int doctorId) {
        this.doctorId = doctorId;
    }

    public static Doctor getCurrentDoctor() {
        return currentDoctor;
    }

    public static Doctor getAdmin(int userId) throws SQLException {
        List<Object> row = Sql.getInstance().getRow("SELECT doctor_id,name,occupation FROM doctor WHERE user_id=?", userId);
        return new Doctor((int)row.get(0));
    }

    public int getDoctorId() {
        return doctorId;
    }

    public String getName() throws SQLException {
        return resultSet.getString("name");
    }

    public String getOccupation() throws SQLException {
        return resultSet.getString("occupation");
    }

    public static void setCurrentAdmin(Doctor currentDoctor) {
        Doctor.currentDoctor = currentDoctor;
    }

    public static Doctor createAdminAccount(User user, String name, String occupation) throws SQLException {
        if (user.getUserType() != UserType.DOCTOR) {
            throw new RuntimeException("Cannot create admin profile for " + user.getUserType().getName() + " users");
        } else {
            int doctorId = (int) Sql.getInstance().getObject("INSERT INTO doctor (user_id,name,occupation) VALUES(?,?,?) RETURNING doctor_id", user.getUserId(), name, occupation);
            return new Doctor(doctorId);
        }
    }

    @Override
    public void load() throws SQLException {
        resultSet=Sql.getInstance().executeQuery("SELECT name,occupation FROM doctor WHERE doctor_id=?", doctorId);
        resultSet.next();
    }

    @Override
    public ResultSet loadAndGetData() throws SQLException {
        load();
        return resultSet;
    }

    @Override
    public ResultSet getData() throws SQLException {
        return resultSet;
    }
}
