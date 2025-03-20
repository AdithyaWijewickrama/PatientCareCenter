package com.pcc.PatientCareCenter.Views.Components.DCConnection;

import javafx.scene.control.TextField;

public class StringTextfieldConnection implements DataComponentConnection{
    private final TextField textField;
    public StringTextfieldConnection(TextField textField){
        this.textField=textField;
    }

    @Override
    public void setData(Object data) {
        if(data==null)return;
        if (data instanceof EmptyData) textField.setText("");
        else
            textField.setText(data.toString());
    }

    @Override
    public Object getData() {
        return textField.getText();
    }
}

