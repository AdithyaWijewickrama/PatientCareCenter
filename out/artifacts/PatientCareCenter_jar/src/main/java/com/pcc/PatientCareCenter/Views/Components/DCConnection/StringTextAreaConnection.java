package com.pcc.PatientCareCenter.Views.Components.DCConnection;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class StringTextAreaConnection implements DataComponentConnection {
    private final TextArea textArea;

    public StringTextAreaConnection(TextArea textField) {
        this.textArea = textField;
    }

    @Override
    public void setData(Object data) {
        if (data == null) return;
        if (data instanceof EmptyData) textArea.setText("");
        else
            textArea.setText(data.toString());
    }

    @Override
    public Object getData() {
        return textArea.getText();
    }
}

