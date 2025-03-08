package com.ppcc.PatientCareCenter.Controllers.Admin;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    private StringProperty selectedPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectedPane = new SimpleStringProperty();
        selectedPane.addListener((observable, oldValue, newValue) -> {
            switch (newValue){

            }
        });
    }
}
