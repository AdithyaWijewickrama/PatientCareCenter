package com.pcc.PatientCareCenter.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DBObject {
    void load() throws SQLException;

    ResultSet loadAndGetData() throws SQLException;

    ResultSet getData() throws SQLException;
}
