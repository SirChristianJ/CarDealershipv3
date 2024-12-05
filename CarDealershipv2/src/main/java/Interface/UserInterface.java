package Interface;

import DataManager.VehicleDoa;
import com.yearup.Dealership.*;

import java.util.ArrayList;

public class UserInterface implements IConnect {
    private int dealership_id = 0;
    private ArrayList<Contract> contracts = ContractDataManager.initializeContracts();
    private static VehicleDoa vehicleDoa;

    public void passArgs(String[] args){
        vehicleDoa = new VehicleDoa(connectDB(args));
        System.out.println("Here's a list of dealerships!");
        vehicleDoa.getDealershipsAll().forEach(System.out::println);

        dealership_id = Console.PromptForInt("Enter the id of the dealership:");
    }
    //Display methods
    public void display() {
        do {
            String menuString = """
                     -------------------------------------------------------
                    
                           Welcome to the Object Oriented Dealership!
                       Current Dealership:
                       %s
                    ________________________________________________________
                           1)Filter by price
                           2)Filter by make/model
                           3)Filter by year
                           4)Filter by color
                           5)Filter by mileage
                           6)Filter by vehicle type
                           7)View all vehicles
                           8)Add vehicle
                           9)Remove vehicle
                           10)SELL/LEASE Vehicle
                           99)Exit
                    \s""";

            String name = "";
            for (Dealership dealership : vehicleDoa.getDealershipsAll()) {
                if (vehicleDoa.getDealershipsAll().stream().anyMatch(dealershipid -> dealership.getId() == dealership_id)) {
                    name = dealership.getName().trim();
                }
            }
            System.out.printf(menuString,name);

            try {
                short choice = Console.PromptForShort("Enter a selection: ");
                switch (choice) {
                    case 1 -> {
                        int minRange = Console.PromptForInt("Enter a min range:");
                        int maxRange = Console.PromptForInt("Enter a max range: ");
                        vehicleDoa.getVehiclesByPriceRange(dealership_id,minRange,maxRange).forEach(System.out::println);
                    }

                    case 2 -> {
                        String makeRange = Console.PromptForString("Enter a make:");
                        String modelRange = Console.PromptForString("Enter a model: ");
                        vehicleDoa.getVehiclesByMake(dealership_id,makeRange,modelRange).forEach(System.out::println);
                    }

                    case 3 -> {
                        int minYearRange = Console.PromptForInt("Enter a min year: ");
                        int maxYearRange = Console.PromptForInt("Enter a max year: ");
                        vehicleDoa.getVehiclesByYear(dealership_id,minYearRange,maxYearRange).forEach(System.out::println);
                    }

                    case 4 -> {
                        String colorRange = Console.PromptForString("Enter a color: ");
                        vehicleDoa.getVehiclesByColor(dealership_id,colorRange).forEach(System.out::println);
                    }

                    case 5 -> {
                        int minMileageRange = Console.PromptForInt("Enter a min mileage: ");
                        int maxMileageRange = Console.PromptForInt("Enter a max mileage: ");
                        vehicleDoa.getVehiclesByMilage(dealership_id,minMileageRange,maxMileageRange).forEach(System.out::println);
                    }

                    case 6 -> {
                        String vehicleTypeQuery = Console.PromptForString("Enter a vehicle type: ");
                        vehicleDoa.getVehiclesByType(dealership_id,vehicleTypeQuery).forEach(System.out::println);
                    }

                    case 7 -> {
                        vehicleDoa.getVehiclesAll(dealership_id).forEach(System.out::println);
                    }

                    case 8 -> {
                        promptForAddingVehicle();
                    }
/*
                    case 9 -> {promptForRemovingVehicle();}

                    case 10 -> {processSellOrLeaseVehicle();}*/

                    case 99 -> {
                        System.out.println("Exiting ...");
                        return;
                    }

                    default -> {
                        System.out.println("Please enter one of the available selections!\n");
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Enter a numerical number!");
            }
            String keyPress = Console.PromptForString("\nPress any key to return to previous menu: ");

        } while (true);
    }

    //adding vehicle methods
    public void promptForAddingVehicle(){
        String vinToAdd = Console.PromptForString("Add a vin: ");
        int yearToAdd = Console.PromptForInt("Add a year: ");
        String makeToAdd = Console.PromptForString("Add a make: ");
        String modelToAdd = Console.PromptForString("Add a model: ");
        String vehicleTypeToAdd = Console.PromptForString("What type of vehicle is this (truck,suv,van,etc): ");
        String colorToAdd = Console.PromptForString("What color is this vehicle: ");
        int odometerToAdd = Console.PromptForInt("Add odometer: ");
        double priceToAdd = Console.PromptForDouble("Add price: ");

        vehicleDoa.addVehicleToDealership(dealership_id,new Vehicle(vinToAdd,
                                                        yearToAdd,
                                                        makeToAdd,
                                                        modelToAdd,
                                                        vehicleTypeToAdd,
                                                        colorToAdd,
                                                        odometerToAdd,
                                                        priceToAdd)
        );
    }

    /*@Override
    public BasicDataSource connectDB(String[] args) {
        String username = args[0];
        String password = args[1];
        // Create the datasource
        BasicDataSource dataSource = new BasicDataSource();
        // Configure the datasource
        dataSource.setUrl("jdbc:mysql://localhost:3306/sakila");
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        vehicleDoa = new VehicleDoa(dataSource);
        System.out.println("Here's a list of dealerships!");
        vehicleDoa.getDealershipsAll().forEach(System.out::println);

        dealership_id = Console.PromptForInt("Enter the id of the dealership:");
        return dataSource;
    }*/
}
 /*   public void displayInventory(List<Vehicle> inventory){
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


    //removing vehicle from inventory via UI
    public void promptForRemovingVehicle(){
        String vinToRemove = Console.PromptForString("Add a vin: ");
        dealership.removeVehicle(vinToRemove);
    }


    //contract related methods
    public String promptForPurchasingVehicle(){
        do{
            System.out.println("---------------------------------------------");
            System.out.println("Welcome to the Object Oriented Dealership!");
            System.out.println("---------------------------------------------");
            displayInventoryByVinYearMakeModel(dealership.getAllVehicles());
            String vehicleChoice = Console.PromptForString("Enter the vin of the vehicle you wish to purchase: ");
            try {
                displaySingleVehicleInfo(dealership.getVehiclesByVin(vehicleChoice));
                boolean isConfirmedVehicle = Console.PromptForYesNo("Is this the vehicle you wish to purchase?");
                if(isConfirmedVehicle) {
                    return vehicleChoice;
                }
            } catch (Exception e) {
                System.out.println("This vin is either not available or does not exist. Please Try again.");
            }

        }while (true);
    }
    public void processSellOrLeaseVehicle(){
        int userChoice = Console.PromptForInt("\nIs this a Sale or a lease?\n  1)Sale\n  2)Lease\nPlease select one of the following options:");
        String vehicleVin = promptForPurchasingVehicle();

        if(userChoice == 1){
            displaySalesContractInfo(initializeSalesContract(vehicleVin));
        } else if (userChoice == 2) {
            displayLeaseContractInfo(initializeLeaseContract(vehicleVin));
        }
    }


    public SalesContract initializeSalesContract(String vin){
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

        SalesContract salesContract = new SalesContract(contractDate,customerName,customerEmail,dealership.getVehiclesByVin(vin),isFinancing);
        contracts.add(salesContract);
        ContractDataManager.appendContracts(salesContract);
        //ContractDataManager.saveContracts(contracts);
        dealership.removeVehicle(vin);
        return salesContract;
    }
    public LeaseContract initializeLeaseContract(String vin){
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

        LeaseContract leaseContract = new LeaseContract(contractDate,customerName,customerEmail,dealership.getVehiclesByVin(vin));
        contracts.add(leaseContract);
        ContractDataManager.appendContracts(leaseContract);
        //ContractDataManager.saveContracts(contracts);

        dealership.removeVehicle(vin);
        return leaseContract;
    }
    public void displaySalesContractInfo(SalesContract salesContract){
        System.out.println(salesContract);
    }
    public void displayLeaseContractInfo(LeaseContract leaseContract){
        System.out.println(leaseContract);
    }
}
*/
