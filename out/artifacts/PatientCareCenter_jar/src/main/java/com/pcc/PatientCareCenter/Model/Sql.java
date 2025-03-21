package com.pcc.PatientCareCenter.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sql {

    private static Sql instance;
    private final Connection connection;
    private PreparedStatement preparedStatement;
    public final String url = "jdbc:postgresql://localhost:5432/pcc";
    public final String user = "postgres";
    public final String password = "Password";

    public Sql() {
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static Sql getInstance() {
        if (instance == null) {
            instance = new Sql();
        }
        return instance;
    }

    public boolean execute(String query) throws SQLException {
        preparedStatement = connection.prepareStatement(query);
        return preparedStatement.execute();
    }

    public boolean execute(PreparedStatement preparedStatement) throws SQLException {
        return preparedStatement.execute();
    }

    public boolean execute(String query, Object... values) throws SQLException {
        System.out.println(query+ Arrays.toString(values));
       preparedStatement = connection.prepareStatement(query);
        return execute(prepareValues(query, values));
    }

    public ResultSet executeQuery(String query) throws SQLException {
        preparedStatement = connection.prepareStatement(query);
        return executeQuery(preparedStatement);
    }

    public ResultSet executeQuery(PreparedStatement pst) {
        try {
            return pst.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet executeQuery(String query, Object... values) throws SQLException {
        preparedStatement = connection.prepareStatement(query);
        for (int i = 0; i < values.length; i++) {
            preparedStatement.setObject(i + 1, values[i]);
        }
        return executeQuery(preparedStatement);
    }

    public int executeUpdate(String query) throws SQLException {
        preparedStatement = connection.prepareStatement(query);
        return executeUpdate(preparedStatement);
    }

    public int executeUpdate(PreparedStatement pst) throws SQLException {
        return pst.executeUpdate();
    }

    public int executeUpdate(String query, Object... values) throws SQLException {
        return executeUpdate(prepareValues(query, values));
    }

    public PreparedStatement prepareValues(String query, Object[] values) {
        try {
            preparedStatement = connection.prepareStatement(query);
            if (values != null) {
                for (int i = 0; i < values.length; i++) {
                    preparedStatement.setObject(i + 1, values[i]);
                }
            }
//            System.out.println(query);
            return preparedStatement;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Object> getRow(ResultSet resultSet) throws SQLException {
        List<Object> row = null;
        if (resultSet.next()) {
            row = new ArrayList<>();
            for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                row.add(resultSet.getObject(i));
            }
        }
        return row;
    }

    public List<Object> getRow(PreparedStatement pst) throws SQLException {
        return getRow(executeQuery(pst));
    }

    public List<Object> getRow(String query, Object... values) throws SQLException {
        System.out.println(query + Arrays.toString(values));
        return getRow(prepareValues(query, values));
    }


    public List<Object> getColumn(ResultSet resultSet) throws SQLException {
        List<Object> column = null;
        column = new ArrayList<>();
        while (resultSet.next()) {
            column.add(resultSet.getObject(1));
        }
        return column;
    }

    public List<Object> getColumn(PreparedStatement pst) throws SQLException {
        return getColumn(executeQuery(pst));
    }

    public List<Object> getColumn(String query, Object... values) throws SQLException {
        System.out.println(query + Arrays.toString(values));
        return getColumn(prepareValues(query, values));
    }

    public Object getObject(ResultSet resultSet) throws SQLException {
        Object obj = null;
        if (resultSet.next()) {
            obj = resultSet.getObject(1);
        }
        return obj;
    }

    public Object getObject(PreparedStatement preparedStatement) throws SQLException {
        return getObject(executeQuery(preparedStatement));
    }

    public Object getObject(String query, Object... values) throws SQLException {
        System.out.println(query+ Arrays.toString(values));
        return getObject(prepareValues(query, values));
    }

    public static void main(String[] args) {
        try {
            List<Object> row = Sql.getInstance().getRow("SELECT * FROM user WHERE user_id=?", 2);
//            System.out.println(row);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
