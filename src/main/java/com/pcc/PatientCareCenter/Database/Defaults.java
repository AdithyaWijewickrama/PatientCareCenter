package com.pcc.PatientCareCenter.Database;

import com.pcc.PatientCareCenter.Database.User.User;
import com.pcc.PatientCareCenter.Model.Sql;

import java.sql.SQLException;

public class Defaults {
    public static String getDefault(String id) throws SQLException {
        Object object = Sql.getInstance().getObject("SELECT value FROM defaults WHERE id=? AND pp_id=?", id, PPDetails.getCurrentPP().getPpId());
        if (object == null) return null;
        return (String) object;
    }

    public static void setDefault(String id, String value) throws SQLException {
        Sql.getInstance().execute("""
                INSERT INTO defaults
                    (id,value,pp_id)
                SET (?,?,?)
                ON CONFLICT(id,pp_id)
                DO UPDATE SET value=EXCLUDE.value
                """, value, id, PPDetails.getCurrentPP().getPpId());
    }
}
