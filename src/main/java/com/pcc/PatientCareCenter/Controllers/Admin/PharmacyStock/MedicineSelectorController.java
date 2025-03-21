package com.pcc.PatientCareCenter.Controllers.Admin.PharmacyStock;

import com.pcc.PatientCareCenter.Controllers.Admin.Patients.GeneralDetailsType;
import com.pcc.PatientCareCenter.Controllers.Admin.Patients.PrescriptionController;
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

import static com.pcc.PatientCareCenter.Controllers.Admin.PharmacyStock.PharmacyStockController.validateRows;

public class MedicineSelectorController implements Initializable {
    public Label pharmacyStock;
    public TextField searchTextField;
    public Button add;
    public TableView<DynamicTableRow> tableView;
    public List<Medicine> selectedStocks = new ArrayList<>();
    public Label message;
    public Button addToPrescription;
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
                    Optional<Medicine.InputValues> result = InputDialogHelper.showInputDialog(stock);
                    result.ifPresent(inputValues -> {
                        Medicine med;
                        try {
                            med = new Medicine(stock, inputValues);
                            selectedStocks.add(med);
                            AdminPanes.getPrescriptionController().addToList(med);
                            PccMessage.showMessage(message, med.getValues() + " added\n" + med, MessageType.MESSAGE_TYPE_INFO);
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
        validateRows(tableView);
    }

    public ResultSet getTableQuery() throws SQLException {
        StringBuilder sql = new StringBuilder("""
                SELECT
                    stock_id AS "Stock Id",
                    medicine_name "Name",
                    medicine_strength "Strength",
                    medicine_unit "Unit",
                    price_per_medicine AS "Price",
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
        return new Button[]{editButton, deleteButton};
    }

    public void patientSelected() throws SQLException {
        if (tableView.getSelectionModel().getSelectedItem() == null) return;
        int stockId = (int) tableView.getSelectionModel().getSelectedItem().getData("Stock Id");
        selectedStock = Stock.getStock(stockId);
        selectedStock.load();
        Stock.setCurrentStock(selectedStock);
    }
}
