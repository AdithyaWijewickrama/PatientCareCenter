package com.pcc.PatientCareCenter.Model;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.MenuItem;

public class MedicineListCell extends ListCell<Medicine> {
    @Override
    protected void updateItem(Medicine medicine, boolean empty) {
        super.updateItem(medicine, empty);
        if (empty || medicine == null) {
            setText(null);
        } else {
            setText(medicine.getName() + " " + medicine.getValues().toString());
            ContextMenu contextMenu = new ContextMenu();
            MenuItem deleteItem = new MenuItem("Delete");
            deleteItem.setOnAction(e -> {
                getListView().getItems().remove(medicine);
            });
            contextMenu.getItems().add(deleteItem);
            setContextMenu(contextMenu);
        }
    }
    public void addItem(Medicine medicine){
        updateItem(medicine,false);
    }
}