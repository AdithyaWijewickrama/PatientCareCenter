package com.pcc.PatientCareCenter.Database.User.Admin;

import com.pcc.PatientCareCenter.Database.User.User;
import com.pcc.PatientCareCenter.Database.User.UserType;
import com.pcc.PatientCareCenter.Model.Sql;

import java.sql.SQLException;
import java.util.List;

public class Admin{
    private static Admin currentAdmin;
    private int adminId;
    private String name;
    private String occupation;

    private Admin(int adminId, String name, String occupation) {
        this.adminId = adminId;
        this.name = name;
        this.occupation = occupation;
    }

    public static Admin getCurrentAdmin() {
        return currentAdmin;
    }

    public static Admin getAdmin(int userId) throws SQLException {
        List<Object> row = Sql.getInstance().getRow("SELECT doctor_id,name,occupation FROM doctor WHERE user_id=?", userId);
        return new Admin((int)row.get(0),(String)row.get(1),(String)row.get(2));
    }

    public int getAdminId() {
        return adminId;
    }

    public String getName() {
        return name;
    }

    public String getOccupation() {
        return occupation;
    }

    public static void setCurrentAdmin(Admin currentAdmin) {
        Admin.currentAdmin = currentAdmin;
    }

    public static Admin createAdminAccount(User user, String name, String occupation) throws SQLException {
        if (user.getUserType() != UserType.DOCTOR) {
            throw new RuntimeException("Cannot create admin profile for " + user.getUserType().getName() + " users");
        } else {
            int doctorId = (int) Sql.getInstance().getObject("INSERT INTO doctor (user_id,name,occupation) VALUES(?,?,?) RETURNING doctor_id", user.getUserId(), name, occupation);
            Admin admin = new Admin(doctorId, name, occupation);
            return admin;
        }
    }
}
