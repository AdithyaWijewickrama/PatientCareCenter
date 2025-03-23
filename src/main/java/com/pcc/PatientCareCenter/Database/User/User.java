package com.pcc.PatientCareCenter.Database.User;

import com.pcc.PatientCareCenter.Model.PasswordEncryptor;
import com.pcc.PatientCareCenter.Model.Sql;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class User {
    private static User currentUser;
    private int user_id = -1;
    private String email;
    private String password;
    private UserType userType;


    public User(int userId) {
        this.user_id = userId;
    }

    private User(int user_id, String email, String password, UserType userType) {
        this.user_id = user_id;
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    private User(String email) {
        this.email = email;
    }

    public UserType getUserType() {
        return userType;
    }

    public int getUserId() {
        return user_id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public static User getUser(String email) throws SQLException {
        List<Object> row = Sql.getInstance().getRow("SELECT user_id,password,account_type FROM public.user where email=?", email);
        User user = null;
        if (row != null) {
            user = new User((Integer) row.get(0), email, decrypt((String) row.get(1)), UserType.getUserTypeByName((String) row.get(2)));
        }
        return user;
    }

    public static String decrypt(String encryptedPassword){
        try {
            return PasswordEncryptor.decrypt(encryptedPassword);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String encrypt(String password){
        try {
            return PasswordEncryptor.encrypt(password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static User getUser(int userId) throws Exception {
        List<Object> row = Sql.getInstance().getRow("SELECT email,password,account_type FROM public.user where user_id=?", userId);
        User user = null;
        if (row != null) {
            user = new User(userId, decrypt((String) row.get(0)), (String) row.get(1), UserType.getUserTypeByName((String) row.get(2)));
        }
        return user;
    }

    public static void setCurrentUser(User user) {
        if (user == null) {
            throw new RuntimeException("User is null");
        }
        currentUser = user;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static boolean isRegisteredEmail(String email) throws SQLException {
        return Sql.getInstance().execute("SELECT user_id FROM public.user WHERE email=?", email);
    }

    public static boolean authenticateUser(String email, String password) throws Exception {
        return Sql.getInstance().execute("SELECT email FROM public.user WHERE email=? AND password=?", email, decrypt(password));
    }

    public static User createUser(String email, String password, UserType userType) throws Exception {
        int userId= (int) Sql.getInstance().getObject("INSERT INTO public.user (email,password,account_type,date_created) VALUES(?,?,?,?) RETURNING user_id", email, encrypt(password), userType.getName(), LocalDate.now());
        return new User(userId, email, password, userType);
    }
}
