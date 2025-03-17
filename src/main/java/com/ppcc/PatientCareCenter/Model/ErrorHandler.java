package com.ppcc.PatientCareCenter.Model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ErrorHandler {
    private static final Logger log = LoggerFactory.getLogger(ErrorHandler.class);

    public static void handle(Error e) {
//        log.error(String.valueOf(e));
        throw new RuntimeException(e);
    }

    public static void handle(Exception e) {
//        log.error(String.valueOf(e));
        throw new RuntimeException(e);
    }

}
