
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
    private Dealership dealership;
    private Vehicle vehicle;

    public LeaseContract(String contractDate, String contractCustomerName, String getContractCustomerEmail, int vehicleSold, Dealership dealership) {
        super(contractDate, contractCustomerName, getContractCustomerEmail, vehicleSold);
        this.vehicle = dealership.getVehiclesByVin(vehicleSold);
        this.leaseFee = vehicle.getPrice() * leaseFeeRate;
        this.residualValue = expectedEndingValue * vehicle.getPrice();
        this.capCost = vehicle.getPrice();
        this.depreciation = (capCost - residualValue)/leaseTerm;
        this.financeFee = (capCost + residualValue) * (interestRate*100/2400);
    }

    public LeaseContract(String contractDate, String contractCustomerName, String getContractCustomerEmail, int vehicleSold,Vehicle vehicle,double residualValue, double leaseFee) {
        super(contractDate, contractCustomerName, getContractCustomerEmail, vehicleSold);
        this.leaseFee = leaseFee;
        this.residualValue = residualValue;
        this.vehicle = vehicle;
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

    public Dealership getDealership() {
        return dealership;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    @Override
    public double getContractMonthlyPayment() {
        return depreciation + financeFee;
    }
    @Override
    public double getTotalPrice() {
        return (getContractMonthlyPayment() * leaseTerm);
    }
    @Override
    public String toString(){
        String output = "\n-----Contract Information-----" +

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

        return output;
    }
    @Override
    public String encodedString(){
        return "Lease|" + super.getContractDate() + "|" + super.getContractCustomerName() + "|" + super.getGetContractCustomerEmail() + "|" + vehicle.toContractString() +
                "|" + String.format("%.2f",getResidualValue()) + "|" + String.format("%.2f",getLeaseFee()) + "|" + String.format("%.2f",getTotalPrice()) + "|" + String.format("%.2f",getContractMonthlyPayment());
    }


}
