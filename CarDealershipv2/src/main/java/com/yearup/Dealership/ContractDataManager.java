package com.yearup.Dealership;

import java.io.*;
import java.util.ArrayList;

public class ContractDataManager {
    static final String datafile = "contracts.csv";
    private static Dealership dealership = DealershipFileManager.getDealership();
    private static ArrayList<Contract> contracts = initializeContracts();

    public static ArrayList<Contract> initializeContracts(){
        ArrayList<Contract> contracts = new ArrayList<>();

        try{
            BufferedReader bfr = new BufferedReader(new FileReader(datafile));
            String input;
            while ((input=bfr.readLine()) != null){
                String[] dataCategory = input.split("\\|");
                if(dataCategory[0].equalsIgnoreCase("sale")){
                    contracts.add(new SalesContract(dataCategory[1],dataCategory[2],dataCategory[3],Integer.parseInt(dataCategory[4]), dealership.getVehiclesByVin(Integer.parseInt(dataCategory[4])),
                            Double.parseDouble(dataCategory[12]),Double.parseDouble(dataCategory[13]),Double.parseDouble(dataCategory[14]),
                            Double.parseDouble(dataCategory[15]),Boolean.getBoolean(dataCategory[16]), Double.parseDouble(dataCategory[17])));
                }

               /* else {
                    contracts.add(new LeaseContract(dataCategory[1],dataCategory[2],dataCategory[3],Integer.parseInt(dataCategory[4]),
                            Double.parseDouble(dataCategory[12]),Double.parseDouble(dataCategory[13]),Double.parseDouble(dataCategory[14]),
                            Double.parseDouble(dataCategory[15]),Boolean.getBoolean(dataCategory[16]), Double.parseDouble(dataCategory[17])));
                }*/
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return contracts;
    }

    public static void addContract(Contract contract){
        ContractDataManager.contracts.add(contract);
    }

    public static void setContracts(ArrayList<Contract> contracts){
        ContractDataManager.contracts = contracts;
    }

    public static ArrayList<Contract> getContracts(){
        return contracts;
    }

    public static void saveContracts(){
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(datafile));
            for(Contract contract: contracts){
                if(contract instanceof SalesContract salesContract){
                    bufferedWriter.write(salesContract.encodedString());
                    dealership.removeVehicle(salesContract.getVehicleSold());

                }
                else if(contract instanceof LeaseContract leaseContract){
                    bufferedWriter.write(leaseContract.encodedString());
                    dealership.removeVehicle(leaseContract.getVehicleSold());
                }
            }

            bufferedWriter.close();
        } catch (Exception e) {
            System.out.println("FILE WRITE ERROR");
        }
    }

}
