package com.pcc.PatientCareCenter.Model;

import com.pcc.PatientCareCenter.Database.Server.DatabaseConfigManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Sql {

    private static Sql instance;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private String url;
    private String user;
    private String password;

    public Sql(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public void connect() throws SQLException {
        connection = tryConnection(url, user, password);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public static Connection tryConnection(String url, String userName, String password) throws SQLException {
        return DriverManager.getConnection(url, userName, password);
    }

    public static void setInstance(Sql sql) {
        instance = sql;
    }

    public Connection getConnection() {
        return connection;
    }

    public static Sql getInstance() {
        if (instance == null) {
            Map<String, String> map = DatabaseConfigManager.readConfig();
            try {
                String url = map.get("url");
                String username = map.get("username");
                String password = PasswordEncryptor.decrypt(map.get("password"));
                instance = new Sql(url, username, password);
                instance.connect();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return instance;
    }

    public boolean execute(PreparedStatement preparedStatement) throws SQLException {
        return preparedStatement.execute();
    }

    public boolean execute(String query, Object... values) throws SQLException {
        System.out.println(query + Arrays.toString(values));
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

    public int executeUpdate(PreparedStatement pst) throws SQLException {
        return pst.executeUpdate();
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
        List<Object> column;
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
        System.out.println(query + Arrays.toString(values));
        return getObject(prepareValues(query, values));
    }

}
