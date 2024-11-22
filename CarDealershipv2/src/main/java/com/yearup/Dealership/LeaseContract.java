
package com.yearup.Dealership;

public class LeaseContract extends Contract{
    private static final double leaseFeeRate = 0.07;
    private static final double expectedEndingValue = 0.50;
    //expectedDepreciationPct
    private static final int leaseTerm = 36;
    private static final double interestRate = 0.04;
    private double leaseFee;
    private double depreciation;
    private double capCost;
    private double residualValue;
    private double financeFee;


    public LeaseContract(String contractDate, String contractCustomerName, String getContractCustomerEmail, Vehicle vehicleSold) {
        super(contractDate, contractCustomerName, getContractCustomerEmail, vehicleSold);
        this.leaseFee = vehicleSold.getPrice() * leaseFeeRate;
        this.residualValue = expectedEndingValue * vehicleSold.getPrice();
        this.capCost = vehicleSold.getPrice();
        this.depreciation = (capCost - residualValue)/leaseTerm;
        this.financeFee = (capCost + residualValue) * ((interestRate*100)/2400);
    }
    public LeaseContract(String contractDate, String contractCustomerName, String getContractCustomerEmail, Vehicle vehicleSold,double residualValue, double leaseFee) {
        super(contractDate, contractCustomerName, getContractCustomerEmail, vehicleSold);
        this.leaseFee = leaseFee;
        this.depreciation = depreciation;
        this.capCost = capCost;
        this.residualValue = residualValue;
        this.financeFee = financeFee;
    }

    public double getCapCost() {
        return capCost;
    }
    public double getResidualValue() {
        return residualValue;
    }

    public double getLeaseFee() {
        return leaseFee;
    }

    public int getLeaseTerm() {
        return leaseTerm;
    }

    public double getDepreciation() {
        return depreciation;
    }
    public double getInterestRate() {
        return interestRate;
    }


    @Override
    public double getContractMonthlyPayment() {
        return getDepreciation() + financeFee;
    }
    @Override
    public double getTotalPrice() {
        return (getContractMonthlyPayment() * leaseTerm);
    }
    @Override
    public String toString(){

        return "\n-----Contract Information-----" +

                "\nCustomer Name: " + getContractCustomerName() +
                "\nContract Date: " + getContractDate() +
                "\nLease Term: " + getLeaseTerm() + " months" +
                "\nLease Fee Rate: " + String.format("%.2f",leaseFeeRate * 100) + "%" +
                "\nExpected Ending Value:" + expectedEndingValue * 100 + "%" +
                "\nInterest Rate:" + getInterestRate() * 100 + "%" +
                "\nCapitalized Cost: " + String.format("$%.2f", getCapCost()) +
                "\nDepreciation:  " + String.format("%.2f",getDepreciation()) +
                "\nResidual Value: " + String.format("%.2f", getResidualValue()) +
                "\n--------------------------------" +
                "\nMonthly Payment: " + String.format("$%.2f", getContractMonthlyPayment()) +
                "\nThe total price: " + String.format("$%.2f", getTotalPrice());
    }
    @Override
    public String encodedString(){
        return "Lease|" + super.getContractDate() + "|" + super.getContractCustomerName() + "|" + super.getGetContractCustomerEmail() + "|" + super.getVehicleSold().toContractString() +
                "|" + String.format("%.2f",getResidualValue()) + "|" + String.format("%.2f",getLeaseFee()) + "|" + String.format("%.2f",getTotalPrice()) + "|" + String.format("%.2f",getContractMonthlyPayment()) + "\n";
    }

}
