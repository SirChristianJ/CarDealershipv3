package com.yearup.Dealership;

public class SalesContract extends Contract {
    private double contractRecordingFee;
    private static final double salesTaxPercentage = 0.05;
    private double salesTaxAmountApplied;
    private double contractProcessingFee;
    private boolean wantsToFinance;
    private int contractLoanTerm;
    private double contractDownPayment;
    private Dealership dealership;
    private Vehicle vehicle;

    public SalesContract(String contractDate, String contractCustomerName, String getContractCustomerEmail, int vehicleSold, boolean wantsToFinance, double contractDownPayment, Dealership dealership) {
        super(contractDate, contractCustomerName, getContractCustomerEmail, vehicleSold);
        this.wantsToFinance = wantsToFinance;
        this.contractDownPayment = contractDownPayment;
        this.contractRecordingFee = 100;
        this.dealership = dealership;
        this.salesTaxAmountApplied = dealership.getVehiclesByVin(vehicleSold).getPrice() * salesTaxPercentage;
        this.vehicle = dealership.getVehiclesByVin(vehicleSold);
        if(vehicle.getPrice() >= 10000){
            contractProcessingFee = 495;
            contractLoanTerm = 48;
        }
        else{
            contractProcessingFee = 295;
            contractLoanTerm = 24;
        }
    }

    public SalesContract(String contractDate, String contractCustomerName, String getContractCustomerEmail, int vehicleSold, Vehicle vehicle, double salesTaxAmountApplied, double contractRecordingFee, double contractProcessingFee, boolean wantsToFinance) {
        super(contractDate, contractCustomerName, getContractCustomerEmail, vehicleSold);
        this.contractRecordingFee = contractRecordingFee;
        this.salesTaxAmountApplied = salesTaxAmountApplied;
        this.contractProcessingFee = contractProcessingFee;
        this.wantsToFinance = wantsToFinance;
        this.contractDownPayment = contractDownPayment;
        this.vehicle = vehicle;
    }

    public double getContractRecordingFee() {
        return contractRecordingFee;
    }

    public double getContractProcessingFee() {
        return contractProcessingFee;
    }
    public double getContractDownPayment() {
        return contractDownPayment;
    }

    public boolean isWantsToFinance() {
        return wantsToFinance;
    }

    public int getContractLoanTerm() {
        return contractLoanTerm;
    }

    public double getSalesTaxAmountApplied() {
        return salesTaxAmountApplied;
    }

    public double rateForMonthlyPayment(int vin){
        if(vehicle.getPrice() >= 10000){
            return 0.0425;
        }
        else{
            return 0.0525;
        }
    }

    @Override
    public double getContractMonthlyPayment() {
        double monthlyPayment = 0;
        if (wantsToFinance) {
            double monthlyRate = rateForMonthlyPayment(super.getVehicleSold())/12;
            double vehiclePrice = vehicle.getPrice();
            double totalPrice = 0;

            totalPrice = contractRecordingFee + contractProcessingFee + salesTaxAmountApplied + vehiclePrice;
            double loanAmount = totalPrice - contractDownPayment;
            monthlyPayment = loanAmount * ((monthlyRate * Math.pow(1 + monthlyRate,contractLoanTerm)) / (Math.pow(1 + monthlyRate, contractLoanTerm) - 1));
        }
        return monthlyPayment;
    }
    @Override
    public double getTotalPrice() {
        double vehiclePrice = vehicle.getPrice();
        if (vehiclePrice >= 10000) {
            if (!wantsToFinance) {
                return contractRecordingFee + contractProcessingFee + salesTaxAmountApplied + vehiclePrice;
            }

            else {
                return (getContractMonthlyPayment() * 48) + contractDownPayment;
            }
        }

        else {
            if (!wantsToFinance) {
                return contractRecordingFee + contractProcessingFee + salesTaxAmountApplied + vehiclePrice;
            }

            else {
                return (getContractMonthlyPayment() * 24) + contractDownPayment;
            }
        }
    }
    @Override
    public String toString(){
        String output = "\n-----Contract Information-----" +

                "\nCustomer Name: " + getContractCustomerName() +
                "\nContract Date: " + getContractDate() +
                "\nLoan Term: " + getContractLoanTerm() + " months" +
                "\nFinancing: " + isWantsToFinance() +
                "\nProcessing Fee: " + String.format("$%.2f", getContractProcessingFee()) +
                "\nRecording Fee: " + String.format("$%.2f", contractRecordingFee) +
                "\nSales Tax: " + salesTaxPercentage * 100 + "%" +
                "\nDown Payment: " + String.format("$%.2f", getContractDownPayment()) +
                "\nLoan Amount: " + String.format("$%.2f", getTotalPrice() - getContractDownPayment()) +
                "\n--------------------------------" +
                "\nMonthly Payment: " + String.format("$%.2f", getContractMonthlyPayment()) +
                "\nThe total price: " + String.format("$%.2f", getTotalPrice());

        return output;
    }
    @Override
    public String encodedString(){
        return "SALE|" + super.getContractDate() + "|" + super.getContractCustomerName() + "|" + super.getGetContractCustomerEmail() + "|" + vehicle.toContractString() + "|" + vehicle.getPrice() * salesTaxPercentage + "|" + contractRecordingFee + "|" + String.format("%.2f",getContractProcessingFee()) + "|" + String.format("%.2f",getTotalPrice()) + "|" + isWantsToFinance() + "|" + String.format("%.2f",getContractMonthlyPayment()) + "\n";
    }

}