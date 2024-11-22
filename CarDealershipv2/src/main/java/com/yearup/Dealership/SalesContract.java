package com.yearup.Dealership;

public class SalesContract extends Contract {
    private double contractRecordingFee;
    private static final double salesTaxPercentage = 0.05;
    private double salesTaxAmountApplied;
    private double contractProcessingFee;
    private boolean wantsToFinance;
    private int contractLoanTerm;
    //private double contractDownPayment;
    //private Dealership dealership;

    public SalesContract(String contractDate, String contractCustomerName, String getContractCustomerEmail, Vehicle vehicleSold, boolean wantsToFinance) {
        super(contractDate, contractCustomerName, getContractCustomerEmail, vehicleSold);
        this.wantsToFinance = wantsToFinance;
        //this.contractDownPayment = contractDownPayment;
        this.contractRecordingFee = 100;
        //this.dealership = dealership;
        this.salesTaxAmountApplied = vehicleSold.getPrice() * salesTaxPercentage;
        if(vehicleSold.getPrice() >= 10000){
            this.contractProcessingFee = 495;
            this.contractLoanTerm = 48;
        }
        else{
            this.contractProcessingFee = 295;
            this.contractLoanTerm = 24;
        }
    }
    public SalesContract(String contractDate, String contractCustomerName, String getContractCustomerEmail, Vehicle vehicleSold, double salesTaxAmountApplied,double contractRecordingFee, double contractProcessingFee, boolean wantsToFinance) {
        super(contractDate, contractCustomerName, getContractCustomerEmail, vehicleSold);
        this.contractRecordingFee = contractRecordingFee;
        this.salesTaxAmountApplied = salesTaxAmountApplied;
        this.contractProcessingFee = contractProcessingFee;
        this.wantsToFinance = wantsToFinance;
    }

    public double getContractRecordingFee() {
        return contractRecordingFee;
    }

    public double getContractProcessingFee() {
        return contractProcessingFee;
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

    public double rateForMonthlyPayment(Vehicle vehicle){
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
        if (isWantsToFinance()) {
            double monthlyRate = rateForMonthlyPayment(super.getVehicleSold())/12;
            double vehiclePrice = super.getVehicleSold().getPrice();
            double totalPrice = 0;

            totalPrice = getContractRecordingFee() + getContractProcessingFee() + getSalesTaxAmountApplied() + vehiclePrice;
            double loanAmount = totalPrice;
            monthlyPayment = loanAmount * ((monthlyRate * Math.pow(1 + monthlyRate,contractLoanTerm)) / (Math.pow(1 + monthlyRate, contractLoanTerm) - 1));
        }
        return monthlyPayment;
    }
    @Override
    public double getTotalPrice() {
        double vehiclePrice = super.getVehicleSold().getPrice();
        if (!isWantsToFinance()) {
            return getContractRecordingFee() + getContractProcessingFee() + getSalesTaxAmountApplied() + vehiclePrice;
        }

        else {
            return (getContractMonthlyPayment() * getContractLoanTerm());
        }
    }
    @Override
    public String toString(){

        return "\n-----Contract Information-----" +

                "\nCustomer Name: " + getContractCustomerName() +
                "\nContract Date: " + getContractDate() +
                "\nLoan Term: " + getContractLoanTerm() + " months" +
                "\nFinancing: " + isWantsToFinance() +
                "\nProcessing Fee: " + String.format("$%.2f", getContractProcessingFee()) +
                "\nRecording Fee: " + String.format("$%.2f", contractRecordingFee) +
                "\nSales Tax: " + salesTaxPercentage * 100 + "%" +
                //"\nDown Payment: " + String.format("$%.2f", getContractDownPayment()) +
                "\nLoan Amount: " + String.format("$%.2f", getTotalPrice()) +
                "\n--------------------------------" +
                "\nMonthly Payment: " + String.format("$%.2f", getContractMonthlyPayment()) +
                "\nThe total price: " + String.format("$%.2f", getTotalPrice());
    }
    @Override
    public String encodedString(){
        return "SALE|" + super.getContractDate() + "|" + super.getContractCustomerName() + "|" + super.getGetContractCustomerEmail() + "|" + super.getVehicleSold().toContractString() + "|" + super.getVehicleSold().getPrice() * salesTaxPercentage + "|" + getContractRecordingFee() + "|" + String.format("%.2f",getContractProcessingFee()) + "|" + String.format("%.2f",getTotalPrice()) + "|" + isWantsToFinance() + "|" + String.format("%.2f",getContractMonthlyPayment()) + "\n";
    }

}