package com.pcc.PatientCareCenter.Database;

import com.pcc.PatientCareCenter.Model.Sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PPDetails implements DBObject {
    ResultSet data;
    int ppId;
    String name;
    private static PPDetails ppDetails;

    private PPDetails(int ppId) {
        this.ppId = ppId;
    }

    @Override
    public void load() throws SQLException {
        data = Sql.getInstance().executeQuery("SELECT * FROM pp_details WHERE pp_id=?", ppId);
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

    public static int getPPId(int doctorId) throws SQLException {
        return (int) Sql.getInstance().getObject("SELECT pp_id FROM pp_details WHERE doctor_id=?", doctorId);
    }


    public static PPDetails getPpDetails(int ppId) throws SQLException {
        PPDetails ppDetails1=new PPDetails(ppId);
        ppDetails1.load();
        return ppDetails1;
    }

    public static PPDetails getPpDetailsOfDoctor(int doctorId) throws SQLException {
        PPDetails ppDetails1=new PPDetails(getPPId(doctorId));
        ppDetails1.load();
        return ppDetails1;
    }

    public static PPDetails createPPpDetails(String name, String email, String address, String telephone, int doctorID) throws SQLException {
        int ppId = (int) Sql.getInstance().getObject("INSERT INTO pp_details (name,address,email,telephone,doctor_id) VALUES(?,?,?,?,?) RETURNING pp_id", name, email, address, telephone, doctorID);
        return getPpDetails(ppId);
    }

    public static void setCurrentPP(PPDetails ppDetails) {
        PPDetails.ppDetails = ppDetails;
    }

    public static PPDetails getCurrentPP() {
        return ppDetails;
    }
}