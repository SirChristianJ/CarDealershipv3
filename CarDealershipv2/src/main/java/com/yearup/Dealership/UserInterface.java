package com.yearup.Dealership;

import javax.crypto.Cipher;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class UserInterface {
    private Dealership dealership;
    UserInterface(){
        this.dealership = DealershipFileManager.getDealership();
    }

    public void display(){
        do {
            System.out.println("---------------------------------------------");
            System.out.println("Welcome to the Object Oriented Dealership!");
            System.out.println("---------------------------------------------");

            System.out.println("1)Filter by price");
            System.out.println("2)Filter by make/model");
            System.out.println("3)Filter by year");
            System.out.println("4)Filter by color");
            System.out.println("5)Filter by mileage");
            System.out.println("6)Filter by vehicle type");
            System.out.println("7)View all vehicles");
            System.out.println("8)Add vehicle");
            System.out.println("9)Remove vehicle");
            System.out.println("10)SELL/LEASE Vehicle");

            System.out.println("99)Exit");

            System.out.println("\n");

            try {
                short choice = Console.PromptForShort("Enter a selection: ");
                switch (choice) {
                    case 1 -> {
                        int minRange = Console.PromptForInt("Enter a min range:");
                        int maxRange = Console.PromptForInt("Enter a max range: ");
                        displayFilteredInventory(dealership.getVehiclesByPrice(minRange, maxRange));
                    }

                    case 2 -> {
                        String makeRange = Console.PromptForString("Enter a make:");
                        String modelRange = Console.PromptForString("Enter a model: ");
                        displayFilteredInventory(dealership.getVehiclesByMakeModel(makeRange, modelRange));
                    }

                    case 3 -> {
                        int minYearRange = Console.PromptForInt("Enter a min year: ");
                        int maxYearRange = Console.PromptForInt("Enter a max year: ");
                        displayFilteredInventory(dealership.getVehiclesByYear(minYearRange, maxYearRange));
                    }

                    case 4 -> {
                        String colorRange = Console.PromptForString("Enter a color: ");
                        displayFilteredInventory(dealership.getVehiclesByColor(colorRange));
                    }

                    case 5 -> {
                        int minMileageRange = Console.PromptForInt("Enter a min mileage: ");
                        int maxMileageRange = Console.PromptForInt("Enter a max mileage: ");
                        displayFilteredInventory(dealership.getVehiclesByMileage(minMileageRange, maxMileageRange));
                    }

                    case 6 -> {
                        String vehicleTypeQuery = Console.PromptForString("Enter a vehicle type: ");
                        displayFilteredInventory(dealership.getVehiclesByType(vehicleTypeQuery));
                    }

                    case 7 -> {
                        displayInventoryByVinYearMakeModel(dealership.getAllVehicles());
                    }

                    case 8 -> {
                        addVehicle();
                    }

                    case 9 -> {promptForRemovingVehicle();}

                    case 10 -> {processSellOrLeaseVehicle();}

                    case 99 -> {
                        System.out.println("Exiting ...");
                        return;
                    }

                    default -> {
                        System.out.println("Please enter one of the available selections!\n");
                    }
                }
            } catch (NumberFormatException e){
                System.out.println("Enter a numerical number!");
            }
            String keyPress = Console.PromptForString("\nPress any key to return to previous menu: ");

        }while (true);
    }

    public void displayInventoryByVinYearMakeModel(ArrayList<Vehicle> inventory){
        System.out.printf("%5s|%5s|%5s|%5s\n","vin","year","make","model");
        System.out.println("-----------------------------------------------");
        for (Vehicle v: inventory){
            System.out.println(v.toEncodedString());
        }
    }
    public void displayFilteredInventory(ArrayList<Vehicle> inventory){
        System.out.printf("%5s|%5s|%5s|%5s|%5s|%5s|%5s|%5s\n","vin","year","make","model","vehicle-type","color","odometer","price");
        System.out.println("-------------------------------------------------------");
        for (Vehicle v: inventory){
            System.out.println(v.toString());
        }
    }
    public void displaySingleVehicleInfo(Vehicle v){
        System.out.printf("%5s|%5s|%5s|%5s|%5s|%5s|%5s|%5s\n","vin","year","make","model","vehicle-type","color","odometer","price");
        System.out.println("-------------------------------------------------------");
        System.out.println(v.toString());
    }
    public void promptForRemovingVehicle(){
        int vinToRemove = Console.PromptForInt("Add a vin: ");
        dealership.removeVehicle(vinToRemove);
    }

    public void addVehicle(){

        Vehicle newVehicle = promptForAddingVehicle();
        dealership.addVehicle(newVehicle);
    }
    public Vehicle promptForAddingVehicle(){
        int vinToAdd = Console.PromptForInt("Add a vin: ");
        int yearToAdd = Console.PromptForInt("Add a year: ");
        String makeToAdd = Console.PromptForString("Add a make: ");
        String modelToAdd = Console.PromptForString("Add a model: ");
        String vehicleTypeToAdd = Console.PromptForString("What type of vehicle is this (truck,suv,van,etc): ");
        String colorToAdd = Console.PromptForString("What color is this vehicle: ");
        int odometerToAdd = Console.PromptForInt("Add odometer: ");
        double priceToAdd = Console.PromptForDouble("Add price: ");
        Vehicle vehicleToAdd = new Vehicle(vinToAdd,yearToAdd,makeToAdd,modelToAdd,vehicleTypeToAdd,colorToAdd,odometerToAdd,priceToAdd);

        return vehicleToAdd;
    }

    public void displaySalesContractInfo(SalesContract salesContract){
        System.out.println(salesContract);
    }
    public void displayLeaseContractInfo(LeaseContract leaseContract){
        System.out.println(leaseContract);
    }
    public void processSellOrLeaseVehicle(){
        //do the stuff here...
        int[] options = promptForPurchasingVehicle();
        int userChoice = options[0];
        int queryVin = options[1];
        if(userChoice == 1){
            displaySalesContractInfo(initializeSalesContract(queryVin));
        } else if (userChoice == 2) {
            displayLeaseContractInfo(initializeLeaseContract(queryVin));
        }
    }
    public int[] promptForPurchasingVehicle(){
        System.out.println("---------------------------------------------");
        System.out.println("Welcome to the Object Oriented Dealership!");
        System.out.println("---------------------------------------------");
        displayInventoryByVinYearMakeModel(dealership.getAllVehicles());
        int vehicleChoice = Console.PromptForInt("Enter the vin of the vehicle you wish to purchase: ");
        displaySingleVehicleInfo(dealership.getVehiclesByVin(vehicleChoice));
        int userChoice = Console.PromptForInt("\nIs this a Sale or a lease?\n  1)Sale\n  2)Lease\nPlease select one of the following options:");

        return new int[]{userChoice, vehicleChoice};
    }
    public SalesContract initializeSalesContract(int vin){
        LocalDateTime currentDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String contractDate = currentDate.format(formatter);
        String customerName = Console.PromptForString("Please enter your name: ");
        String customerEmail = Console.PromptForString("Please enter your email: ");
        boolean isFinancing = Console.PromptForYesNo("Would you like to finance? ");
        boolean isDownpayment = Console.PromptForYesNo("Would you like to make a down payment?");
        double downpayment = 0;
        if(isDownpayment)
            downpayment = Console.PromptForDouble("Enter down payment amount: ");
        SalesContract salesContract = new SalesContract(contractDate,customerName,customerEmail,vin,isFinancing,downpayment,dealership);
        ContractDataManager.addContract(salesContract);
        ContractDataManager.saveContracts();
        return salesContract;
    }
    public LeaseContract initializeLeaseContract(int vin){
        LocalDateTime currentDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String contractDate = currentDate.format(formatter);
        String customerName = Console.PromptForString("Please enter your name: ");
        String customerEmail = Console.PromptForString("Please enter your email: ");
        /*boolean isFinancing = Console.PromptForYesNo("Would you like to finance? ");
        boolean isDownpayment = Console.PromptForYesNo("Would you like to make a down payment?");
        double downpayment = 0;
        if(isDownpayment)
            downpayment = Console.PromptForDouble("Enter down payment amount: ");*/
        LeaseContract leaseContract = new LeaseContract(contractDate,customerName,customerEmail,vin,dealership);
        ContractDataManager.addContract(leaseContract);
        ContractDataManager.saveContracts();
        return leaseContract;
    }
}
