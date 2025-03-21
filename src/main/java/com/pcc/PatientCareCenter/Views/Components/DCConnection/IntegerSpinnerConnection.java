package com.pcc.PatientCareCenter.Views.Components.DCConnection;

import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class IntegerSpinnerConnection implements DataComponentConnection {
    Spinner<Integer> integerSpinner;
    SpinnerValueFactory.IntegerSpinnerValueFactory integerSpinnerValueFactory;

    public IntegerSpinnerConnection(Spinner<Integer> integerSpinner) {
        this.integerSpinner = integerSpinner;
    }

    public IntegerSpinnerConnection(Spinner<Integer> integerSpinner, int min, int max, int initialValue, int step) {
        this.integerSpinner = integerSpinner;
        integerSpinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max, initialValue, step);
        this.integerSpinner.setValueFactory(integerSpinnerValueFactory);
    }


    @Override
    public void setData(Object data) {
        if (data instanceof EmptyData) {
            System.out.println();
            integerSpinner.getValueFactory().setValue(integerSpinnerValueFactory.getMin());
        } else
            integerSpinner.getValueFactory().setValue((Integer) data);
    }

    @Override
    public Object getData() {
        return integerSpinner.getValue();
    }
}
