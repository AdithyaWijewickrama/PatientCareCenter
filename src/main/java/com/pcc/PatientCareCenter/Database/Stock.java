package com.pcc.PatientCareCenter.Database;

import com.pcc.PatientCareCenter.Model.MedicineType;
import com.pcc.PatientCareCenter.Model.Sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class Stock implements DBObject {
    ResultSet data;
    int stockId;
    private static Stock stock;

    private Stock(int stockId) throws SQLException {
        this.stockId = stockId;
        load();
    }

    public static Stock getLatestStock(String name) throws SQLException {
        Object id = Sql.getInstance().getObject("SELECT stock_id FROM stock_details WHERE medicine_name=? ORDER BY stock_expire_date ASC", name);
        if (id != null) {
            return new Stock((int) id);
        }
        return null;
    }

    public int getStockId() {
        return stockId;
    }

    @Override
    public void load() throws SQLException {
        data = Sql.getInstance().executeQuery("SELECT * FROM stock_details WHERE stock_id=?", stockId);
        data.next();
    }

    @Override
    public ResultSet loadAndGetData() throws SQLException {
        load();
        return data;
    }

    @Override
    public ResultSet getData() throws SQLException {
        if (data == null) {
            load();
        }
        return data;
    }

    public static Stock getStock(int stockId) throws SQLException {
        Stock stock = new Stock(stockId);
        stock.load();
        return stock;
    }

    public MedicineType getMedicineType() throws SQLException {
        return MedicineType.fromDisplayName(data.getString("medicine_type"));
    }

    public Double getPricePerMedicine() throws SQLException {
        return data.getDouble("price_per_medicine");
    }

    public LocalDate getExpireDate() throws SQLException {
        return data.getDate("stock_expire_date").toLocalDate();

    }

    public void removeStockMedicine(int q) throws SQLException {
        if (q <= getQuantity()) {
            Sql.getInstance().execute("UPDATE stock_details SET stock_quantity=stock_quantity-? WHERE stock_id=?", q, stockId);
        }
    }

    public Integer getQuantity() throws SQLException {
        return data.getInt("stock_quantity");
    }

    public String getName() throws SQLException {
        return data.getString("medicine_name");
    }

    public String getUnit() throws SQLException {
        return data.getString("medicine_unit");
    }

    public Integer getStrength() throws SQLException {
        return data.getInt("medicine_strength");
    }

    public static List<Object> getMedicineNames(int doctorId) throws SQLException {
        List<Object> selectMedicineNameFromStockDetails = Sql.getInstance().getColumn("SELECT DISTINCT medicine_name FROM stock_details");
        return selectMedicineNameFromStockDetails;
    }

    public String getLocalizedName() throws SQLException {
        return String.format("%s %d%s", getName(), getStrength(), getUnit());
    }

    public static void setCurrentStock(Stock stock) {
        Stock.stock = stock;
    }

    public static Stock getCurrentStock() {
        return Stock.stock;
    }

    public static void main(String[] args) {
        try {
            Stock stock1 = new Stock(3);
            System.out.println(stock1.getLocalizedName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}