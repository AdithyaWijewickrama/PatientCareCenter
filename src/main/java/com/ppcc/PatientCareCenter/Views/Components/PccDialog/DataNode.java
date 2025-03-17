package com.ppcc.PatientCareCenter.Views.Components.PccDialog;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.util.Date;

public class DataNode {
    private ObjectProperty<Object> dataObject;
    private Node node;

    public DataNode(Object data) {
        this.dataObject = new SimpleObjectProperty<>(data);
        if (data instanceof String || data instanceof Integer) {
            TextField textField = new TextField(data.toString());
            textField.setOnInputMethodTextChanged(event -> {
                dataObject.set(textField.getText());
            });
        } else if (data instanceof Date) {
            DatePicker datePicker = new DatePicker((LocalDate) data);
            datePicker.valueProperty().addListener(event -> {
                dataObject.set(datePicker.getValue());
            });
        }
    }

    public DataNode(Object data, ComboBox<Object> comboBox) {
        this.dataObject = new SimpleObjectProperty<>(data);
        comboBox.setOnAction(event -> {
            dataObject.set(comboBox.getValue());
        });
    }

    public Object getData() {
        return dataObject.get();
    }

    public Node getNode() {
        return node;
    }
}
