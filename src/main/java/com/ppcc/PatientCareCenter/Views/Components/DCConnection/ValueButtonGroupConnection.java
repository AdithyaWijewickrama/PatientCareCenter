package com.ppcc.PatientCareCenter.Views.Components.DCConnection;

import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

import java.util.Optional;

public class ValueButtonGroupConnection implements DataComponentConnection{
    ToggleGroup toggleGroup;

    public ValueButtonGroupConnection(ToggleGroup toggleGroup) {
        this.toggleGroup = toggleGroup;
    }

    @Override
    public void setData(Object data) {
        toggleGroup.getToggles().stream().filter(toggle->data.equals(((RadioButton)toggle).getText())).findFirst().get().setSelected(true);
    }

    @Override
    public Object getData() {
        Optional<Toggle> toggle = toggleGroup.getToggles().stream().filter(Toggle::isSelected).findFirst();
        if(toggle.isPresent()){
            RadioButton radioButton = (RadioButton) toggle.get();
            return radioButton.getText();
        }
        return null;
    }
}
