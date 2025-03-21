package com.pcc.PatientCareCenter.Views.Components.DCConnection;

import javafx.scene.control.CheckBox;

public class BooleanCheckboxConnection implements DataComponentConnection{
    CheckBox checkBox;

    public BooleanCheckboxConnection(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    @Override
    public void setData(Object data) {
        if(data==null){
            checkBox.setSelected(false);
        }else if(data instanceof Boolean){
            checkBox.setSelected((Boolean)data);
        }else if(data instanceof String){
            checkBox.setSelected(Boolean.getBoolean(((String) data).toLowerCase()));
        }else if(data instanceof EmptyData){
            checkBox.setSelected(false);
        }
    }

    @Override
    public Object getData() {
        return checkBox.isSelected();
    }
}
