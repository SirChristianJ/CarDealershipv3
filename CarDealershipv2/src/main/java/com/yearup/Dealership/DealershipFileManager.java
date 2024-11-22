package com.yearup.Dealership;

import java.io.*;
import java.util.ArrayList;

public class DealershipFileManager {
    static final String datafile = "vehicles.csv";
    private static Dealership dealership = getDealership();

    public static Dealership getDealership(){
        Dealership test;
        ArrayList<Vehicle> inventory = new ArrayList<>();
        try{
            BufferedReader bfr = new BufferedReader(new FileReader(datafile));
            String input;
            String[] dealerInfo = bfr.readLine().split("\\|");

            while ((input=bfr.readLine()) != null){
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

            test = new Dealership(dealerInfo[0],dealerInfo[1],dealerInfo[2]);
            test.setInventory(inventory);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
        return test;
    }
    public static void saveDealership(ArrayList<Vehicle> inventory){
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(datafile));
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
