package com.pcc.PatientCareCenter.Views.Components.DCConnection;

import com.pcc.PatientCareCenter.Model.Sql;

import javax.net.ssl.SSLContext;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ResultConnection {
    SQLQuery insert;
    SQLQuery update;
    SQLQuery select;
    SQLQuery delete;
    DataComponentConnection[] connections;

    public ResultConnection(DataComponentConnection[] connections) {
        this.connections = connections;
    }

    public ResultConnection(SQLQuery select, SQLQuery insert, SQLQuery update, SQLQuery delete, DataComponentConnection[] connections) {
        this.insert = insert;
        this.update = update;
        this.select = select;
        this.delete = delete;
        this.connections = connections;
    }

    public SQLQuery getInsert() {
        return insert;
    }

    public void setInsert(SQLQuery insert) {
        this.insert = insert;
    }

    public SQLQuery getUpdate() {
        return update;
    }

    public void setUpdate(SQLQuery update) {
        this.update = update;
    }

    public SQLQuery getSelect() {
        return select;
    }

    public void setSelect(SQLQuery select) {
        this.select = select;
    }

    public SQLQuery getDelete() {
        return delete;
    }

    public void setDelete(SQLQuery delete) {
        this.delete = delete;
    }

    public DataComponentConnection[] getConnections() {
        return connections;
    }

    public void setConnections(DataComponentConnection[] connections) {
        this.connections = connections;
    }

    public void clear() {
        for (DataComponentConnection connection : connections) {
            connection.setData(new EmptyData());
        }
    }

    public static String joinColumns(String[] columns) {
        return String.join(",", columns);
    }

    public void loadDataFromDatabase() throws SQLException {
        List<Object> row = getList(select);
        for (int i = 0; i < row.size(); i++) {
            connections[i].setData(row.get(i));
        }
    }

    public List<Object> getList(SQLQuery query) throws SQLException {
        if (query.getParams() != null) {
            return select.getQueryReturnType() == QueryReturnType.ROW ? Sql.getInstance().getRow(query.getQueryString(), query.getParams()) :
                    select.getQueryReturnType() == QueryReturnType.COLUMN ? Sql.getInstance().getColumn(query.getQueryString(), query.getParams()) : Sql.getInstance().getRow(query.getQueryString(), query.getParams());
        }
        return select.getQueryReturnType() == QueryReturnType.ROW ? Sql.getInstance().getRow(query.getQueryString()) :
                select.getQueryReturnType() == QueryReturnType.COLUMN ? Sql.getInstance().getColumn(query.getQueryString()) : Sql.getInstance().getRow(query.getQueryString());
    }

    public Object insertToDataBase() throws SQLException {
        Object[] array = getDataArray(insert);
        if (insert.getQueryReturnType() == QueryReturnType.SINGLE_VALUE)
            return Sql.getInstance().getObject(insert.getQueryString(), array);
        return Sql.getInstance().execute(insert.getQueryString(), array);
    }

    public void deleteFromDataBase() throws SQLException {
        Sql.getInstance().execute(delete.getQueryString(), delete.getParams());
    }

    private Object[] getDataArray(SQLQuery s) {
        List<Object> objectStream = new java.util.ArrayList<>(Arrays.stream(connections).map(DataComponentConnection::getData).toList());
        if (s.getParams() != null) {
            objectStream.addAll(Arrays.asList(s.getParams()));
        }
        return objectStream.toArray();
    }

    private String getColumnsWithParamString(String[] columns) {
        return String.join(",", Arrays.stream(columns).map(s -> s + "=?").toArray(String[]::new));
    }

    public void updateToDataBase() throws SQLException {
        Object[] array = getDataArray(update);
        Sql.getInstance().execute(update.getQueryString(), array);
    }

}
