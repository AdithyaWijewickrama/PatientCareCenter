package com.ppcc.PatientCareCenter.Database.User;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public enum UserType {
    DOCTOR("Doctor",1),
    PATIENT("Patient",2);

    private final String name;
    private final int level;

    UserType(String name,int level){
        this.name=name;
        this.level=level;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public static UserType getUserTypeByName(String name){
        AtomicReference<UserType> ut= new AtomicReference<>();
        Arrays.stream(UserType.values()).forEach(userType -> {
            if(userType.name.equals(name)){
                ut.set(userType);
            }
        });
        return ut.get();
    }
}
