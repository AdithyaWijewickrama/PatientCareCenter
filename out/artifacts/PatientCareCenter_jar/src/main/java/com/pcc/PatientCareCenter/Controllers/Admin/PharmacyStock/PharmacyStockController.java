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

import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

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
            return Sql.getInstance().executeQuery(sql + " ORDER BY stock_expire_date DESC, medicine_name ASC;");
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
                            LocalDate expireDate = ((Date) expireDateObj).toLocalDate();
                            LocalDate today = LocalDate.now();
                            long monthsUntilExpire = ChronoUnit.MONTHS.between(today, expireDate);
                            if (expireDate.isBefore(today)) {
                                setStyle("-fx-background-color: #FFCCCB;");
                            } else if (monthsUntilExpire <= 3) {
                                setStyle("-fx-background-color: #FFFFE0;");
                            } else {
                                setStyle("");
                            }
                            Object quantity = item.getData("Stock quantity");
                            getStyleClass().removeAll("low-quantity", "medium-quantity", "high-quantity", "very-high-quantity");
                            if (quantity instanceof Integer) {
                                int qnt = (int) quantity;
                                if (qnt < 50) {
                                    getStyleClass().add("low-quantity");
                                } else if (qnt < 150) {
                                    getStyleClass().add("medium-quantity");
                                } else if (qnt < 500) {
                                    getStyleClass().add("high-quantity");
                                } else if (qnt < 1000) {
                                    getStyleClass().add("very-high-quantity");
                                }
                            }
                        }
                    }
                }
            };
            return row;
        });
    }

    public void patientSelected() throws SQLException {
        if (tableView.getSelectionModel().getSelectedItem() == null) return;
        int stockId = (int) tableView.getSelectionModel().getSelectedItem().getData("Stock Id");
        selectedStock = Stock.getStock(stockId);
        selectedStock.load();
        Stock.setCurrentStock(selectedStock);
    }
}
