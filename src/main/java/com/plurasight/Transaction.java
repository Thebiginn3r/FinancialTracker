package com.plurasight;

public class Transaction {
    private String date;
    private String description;
    private String vendor;
    private double amount;

    public Transaction(String date, String description, String vendor, double amount) {
        this.date = date;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    public String toCSV() {
        return date + "|" + description + "|" + vendor + "|" + amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "date='" + date + '\'' +
                ", description='" + description + '\'' +
                ", vendor='" + vendor + '\'' +
                ", amount=" + amount +
                '}';
    }
}
