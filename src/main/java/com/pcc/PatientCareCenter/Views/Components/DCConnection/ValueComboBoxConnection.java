package com.pcc.PatientCareCenter.Views.Components.DCConnection;

import javafx.scene.control.ComboBox;

public class ValueComboBoxConnection<ValueType> implements DataComponentConnection{
    ComboBox<ValueType> comboBox;

    public ValueComboBoxConnection(ComboBox<ValueType> comboBox) {
        this.comboBox = comboBox;
    }

    @Override
    public void setData(Object data) {
        comboBox.setValue((ValueType) data);
    }

    @Override
    public Object getData() {
        return comboBox.getValue();
    }
}
