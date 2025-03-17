package com.ppcc.PatientCareCenter.Views.Components.PccTable;

import javafx.scene.Node;
import javafx.scene.control.TableCell;

public  class DynamicNodeCell<NodeType> extends TableCell<DynamicTableRow, Void> {
    protected NodeType node;

    public DynamicNodeCell(NodeType node) {
        this.node = node;
    }

    protected DynamicNodeCell(){

    }

    @Override
    protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            setGraphic((Node) node);
        }
    }
}
