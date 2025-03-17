package com.ppcc.PatientCareCenter.Views.Components.PccTable;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.util.Arrays;

public class PatientsButtonCell extends DynamicNodeCell<HBox> {

    public PatientsButtonCell(Button... buttons) {
        super(new HBox());
        node.setSpacing(5);
        node.getChildren().addAll(Arrays.copyOf(buttons,buttons.length));
    }
    private PatientsButtonCell(){

    }

}