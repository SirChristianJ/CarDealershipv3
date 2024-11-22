package com.yearup.Dealership;

public abstract class Contract {
    private String contractDate;
    private String contractCustomerName;
    private String getContractCustomerEmail;
    private Vehicle vehicleSold;

    public abstract double getTotalPrice();
    public abstract String encodedString();
    public abstract double getContractMonthlyPayment();

    public Contract(String contractDate, String contractCustomerName, String getContractCustomerEmail, Vehicle vehicleSold) {
        this.contractDate = contractDate;
        this.contractCustomerName = contractCustomerName;
        this.getContractCustomerEmail = getContractCustomerEmail;
        this.vehicleSold = vehicleSold;
    }

    public String getContractDate() {
        return contractDate;
    }

    public String getContractCustomerName() {
        return contractCustomerName;
    }

    public String getGetContractCustomerEmail() {
        return getContractCustomerEmail;
    }

    public Vehicle getVehicleSold() {
        return vehicleSold;
    }
}