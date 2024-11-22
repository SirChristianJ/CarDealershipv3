package com.yearup.Dealership;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DealershipFileManager {
    static final String datafile_vehicles = "vehicles.csv";
    static final String datafile_dealerships = "dealerships.csv";
    private static List<Dealership> dealerships = getDealerships();
    private static Dealership dealership;

    public static List<Dealership> getDealerships(){
        List<Dealership> test = new ArrayList<>();

        try {
            BufferedReader bfr = new BufferedReader(new FileReader(datafile_dealerships));
            String input;
            bfr.readLine();
            while ((input = bfr.readLine())!=null){
                String[] dataCategory = input.split("\\|");
                Dealership d = new Dealership(dataCategory[0],dataCategory[1],dataCategory[2]);
                test.add(d);
            }
        }
        catch (IOException e) {
            throw new RuntimeException("File not found!");
        }

        return test;
    }

    public static Dealership getDealership(String dealershipName){
        ArrayList<Vehicle> inventory = new ArrayList<>();

        try{
            do {
                if (dealerships.stream().anyMatch(dealership -> dealership.getName().equalsIgnoreCase(dealershipName))) {
                    String dealerName = dealershipName + "/";
                    BufferedReader bfr = new BufferedReader(new FileReader(dealerName + datafile_vehicles));
                    String input;
                    bfr.readLine();
                    while ((input = bfr.readLine()) != null) {
                        String[] dataCategory = input.split("\\|");
                        Vehicle v = new Vehicle(dataCategory[0],
                                Integer.parseInt(dataCategory[1]),
                                dataCategory[2],
                                dataCategory[3],
                                dataCategory[4],
                                dataCategory[5],
                                Integer.parseInt(dataCategory[6]),
                                Double.parseDouble(dataCategory[7])
                        );
                        inventory.add(v);
                    }

                    dealership = new Dealership(dealershipName);
                    dealership.setInventory(inventory);

                    return dealership;
                }
            }while (true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void saveDealership(ArrayList<Vehicle> inventory){
        try {
            String dealerName = dealership.getName() + "/";
            Path path = Paths.get(dealerName);
            Files.createDirectories(path);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(dealerName + datafile_vehicles));
            bufferedWriter.write(dealership.toString());
            for (Vehicle v : inventory) {
                String data = v.toString();
                bufferedWriter.write(data);
            }


            bufferedWriter.close();
        } catch (Exception e) {
            System.out.println("FILE WRITE ERROR");
        }
    }
}
