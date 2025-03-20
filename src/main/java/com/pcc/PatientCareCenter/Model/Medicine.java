package com.pcc.PatientCareCenter.Model;

import com.pcc.PatientCareCenter.Database.Stock;

import java.sql.SQLException;

public class Medicine {
    String name;
    int frequency;
    int days;
    Stock stock;

    public Medicine(Stock stock, int frequency, int days) throws SQLException {
        this.name = stock.getLocalizedName();
        this.frequency = frequency;
        this.days = days;
        this.stock = stock;
    }

    public Medicine(String name, int frequency, int days) {
        this.name = name;
        this.frequency = frequency;
        this.days = days;
    }

    public Double calculatePrise() throws SQLException {
        if (stock == null) {
            throw new RuntimeException("This medicine is not in the stock");
        }
        return stock.getPricePerMedicine() * frequency * days;
    }

    public record InputValues(int frequency, int days, int weeks, int months) {
        @Override
        public String toString() {
            return "Frequency: " + frequency + (days==0?(", Days: " + days):"") + (weeks>0?(", Weeks: " + weeks):"") + (months>0?(", Months: " + months):"");
        }
    }
}
