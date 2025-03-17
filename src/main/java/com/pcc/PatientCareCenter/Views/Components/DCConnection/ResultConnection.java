package com.pcc.PatientCareCenter.Views.Components.DCConnection;

import com.pcc.PatientCareCenter.Model.Sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ResultConnection {
    private ResultSet resultSet;
    String table;
    String[] columnNames;
    String searchString;
    Object[] searchParams;
    DataComponentConnection[] connections;

    public ResultConnection(String table, String[] columnNames, String searchString, Object[] searchParams, DataComponentConnection[] connections) {
        this.table = table;
        this.columnNames = columnNames;
        this.searchParams = searchParams;
        this.searchString = searchString;
        this.connections = connections;
        checkParams();
    }

    public Object[] getSearchParams() {
        return searchParams;
    }

    public void setSearchParams(Object[] searchParams) {
        this.searchParams = searchParams;
    }

    private void checkParams() {
        if (columnNames.length != connections.length) {
            throw new RuntimeException(String.format("Number of columns(%d) must equal to number of connections(%d)", columnNames.length, connections.length));
        }
    }

    public static String getParamString(int columnCount) {
        return columnCount == 0 ? "" : (columnCount == 1 ? "?" : ("?,".repeat(columnCount - 1)));
    }

    public static String joinColumns(String[] columns) {
        return String.join(",", columns);
    }

    public void loadDataFromDatabase() throws SQLException {
        List<Object> row = Sql.getInstance().getRow("SELECT " + joinColumns(columnNames) + " FROM " + table + " WHERE " + searchString, searchParams);
        for (int i = 0; i < row.size(); i++) {
            connections[i].setData(row.get(i));
        }
    }

    public void insertToDataBase() throws SQLException {
        Object[] array = getDataArray();
        String query = "INSERT INTO " + table + " (" + joinColumns(columnNames) + ") VALUES(" + getParamString(columnNames.length) + ")";
        System.out.printf("[insertToDataBase]\tquery:%s\tobjects:%s",query, Arrays.stream(array).map(Object::toString).toString());
        Sql.getInstance().execute(query,array);
    }

    private Object[] getDataArray() {
        return Arrays.stream(connections).map(DataComponentConnection::getData).toArray(Object[]::new);
    }

    private String getColumnsWithParamString(String[] columns){
        return String.join(",",Arrays.stream(columns).map(s->s+"=?").toArray(String[]::new));
    }

    public void updateToDataBase() throws SQLException {
        Object[] array = getDataArray();
        String query = "UPDATE " + table + " SET "+getColumnsWithParamString(columnNames)+" WHERE "+searchString;
        System.out.printf("[updateToDataBase]\tquery:%s\tobjects:%s",query, Arrays.stream(array).map(Object::toString).toString());
        Sql.getInstance().execute(query, Stream.concat(Arrays.stream(array),Arrays.stream(searchParams)));
    }

}
