package com.ppcc.PatientCareCenter.Views.Components.PccDialog;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class PccDataPane extends PccPane<VBox> {
    private Map<String, DataNode> dataMap = new HashMap<>();

    public PccDataPane(String header, String footer) {
        super(header, footer, new VBox());
    }

    public void resultSetToDialog(ResultSet resultSet) throws SQLException {
        int columnCount = resultSet.getMetaData().getColumnCount();
        for (int i = 0; i < columnCount; i++) {
            String columnName = resultSet.getMetaData().getColumnName(i + 1);
            Object data = resultSet.getObject(i + 1);
            DataNode dataNode = new DataNode(data);
            dataMap.put(columnName, dataNode);
        }
        dataMap.forEach((key, value) -> {
            HBox row = new HBox();
            row.getChildren().add(getNormalLabel(capitalizeSnakeCase(key)));
            row.getChildren().add(value.getNode());
            pane.getChildren().add(row);
        });
    }

    public Label getNormalLabel(String text) {
        return getLabel(text,"normal-text-p");
    }

    public Label getLabel(String text, String styleClass) {
        Label label = new Label(text);
        label.getStyleClass().add(styleClass);
        return label;
    }

    public static String capitalizeSnakeCase(String string) {
        String[] words = string.split("_");
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            words[i] = (word.substring(0, 1).toUpperCase()) + (word.length() > 1 ? word.substring(1).toLowerCase() : "");
        }
        return String.join(" ", words);
    }

}
