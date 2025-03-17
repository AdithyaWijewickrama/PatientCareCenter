package com.ppcc.PatientCareCenter.Model;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

public class PasswordValidator {
    private String password;

    public PasswordValidator(String password) {
        this.password = password;
    }

    public PasswordValidator() {
        this(null);
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isValid(String password) {
        setPassword(password);
        return isValid();
    }

    public boolean isValid() {
        if (password == null) {
            throw new RuntimeException("Password is null");
        }
        return password.matches(".{4,}");
    }
}
