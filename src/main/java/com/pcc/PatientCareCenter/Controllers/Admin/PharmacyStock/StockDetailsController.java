package com.pcc.PatientCareCenter.Controllers.Admin.PharmacyStock;

import com.pcc.PatientCareCenter.Controllers.Admin.Patients.GeneralDetailsType;
import com.pcc.PatientCareCenter.Database.PPDetails;
import com.pcc.PatientCareCenter.Database.Stock;
import com.pcc.PatientCareCenter.Database.User.Admin.Doctor;
import com.pcc.PatientCareCenter.Model.MedicineType;
import com.pcc.PatientCareCenter.Views.Components.DCConnection.*;
import com.pcc.PatientCareCenter.Views.GlobalsViews;
import com.pcc.PatientCareCenter.Views.Panes.AdminPanes;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;

public class StockDetailsController implements Initializable {
    public Spinner<Integer> strength;
    public ComboBox<String> unit;
    public ComboBox<String> medicineType;
    public Spinner<Double> pricePerMedicine;
    public Spinner<Integer> quantity;
    public DatePicker expirationDate;
    public ComboBox<Object> name;
    public Button saveButton;

    ResultConnection resultConnection;
    GeneralDetailsType generalDetailsType;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeData();
        DataComponentConnection[] connections = {
                new ValueComboBoxConnection<>(name),
                new ValueComboBoxConnection<>(medicineType),
                new IntegerSpinnerConnection(strength, 0, 1000000, 1, 1),
                new ValueComboBoxConnection<>(unit),
                new DoubleSpinnerConnection(pricePerMedicine, 0, 500000, 5.5, 5),
                new IntegerSpinnerConnection(quantity, 0, Integer.MAX_VALUE, 100, 100),
                new DateDatePickerConnection(expirationDate),
        };
        resultConnection = new ResultConnection(connections);
        setGeneralDetailsType(GeneralDetailsType.UPDATE);
    }


    public void setGeneralDetailsType(GeneralDetailsType generalDetailsType) {
        this.generalDetailsType = generalDetailsType;
        resultConnection.clear();
        switch (generalDetailsType) {
            case UPDATE -> {
                loadDataForCurrentMedicine();
                saveButton.setText("Update");
                setAction((action) -> {
                    try {
                        resultConnection.updateToDataBase();
                        AdminPanes.getPharmacyStockController().tableLoad();
                        GlobalsViews.showInformationAlert("Updated successfully!");
                        resultConnection.clear();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
            case INSERT -> {
                saveButton.setText("Insert");
                prepareToInsert();
                setAction((action) -> {
                    try {
                        resultConnection.insertToDataBase();
                        GlobalsViews.showInformationAlert("Inserted successfully!");
                        setGeneralDetailsType(GeneralDetailsType.UPDATE);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
            case DELETE -> {
                saveButton.setText("Delete");
                loadDataForCurrentMedicine();
                setAction((action) -> {
                    try {
                        if (GlobalsViews.showWarningAlert("Are you sure want to delete!")) {
                            resultConnection.setDelete(new SQLQuery("""
                                    DELETE FROM stock_details WHERE stock_id=? AND pp_id=?
                                    """, QueryReturnType.NONE, new Object[]{Stock.getCurrentStock().getStockId(), PPDetails.getCurrentPP().getPpId()}));
                            resultConnection.deleteFromDataBase();
                            AdminPanes.getPharmacyStockController().tableLoad();
                            GlobalsViews.showInformationAlert("Deleted successfully!");
                            prepareToInsert();
                            setGeneralDetailsType(GeneralDetailsType.INSERT);
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }
    }

    public void setAction(EventHandler<ActionEvent> eventHandler) {
        saveButton.setOnAction(eventHandler);
    }

    public void prepareToInsert() {
        resultConnection.clear();
        resultConnection.setInsert(new SQLQuery("""
                BEGIN TRANSACTION;
                INSERT INTO stock_details
                    (
                    medicine_name,
                    medicine_type,
                    medicine_dose,
                    medicine_dose_unit,
                    price_per_medicine,
                    stock_quantity,
                    stock_expire_date,
                    pp_id,
                     )
                    VALUES(?,?,?,?,?,?,?)
                """, QueryReturnType.ROW, new Object[]{PPDetails.getCurrentPP().getPpId()}));
        resultConnection.setUpdate(new SQLQuery("""
                UPDATE stock_details
                SET
                    medicine_name=?,
                    medicine_type=?,
                    medicine_dose=?,
                    medicine_dose_unit=?,
                    price_per_medicine=?,
                    stock_quantity=?,
                    stock_expire_date=?
                WHERE
                    stock_id=? AND pp_id=?
                """, QueryReturnType.NONE, new Object[]{Stock.getCurrentStock().getStockId(), PPDetails.getCurrentPP().getPpId()}));
    }

    public void loadDataForCurrentMedicine() {
        if (Stock.getCurrentStock() == null) {
            return;
        }
        try {
            resultConnection.setInsert(new SQLQuery("""
                BEGIN TRANSACTION;
                INSERT INTO stock_details
                    (
                    medicine_name,
                    medicine_type,
                    medicine_dose,
                    medicine_dose_unit,
                    price_per_medicine,
                    stock_quantity,
                    stock_expire_date,
                    pp_id,
                     )
                    VALUES(?,?,?,?,?,?,?)
                """, QueryReturnType.ROW, new Object[]{PPDetails.getCurrentPP().getPpId()}));
            resultConnection.setSelect(new SQLQuery(String.format("""
                    SELECT
                        medicine_name,
                        medicine_type,
                        medicine_strength,
                        medicine_unit,
                        price_per_medicine,
                        stock_quantity,
                        stock_expire_date
                    FROM stock_details
                    WHERE  stock_id=%d
                    ORDER BY stock_expire_date DESC, medicine_name ASC;
                    """, Stock.getCurrentStock().getStockId()), QueryReturnType.ROW));
            resultConnection.setUpdate(new SQLQuery("""
                    UPDATE stock_details
                    SET
                        medicine_name=?,
                        medicine_type=?,
                        medicine_strength=?,
                        medicine_unit=?,
                        price_per_medicine=?,
                        stock_quantity=?,
                        stock_expire_date=?
                    WHERE
                        stock_id=? AND pp_id=?
                    """, QueryReturnType.NONE, new Object[]{Stock.getCurrentStock().getStockId(), PPDetails.getCurrentPP().getPpId()}));
            resultConnection.loadDataFromDatabase();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    HashMap<String, ObservableList<String>> citiesByProvince;

    private void initializeData() {
        unit.getItems().addAll("mg", "mcg", "ml", "g");
        medicineType.getItems().addAll(MedicineType.getList());
        try {
            name.getItems().addAll(Stock.getMedicineNames(Doctor.getCurrentDoctor().getDoctorId()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void clear() {
        resultConnection.clear();
    }
}
