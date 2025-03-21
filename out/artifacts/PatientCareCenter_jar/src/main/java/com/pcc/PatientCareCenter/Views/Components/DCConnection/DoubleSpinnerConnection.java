package com.pcc.PatientCareCenter.Views.Components.DCConnection;

import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class DoubleSpinnerConnection implements DataComponentConnection {
    Spinner<Double> doubleSpinner;
    SpinnerValueFactory.DoubleSpinnerValueFactory doubleSpinnerValueFactory;

    public DoubleSpinnerConnection(Spinner<Double> doubleSpinner) {
        this.doubleSpinner = doubleSpinner;
    }

    public DoubleSpinnerConnection(Spinner<Double> doubleSpinner, double min, double max, double initialValue, double step) {
        this.doubleSpinner = doubleSpinner;
        doubleSpinnerValueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(min, max, initialValue, step);
        this.doubleSpinner.setValueFactory(doubleSpinnerValueFactory);
    }

    @Override
    public void setData(Object data) {
        if (data instanceof EmptyData) {
            doubleSpinner.getValueFactory().setValue(doubleSpinnerValueFactory.getMin());
        } else
            doubleSpinner.getValueFactory().setValue((Double) data);
    }

    @Override
    public Object getData() {
        return doubleSpinner.valueProperty().getValue();
    }
}
