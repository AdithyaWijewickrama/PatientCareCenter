package com.pcc.PatientCareCenter.Views.Components.DCConnection;

import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

import java.util.Optional;

public class ValueButtonGroupConnection implements DataComponentConnection {
    ToggleGroup toggleGroup;

    public ValueButtonGroupConnection(ToggleGroup toggleGroup) {
        this.toggleGroup = toggleGroup;
    }

    @Override
    public void setData(Object data) {
        Optional<Toggle> toggles = toggleGroup.getToggles().stream().filter(toggle -> {
            String val = ((RadioButton) toggle).getText();
            System.out.println(data+":"+val);
            return data.equals(val);
        }).findFirst();
        if (toggles.isPresent()) {
            RadioButton radioButton = (RadioButton) toggles.get();
        }
    }

    @Override
    public Object getData() {
        Optional<Toggle> toggle = toggleGroup.getToggles().stream().filter(Toggle::isSelected).findFirst();
        if (toggle.isPresent()) {
            RadioButton radioButton = (RadioButton) toggle.get();
            return radioButton.getText();
        }
        return null;
    }
}
