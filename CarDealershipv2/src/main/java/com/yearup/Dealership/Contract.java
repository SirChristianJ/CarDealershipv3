package com.yearup.Dealership;

public abstract class Contract {
    private String contractDate;
    private String contractCustomerName;
    private String getContractCustomerEmail;
    private int vehicleSold;
    private double totalPrice;
    private double contractMonthlyPayment;

    public abstract double getTotalPrice();
    public abstract String encodedString();
    public abstract double getContractMonthlyPayment();

    public Contract(String contractDate, String contractCustomerName, String getContractCustomerEmail, int vehicleSold) {
        this.contractDate = contractDate;
        this.contractCustomerName = contractCustomerName;
        this.getContractCustomerEmail = getContractCustomerEmail;
        this.vehicleSold = vehicleSold;
    }

    public String getContractDate() {
        return contractDate;
    }

    public void setContractDate(String contractDate) {
        this.contractDate = contractDate;
    }

    public String getContractCustomerName() {
        return contractCustomerName;
    }

    public void setContractCustomerName(String contractCustomerName) {
        this.contractCustomerName = contractCustomerName;
    }

    public String getGetContractCustomerEmail() {
        return getContractCustomerEmail;
    }

    public void setGetContractCustomerEmail(String getContractCustomerEmail) {
        this.getContractCustomerEmail = getContractCustomerEmail;
    }

    public int getVehicleSold() {
        return vehicleSold;
    }

    public void setVehicleSold(int vehicleSold) {
        this.vehicleSold = vehicleSold;
    }
}