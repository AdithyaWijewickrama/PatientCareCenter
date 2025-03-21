package com.pcc.PatientCareCenter.Views.Components.PccTable;

import com.pcc.PatientCareCenter.Views.Components.PccComponent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PccTable extends PccComponent {

    private final TableView<DynamicTableRow> tableView;
    private final ObservableList<DynamicTableRow> data;

    public PccTable(Node node) {
        super(node);
        tableView = (TableView<DynamicTableRow>) node;
        data = FXCollections.observableArrayList();
    }

    public static List<TableColumn<DynamicTableRow, ?>> getColumns(ResultSet resultSet) throws SQLException {
        List<TableColumn<DynamicTableRow, ?>> columns = new ArrayList<>();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            String columnName = metaData.getColumnName(i);
            TableColumn<DynamicTableRow, Object> column = new TableColumn<>(columnName);
            column.setCellValueFactory(cellData -> {
                DynamicTableRow row = cellData.getValue();
                return new javafx.beans.property.SimpleObjectProperty<>(row.getData(columnName));
            });
            columns.add(column);
        }
        return columns;
    }


    public static TableColumn<DynamicTableRow, Void> getNodeColumn(String columnName, Callback<TableColumn<DynamicTableRow, Void>, TableCell<DynamicTableRow, Void>> callback) {
        TableColumn<DynamicTableRow, Void> cell = new TableColumn<>(columnName);
        cell.setCellFactory(callback);
        return cell;
    }

    public void setTableColumns(List<TableColumn<DynamicTableRow, ?>> columns) throws SQLException {
        tableView.getColumns().addAll(columns);
    }

    public void addTableColumn(TableColumn<DynamicTableRow, Void> column) throws SQLException {
        tableView.getColumns().add(column);
    }

    public void clear(){
        tableView.getColumns().clear();
        data.clear();
    }

    public void resultSetToPccTable(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
//        tableView.getColumns().addAll(getColumns(resultSet));
//        addTableColumn(getNodeColumn("A",e->new PatientsButtonCell(new Button("ds"),new Button("ds"))));
        while (resultSet.next()) {
            DynamicTableRow row = new DynamicTableRow();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                Object value = resultSet.getObject(i);
                row.addData(columnName, value);
            }
            data.add(row);
        }
        tableView.setItems(data);
    }

}
