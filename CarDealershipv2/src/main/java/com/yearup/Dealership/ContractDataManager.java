package com.yearup.Dealership;

import java.io.*;
import java.util.ArrayList;

public class ContractDataManager {
    static final String datafile = "contracts.csv";

    public static ArrayList<Contract> initializeContracts(){
        ArrayList<Contract> contracts = new ArrayList<>();

        try{
            BufferedReader bfr = new BufferedReader(new FileReader(datafile));
            String input;
            while ((input=bfr.readLine()) != null){
                String[] dataCategory = input.split("\\|");
                if (dataCategory.length >= 16){
                    if (dataCategory[0].equalsIgnoreCase("sale")) {
                        contracts.add(new SalesContract(dataCategory[1], dataCategory[2], dataCategory[3],
                                new Vehicle(dataCategory[4],
                                        Integer.parseInt(dataCategory[5]), dataCategory[6], dataCategory[7], dataCategory[8],
                                        dataCategory[9], Integer.parseInt(dataCategory[10]),
                                        Double.parseDouble(dataCategory[11])),
                                Double.parseDouble(dataCategory[12]), Double.parseDouble(dataCategory[13]),
                                Double.parseDouble(dataCategory[14]), Boolean.parseBoolean(dataCategory[15])));
                    } else if (dataCategory[0].equalsIgnoreCase("lease")) {
                        contracts.add(new LeaseContract(dataCategory[1], dataCategory[2], dataCategory[3],
                                new Vehicle(dataCategory[4], Integer.parseInt(dataCategory[5]),
                                        dataCategory[6], dataCategory[7], dataCategory[8],
                                        dataCategory[9], Integer.parseInt(dataCategory[10]), Double.parseDouble(dataCategory[11])),
                                Double.parseDouble(dataCategory[12]), Double.parseDouble(dataCategory[13])));
                    }
                }
            }
            bfr.close();
        } catch (IOException e) {
            System.out.println(e.getMessage() +  "<-- Error");
        }
        return contracts;
    }

    public static void saveContracts( ArrayList<Contract> contracts){
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(datafile));
            for(Contract contract: contracts){
                /***if(contract instanceof SalesContract salesContract){
                    bufferedWriter.write(salesContract.encodedString());
                }
                else if(contract instanceof LeaseContract leaseContract){
                    bufferedWriter.write(leaseContract.encodedString());
                }*/
                bufferedWriter.write(contract.encodedString());

            }

            bufferedWriter.close();
        } catch (Exception e) {
            System.out.println(e.getMessage() + "<-- FILE WRITE ERROR");
        }
    }

    public static void appendContracts( Contract contract){
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(datafile));
            bufferedWriter.write(contract.encodedString());
            bufferedWriter.close();
            }
            catch (IOException ex) {
                throw new RuntimeException(ex);
            }
    }
}
