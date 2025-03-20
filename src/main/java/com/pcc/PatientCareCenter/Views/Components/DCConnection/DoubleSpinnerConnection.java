package com.pcc.PatientCareCenter.Views.Components.DCConnection;

import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class IntegerSpinnerConnection implements DataComponentConnection {
    Spinner<Integer> integerSpinner;

    public IntegerSpinnerConnection(Spinner<Integer> integerSpinner) {
        this.integerSpinner = integerSpinner;
    }

    public IntegerSpinnerConnection(Spinner<Integer> integerSpinner,int min,int max,int initialValue) {
        this.integerSpinner = integerSpinner;
        SpinnerValueFactory.IntegerSpinnerValueFactory integerSpinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max, initialValue);
        this.integerSpinner.setValueFactory(integerSpinnerValueFactory);
    }



    @Override
    public void setData(Object data) {
        integerSpinner.getValueFactory().setValue((Integer) data);
    }

    @Override
    public Object getData() {
        return integerSpinner.getValue();
    }
}
