package com.pcc.PatientCareCenter.Model;


public class ErrorHandler {

    public static void handle(Error e) {
//        log.error(String.valueOf(e));
        throw new RuntimeException(e);
    }

    public static void handle(Exception e) {
//        log.error(String.valueOf(e));
        throw new RuntimeException(e);
    }

}
