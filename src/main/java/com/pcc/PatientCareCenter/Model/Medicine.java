package com.pcc.PatientCareCenter.Model;

import com.pcc.PatientCareCenter.Database.Stock;

import java.sql.SQLException;
import java.time.LocalDate;

public class Medicine {
    String name;
    InputValues values;
    Stock stock;

    public Medicine(Stock stock, InputValues values) throws SQLException {
        this.name = stock.getLocalizedName();
        this.values = values;
        this.stock = stock;
    }

    public Medicine(String name, InputValues values) {
        this.name = name;
        this.values = values;
    }

    public void requireStock(Stock stock) {
        if (stock == null) throw new RuntimeException("This medicine is not in the stock");
    }

    public Double calculatePrise() throws Exception {
        requireStock(stock);
        if (values.frequency == FrequencyType.WEEKLY) {
            return stock.getPricePerMedicine() * values.frequency.getDailyFrequency() * values.getTotalDays();
        }
        return stock.getPricePerMedicine() * values.frequency.getDailyFrequency() * values.nOfDosesPerMedicine * values.getTotalDays();
    }

    public int getTotalMedicine() throws SQLException {
        if (values.frequency == FrequencyType.WEEKLY) {

        }
        return (int) Math.ceil((values.frequency.getDailyFrequency() * values.getTotalDays() * 1.) / values.nOfDosesPerMedicine());
    }

    public static String getFrequencyType(int frq) {
        return FrequencyType.getFrequencyType(frq).getName();
    }

    public Stock getStock() {
        return stock;
    }

    public String getName() {
        return name;
    }

    public InputValues getValues() {
        return values;
    }

    public boolean hasStock(int limit) throws SQLException {
        if (stock == null) return false;
        return stock.getQuantity() > limit;
    }

    public boolean isExpired() throws SQLException {
        if (stock == null) return false;
        return stock.getExpireDate().isBefore(LocalDate.now());
    }

    public record InputValues(Stock stock, FrequencyType frequency, int nOfDosesPerMedicine, int days, int weeks,
                              int months) {
        public String getFrequency() {
            return "Frequency: " + frequency;
        }

        public String getTime() {
            return (days > 0 ? (" Days: " + days) : "") + (weeks > 0 ? (" Weeks: " + weeks) : "") + (months > 0 ? (" Months: " + months) : "");
        }

        @Override
        public String toString() {
            try {
                return (stock.getLocalizedName()) + "\t" + frequency.getName() + " for " + (days > 0 ? ("\t" + days + " days") : "") + (weeks > 0 ? ("\t" + weeks + " weeks") : "") + (months > 0 ? ("\t" + months + " months") : "");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        public int getTotalDays() {
            return days + (weeks * 7) + (months * 30);
        }
    }
}
