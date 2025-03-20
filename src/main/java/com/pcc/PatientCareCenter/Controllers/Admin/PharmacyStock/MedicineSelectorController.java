package com.pcc.PatientCareCenter.Controllers.Admin.PharmacyStock;

import com.pcc.PatientCareCenter.Controllers.Admin.Patients.GeneralDetailsType;
import com.pcc.PatientCareCenter.Database.Stock;
import com.pcc.PatientCareCenter.Model.Medicine;
import com.pcc.PatientCareCenter.Model.Model;
import com.pcc.PatientCareCenter.Model.Sql;
import com.pcc.PatientCareCenter.Views.Components.MessageType;
import com.pcc.PatientCareCenter.Views.Components.PccMessage;
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
import java.util.*;

public class MedicineSelectorController implements Initializable {
    public Label pharmacyStock;
    public TextField searchTextField;
    public Button add;
    public TableView<DynamicTableRow> tableView;
    public List<Medicine> selectedStocks = new ArrayList<>();
    public Label message;
    private PccTable pccTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pccTable = new PccTable(tableView);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        add.setOnAction(event -> {
            tableView.getSelectionModel().getSelectedItems().forEach(dynamicTableRow -> {
                int stockId = (Integer) dynamicTableRow.getData("Stock Id");
                Stock stock;
                try {
                    stock = Stock.getStock(stockId);
                    Optional<Medicine.InputValues> result = InputDialogHelper.showInputDialog();
                    result.ifPresent(inputValues -> {
                        Medicine med;
                        try {
                            med = new Medicine(stock, inputValues.frequency(), inputValues.days() + (inputValues.weeks() * 7) + (inputValues.months() * 30));
                            selectedStocks.add(med);
                            PccMessage.showMessage(message, stock.getLocalizedName() + " added\n" + med, MessageType.MESSAGE_TYPE_INFO);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    });
                } catch (SQLException e) {
                    GlobalsViews.showErrorAlert(e.getLocalizedMessage());
                    throw new RuntimeException(e);
                }
            });
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
                    medicine_name Name,
                    medicine_strength Strength,
                    medicine_unit "Unit",
                    prise_per_medicine AS Prise,
                    stock_quantity AS "Stock quantity",
                    stock_expire_date AS "Expire date"
                FROM public.stock_details
                """);
        String searchString = searchTextField.getText();
        if (searchString.isEmpty())
            return Sql.getInstance().executeQuery(sql + ";");
        List<String> columns = Arrays.asList(
                "stock_id::TEXT",
                "medicine_name",
                "medicine_strength::TEXT",
                "medicine_unit",
                "prise_per_medicine::TEXT",
                "stock_quantity::TEXT",
                "stock_expire_date::TEXT"
        );
        sql.append("WHERE\n\t");
        for (String column : columns) {
            sql.append(column).append(" ILIKE ").append("'%").append(searchString).append("%'");
            if (columns.indexOf(column) < columns.size() - 1) {
                sql.append("\n\tOR ");
            } else {
                sql.append(";");
            }
        }
        return Sql.getInstance().executeQuery(sql.toString());
    }

    public void tableLoad() {
        try {
            tableLoad(getTableQuery());
            validateRows();
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
        Button viewButton = new Button();
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
        ButtonElements.bindIconFillProperty(viewButton, "view-button", new FontAwesomeIconView(FontAwesomeIcon.EYE, iconSize));
        return new Button[]{viewButton, editButton, deleteButton};
    }

    public void validateRows() {
        tableView.setRowFactory(tv -> {
            TableRow<DynamicTableRow> row = new TableRow<>() {
                @Override
                protected void updateItem(DynamicTableRow item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setStyle(""); // Clear style for empty rows
                    } else {
                        // Check the expiration date column (assuming it's named "expire_date")
                        Object expireDateObj = item.getData("Expire date");
                        System.out.println(expireDateObj.getClass());
                        if (expireDateObj instanceof Date) {
                            LocalDate expireDate = ((Date) expireDateObj).toLocalDate();
                            LocalDate today = LocalDate.now();

                            // Check if expiration date is within 3 months
                            long monthsUntilExpire = ChronoUnit.MONTHS.between(today, expireDate);

                            if (expireDate.isBefore(today)) {
                                // Expired: Set row background to red
                                setStyle("-fx-background-color: #FFCCCB;"); // Light red
                            } else if (monthsUntilExpire <= 3) {
                                // Expiring soon: Set row background to yellow
                                setStyle("-fx-background-color: #FFFFE0;"); // Light yellow
                            } else {
                                // Not expiring soon: Clear style
                                setStyle("");
                            }
                        }
                    }
                }
            };
            return row;
        });
    }

    public void addMedicine() {

    }

    public void patientSelected() throws SQLException {
        if (tableView.getSelectionModel().getSelectedItem() == null) return;
        int stockId = (int) tableView.getSelectionModel().getSelectedItem().getData("Stock Id");
        selectedStock = Stock.getStock(stockId);
        selectedStock.load();
        Stock.setCurrentStock(selectedStock);
    }
}
