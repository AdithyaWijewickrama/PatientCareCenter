package com.pcc.PatientCareCenter.Views.Components.DCConnection;

import com.pcc.PatientCareCenter.Model.PasswordEncryptor;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class StringPasswordFieldConnection implements DataComponentConnection{
    private final PasswordField textField;
    public StringPasswordFieldConnection(PasswordField textField){
        this.textField=textField;
    }

    @Override
    public void setData(Object data) {
        if(data==null)return;
        if (data instanceof EmptyData) textField.setText("");
        else {
            try {
                textField.setText(PasswordEncryptor.decrypt(data.toString()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Object getData() {
        try {
            return PasswordEncryptor.encrypt(textField.getText());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

