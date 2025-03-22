package com.pcc.PatientCareCenter.Controllers.Admin.PharmacyStock;

import com.pcc.PatientCareCenter.Controllers.Admin.Patients.GeneralDetailsType;
import com.pcc.PatientCareCenter.Database.Stock;
import com.pcc.PatientCareCenter.Model.Model;
import com.pcc.PatientCareCenter.Model.Sql;
import com.pcc.PatientCareCenter.Views.Components.PccTable.ButtonElements;
import com.pcc.PatientCareCenter.Views.Components.PccTable.DynamicTableRow;
import com.pcc.PatientCareCenter.Views.Components.PccTable.PatientsButtonCell;
import com.pcc.PatientCareCenter.Views.Components.PccTable.PccTable;
import com.pcc.PatientCareCenter.Views.GlobalsViews;
import com.pcc.PatientCareCenter.Views.Panes.AdminPanes;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class PharmacyStockController implements Initializable {
    public Label pharmacyStock;
    public TextField searchTextField;
    public ToggleButton addStock;
    public TableView<DynamicTableRow> tableView;

    private PccTable pccTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pccTable = new PccTable(tableView);
        tableView.getSelectionModel().selectedIndexProperty().addListener((e) -> {
            try {
                patientSelected();
            } catch (SQLException ex) {
                GlobalsViews.showErrorAlert(ex.getLocalizedMessage());
                throw new RuntimeException(ex);
            }
        });
        addStock.setOnAction(event -> {
            Model.getInstance().getCommonViewFactory().getAdminViewFactory().getAdmin().showStockDetails();
            AdminPanes.getStockDetailsController().clear();
        });
        searchTextField.textProperty().addListener(event -> {
            try {
                tableLoad(getTableQuery());
            } catch (SQLException e) {
                GlobalsViews.showErrorAlert(e.getLocalizedMessage());
                throw new RuntimeException(e);
            }
        });
        try {
            tableLoad(getTableQuery());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void tableLoad(ResultSet resultSet) throws SQLException {
        List<TableColumn<DynamicTableRow, ?>> columns = PccTable.getColumns(resultSet);
        pccTable.clear();
        pccTable.setTableColumns(columns);
        pccTable.addTableColumn(PccTable.getNodeColumn("Action", cell -> new PatientsButtonCell(getButtonSet())));
        pccTable.resultSetToPccTable(resultSet);
    }

    public ResultSet getTableQuery() throws SQLException {
        StringBuilder sql = new StringBuilder("""
                SELECT
                    stock_id AS "Stock Id",
                    medicine_name "Name",
                    medicine_strength "Strength",
                    medicine_unit "Unit",
                    price_per_medicine AS "Prise",
                    stock_quantity AS "Stock quantity",
                    stock_expire_date AS "Expire date"
                FROM public.stock_details
                """);
        String searchString = searchTextField.getText();
        if (searchString.isEmpty())
            return Sql.getInstance().executeQuery(sql + " ORDER BY stock_expire_date ASC, medicine_name ASC;");
        List<String> columns = Arrays.asList(
                "stock_id::TEXT",
                "medicine_name",
                "medicine_strength::TEXT",
                "medicine_unit",
                "price_per_medicine::TEXT",
                "stock_quantity::TEXT",
                "stock_expire_date::TEXT"
        );
        sql.append("WHERE\n\t");
        for (String column : columns) {
            sql.append(column).append(" ILIKE ").append("'%").append(searchString).append("%'");
            if (columns.indexOf(column) < columns.size() - 1) {
                sql.append("\n\tOR ");
            } else {
                sql.append(" ORDER BY stock_expire_date DESC, medicine_name ASC;");
            }
        }
        System.out.println(sql);
        return Sql.getInstance().executeQuery(sql.toString());
    }

    public void tableLoad() {
        try {
            tableLoad(getTableQuery());
            validateRows(tableView);
        } catch (SQLException e) {
            GlobalsViews.showErrorAlert(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    private Stock selectedStock;

    private Button[] getButtonSet() {
        String iconSize = "20";
        Button editButton = new Button();
        Button deleteButton = new Button();
        editButton.setOnAction(event -> {
            Stock.setCurrentStock(selectedStock);
            Model.getInstance().getCommonViewFactory().getAdminViewFactory().getAdmin().showStockDetails();
            AdminPanes.getStockDetailsController().loadDataForCurrentMedicine();
        });
        deleteButton.setOnAction(event -> {
            Stock.setCurrentStock(selectedStock);
            Model.getInstance().getCommonViewFactory().getAdminViewFactory().getAdmin().showStockDetails();
            AdminPanes.getStockDetailsController().loadDataForCurrentMedicine();
            AdminPanes.getStockDetailsController().setGeneralDetailsType(GeneralDetailsType.DELETE);
        });
        ButtonElements.bindIconFillProperty(editButton, "edit-button", new FontAwesomeIconView(FontAwesomeIcon.EDIT, iconSize));
        ButtonElements.bindIconFillProperty(deleteButton, "delete-button", new FontAwesomeIconView(FontAwesomeIcon.TRASH, iconSize));
//        ButtonElements.bindIconFillProperty(viewButton, "view-button", new FontAwesomeIconView(FontAwesomeIcon.EYE, iconSize));
        return new Button[]{editButton, deleteButton};
    }



    public static void validateRows(TableView<DynamicTableRow> tableView) {
        Map<Integer,Integer> dates=new HashMap<>();
        final int[] j ={0};
        tableView.setRowFactory(tv -> {
            TableRow<DynamicTableRow> row = new TableRow<>() {
                @Override
                protected void updateItem(DynamicTableRow item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setStyle("");
                    } else {

                        Object expireDateObj = item.getData("Expire date");
                        if (expireDateObj instanceof Date) {
                            j[0]+=9;
                            LocalDate expireDate = ((Date) expireDateObj).toLocalDate();
                            dates.put((Integer) item.getData("Stock Id"), (Integer) item.getData("Stock quantity"));
                            LocalDate today = LocalDate.now();
                            long monthsUntilExpire = ChronoUnit.MONTHS.between(today, expireDate);
                            if (expireDate.isBefore(today)) {
                                setStyle("-fx-background-color: #FFCCCB;");
                            } else if (monthsUntilExpire <= 3) {
                                setStyle("-fx-background-color: #FFFFE0;");
                            } else {
                                setStyle("");
                            }
                        }
                    }
                }
            };
            return row;
        });
        TableColumn<DynamicTableRow, Integer> firstColumn = (TableColumn<DynamicTableRow, Integer>) tableView.getColumns().get(5);
//
//        firstColumn.setCellValueFactory(cellData -> {
//            String value = cellData.getValue().getData("First Column Data").toString(); // Replace with your method to get the value
//            return new javafx.beans.property.SimpleStringProperty(value);
//        });
        final int[] i = {0};
        firstColumn.setCellFactory(new Callback<TableColumn<DynamicTableRow, Integer>, TableCell<DynamicTableRow, Integer>>() {
            @Override
            public TableCell<DynamicTableRow, Integer> call(TableColumn<DynamicTableRow, Integer> param) {
                return new TableCell<DynamicTableRow, Integer>() {
                    @Override
                    protected void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            Label label = new Label();
                            label.getStyleClass().add("first-column-label");
                            getStyleClass().add("label-table-cell");
                            int quantity=item;
                            QuantityLimit quantityLimit = QuantityLimit.getQuantityByLimit(quantity);
                            String style = getStyleByLimit(quantityLimit);
                            label.getStyleClass().add(style);
                            assert quantityLimit != null;
                            label.setText(quantityLimit.toString().replace("_"," ")+"("+quantity+")");
                            setGraphic(label);
                        }
                    }
                };
            }
        });
    }

    private static @NotNull String getStyleByLimit(QuantityLimit quantityLimit) {
        String style="";
        switch (Objects.requireNonNull(quantityLimit)){
            case LOW -> {
                style="low-quantity";
            }case MEDIUM -> {
                style="medium-quantity";
            }case HIGH -> {
                style="high-quantity";
            }case VERY_HIGH -> {
                style="very-high-quantity";
            }
        }
        return style;
    }

    public void patientSelected() throws SQLException {
        if (tableView.getSelectionModel().getSelectedItem() == null) return;
        int stockId = (int) tableView.getSelectionModel().getSelectedItem().getData("Stock Id");
        selectedStock = Stock.getStock(stockId);
        selectedStock.load();
        Stock.setCurrentStock(selectedStock);
    }

    public enum QuantityLimit {
        LOW(100),MEDIUM(500),HIGH(2000),VERY_HIGH(10000);
        private final int limit;
        QuantityLimit(int limit){
            this.limit = limit;
        }
        public static QuantityLimit getQuantityByLimit(int limit){
            for(QuantityLimit q:values()){
                if(q.limit>limit)return q;
            }
            return null;
        }
    }
}
