package com.pcc.PatientCareCenter.Views.Components.DCConnection;

import javafx.scene.control.DatePicker;

import java.time.LocalDate;

public class DateDatePickerConnection implements DataComponentConnection{
    DatePicker datePicker;

    public DateDatePickerConnection(DatePicker datePicker) {
        this.datePicker = datePicker;
    }

    @Override
    public void setData(Object data) {
        if(data==null)return;
        if(data instanceof EmptyData)datePicker.setValue(LocalDate.now());
        datePicker.valueProperty().set(LocalDate.parse(data.toString()));
    }

    @Override
    public Object getData() {
        return datePicker.getValue();
    }
}
