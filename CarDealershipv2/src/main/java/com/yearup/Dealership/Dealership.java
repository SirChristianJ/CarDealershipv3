package com.yearup.Dealership;

import DataManager.VehicleDoa;

import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.function.Predicate;

public class Dealership {
    private int id;
    private String name;
    private String address;
    private String phone;
    private ArrayList<Vehicle> inventory;
    private VehicleDoa doa;

    public Dealership(String name){
        this.name = name;
        this.address = "";
        this.phone = "";
        this.inventory = null;
    }
    public Dealership(int id, String name, String address, String phone) {
        this.id =id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.inventory = new ArrayList<>();
    }

    public Vehicle getVehiclesByVin(String  vin){
        for(Vehicle v: inventory){
            if(v.getVin().equalsIgnoreCase(vin)){
                return v;
            }
        }
        return null;
    }

    public ArrayList<Vehicle> getVehiclesByPrice(int min, int max){
        ArrayList<Vehicle> priceRangeArray = new ArrayList<>();
        for(Vehicle v: inventory){
            if(v.getPrice() >= min && v.getPrice() <= max){
                priceRangeArray.add(v);
            }
        }

        return priceRangeArray;
    }
    public ArrayList<Vehicle> getVehiclesByMakeModel(String make, String model){
        ArrayList<Vehicle> makeModelRangeArray = new ArrayList<>();
        for(Vehicle v: inventory){
            if(v.getMake().equalsIgnoreCase(make) && v.getModel().equalsIgnoreCase(model)){
                makeModelRangeArray.add(v);
            }
        }

        return makeModelRangeArray;
    }
    public ArrayList<Vehicle> getVehiclesByYear(int min, int max){
        ArrayList<Vehicle> yearRangeArray = new ArrayList<>();
        for(Vehicle v: inventory){
            if(v.getYear() >= min && v.getYear() <= max){
                yearRangeArray.add(v);
            }
        }

        return yearRangeArray;
    }
    public ArrayList<Vehicle> getVehiclesByColor(String color){
        ArrayList<Vehicle> colorRangeArray = new ArrayList<>();
        for(Vehicle v: inventory){
            if(v.getColor().equalsIgnoreCase(color)){
                colorRangeArray.add(v);
            }
        }

        return colorRangeArray;
    }
    public ArrayList<Vehicle> getVehiclesByMileage(int min, int max){
        ArrayList<Vehicle> milesRangeArray = new ArrayList<>();
        for(Vehicle v: inventory){
            if(v.getOdometer() >= min && v.getOdometer() <= max){
                milesRangeArray.add(v);
            }
        }

        return milesRangeArray;
    }
    public ArrayList<Vehicle> getVehiclesByType(String vehicleType){
        ArrayList<Vehicle> vehiclesTypeArray = new ArrayList<>();
        for(Vehicle v: inventory){
            if(v.getVehicleType().equalsIgnoreCase(vehicleType)){
                vehiclesTypeArray.add(v);
            }
        }

        return vehiclesTypeArray;
    }
    public ArrayList<Vehicle> getAllVehicles(){
        return inventory;
    }

    public void setInventory(ArrayList<Vehicle> inventory){
        this.inventory = inventory;
    }
    /*public void addVehicle(Vehicle v){
        try{
            this.inventory.add(v);
            DealershipFileManager.saveDealership(inventory);
        }
        catch(Exception e)
        {
            e.getMessage();
        }
    }
    public void removeVehicle(String vinToQuery){
        try{
            inventory.removeIf(vehicle -> vehicle.getVin().equalsIgnoreCase(vinToQuery));
            DealershipFileManager.saveDealership(inventory);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }*/

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString(){
        return String.format("%d %-10s %-10s %-10s\n", id,name,address,phone);
    }
}
