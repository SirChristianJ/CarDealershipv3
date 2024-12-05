package DataManager;

import com.yearup.Dealership.Dealership;
import com.yearup.Dealership.Vehicle;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehicleDoa {
    private final BasicDataSource dataSource;

    public VehicleDoa(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }

    private boolean checkForValidDealershipID(int dealershipID){
        return getDealershipsAll().stream().anyMatch(dealership -> dealership.getId()==dealershipID);
    }

    private Vehicle mapResultSetToVehicle(ResultSet resultSet){
        try{
            return new Vehicle(
                    resultSet.getString("vin"),
                    resultSet.getInt("year"),
                    resultSet.getString("make"),
                    resultSet.getString("model"),
                    resultSet.getString("type"),
                    resultSet.getString("color"),
                    resultSet.getInt("odometer"),
                    resultSet.getDouble("price")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Vehicle> executeQuery(String sql, Object ... params ){
        List<Vehicle> vehicleList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    vehicleList.add(mapResultSetToVehicle(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return vehicleList;

    }

    public Vehicle getVehicleByVIN(int dealershipID, String vin){
        return null;
    }

    public List<Vehicle> getVehiclesByPriceRange(int dealershipID, double min, double max){
        List<Vehicle> vehicleList = new ArrayList<>();
        if(checkForValidDealershipID(dealershipID)){
            String sql =
                    """
                            SELECT
                            dealerships.dealership_id\s
                            ,vehicles.vin
                            ,year
                            ,make
                            ,model
                            ,type
                            ,color
                            ,odometer
                            ,price
                            ,SOLD FROM cardealership.vehicles
                            INNER JOIN cardealership.inventory
                            		ON inventory.vin = vehicles.vin
                            INNER JOIN cardealership.dealerships
                            		ON dealerships.dealership_id = inventory.dealership_id
                            WHERE dealerships.dealership_id = ? AND vehicles.price BETWEEN ? AND ?;
                    """;
           vehicleList = executeQuery(sql,dealershipID,min,max);
        }
        return vehicleList;
    }

    public List<Vehicle> getVehiclesByMake(int dealershipID, String make, String model){
        List<Vehicle> vehicleList = new ArrayList<>();
        if(checkForValidDealershipID(dealershipID)){
            String sql =
                    """
                            SELECT
                            dealerships.dealership_id\s
                            ,vehicles.vin
                            ,year
                            ,make
                            ,model
                            ,type
                            ,color
                            ,odometer
                            ,price
                            ,SOLD FROM cardealership.vehicles
                            INNER JOIN cardealership.inventory
                                    ON inventory.vin = vehicles.vin
                            INNER JOIN cardealership.dealerships
                                    ON dealerships.dealership_id = inventory.dealership_id
                            WHERE dealerships.dealership_id = ? AND vehicles.make = ? AND vehicles.model = ?;
                    """;
            vehicleList = executeQuery(sql,dealershipID,make,model);
        }
        return vehicleList;
    }

    public List<Vehicle> getVehiclesByYear(int dealershipID, int minYear, int maxYear){
        List<Vehicle> vehicleList = new ArrayList<>();
        if(checkForValidDealershipID(dealershipID)){
            String sql =
                    """
                            SELECT
                            dealerships.dealership_id\s
                            ,vehicles.vin
                            ,year
                            ,make
                            ,model
                            ,type
                            ,color
                            ,odometer
                            ,price
                            ,SOLD FROM cardealership.vehicles
                            INNER JOIN cardealership.inventory
                                    ON inventory.vin = vehicles.vin
                            INNER JOIN cardealership.dealerships
                                    ON dealerships.dealership_id = inventory.dealership_id
                            WHERE dealerships.dealership_id = ? AND vehicles.year BETWEEN ? AND ?;
                    """;
            vehicleList = executeQuery(sql,dealershipID,minYear,maxYear);
        }
        return vehicleList;
    }

    public List<Vehicle> getVehiclesByColor(int dealershipID, String color ){
        List<Vehicle> vehicleList = new ArrayList<>();
        String sql =
                """
                        SELECT
                        dealerships.dealership_id\s
                        ,vehicles.vin
                        ,year
                        ,make
                        ,model
                        ,type
                        ,color
                        ,odometer
                        ,price
                        ,SOLD FROM cardealership.vehicles
                        INNER JOIN cardealership.inventory
                        		ON inventory.vin = vehicles.vin
                        INNER JOIN cardealership.dealerships
                        		ON dealerships.dealership_id = inventory.dealership_id
                        WHERE dealerships.dealership_id = ? AND vehicles.color = ?;
                """;
        vehicleList = executeQuery(sql,dealershipID,color);
        return vehicleList;
    }

    public List<Vehicle> getVehiclesByMilage(int dealershipID, int min, int max){
        List<Vehicle> vehicleList = new ArrayList<>();
        if(checkForValidDealershipID(dealershipID)){
            String sql =
                    """
                                    SELECT
                                    dealerships.dealership_id\s
                                    ,vehicles.vin
                                    ,year
                                    ,make
                                    ,model
                                    ,type
                                    ,color
                                    ,odometer
                                    ,price
                                    ,SOLD FROM cardealership.vehicles
                                    INNER JOIN cardealership.inventory
                                    		ON inventory.vin = vehicles.vin
                                    INNER JOIN cardealership.dealerships
                                    		ON dealerships.dealership_id = inventory.dealership_id
                                    WHERE dealerships.dealership_id = ? AND vehicles.odometer BETWEEN ? AND ?;
                            """;
            vehicleList = executeQuery(sql, dealershipID, min, max);
        }
        return vehicleList;
    }

    public List<Vehicle> getVehiclesByType(int dealershipID, String type){
        List<Vehicle> vehicleList = new ArrayList<>();
        if(checkForValidDealershipID(dealershipID)) {
            String sql =
                    """
                                    SELECT
                                    dealerships.dealership_id\s
                                    ,vehicles.vin
                                    ,year
                                    ,make
                                    ,model
                                    ,type
                                    ,color
                                    ,odometer
                                    ,price
                                    ,SOLD FROM cardealership.vehicles
                                    INNER JOIN cardealership.inventory
                                    		ON inventory.vin = vehicles.vin
                                    INNER JOIN cardealership.dealerships
                                    		ON dealerships.dealership_id = inventory.dealership_id
                                    WHERE dealerships.dealership_id = ? AND vehicles.type = ?;
                            """;
            vehicleList = executeQuery(sql, dealershipID, type);
        }
        return vehicleList;
    }

    public List<Vehicle> getVehiclesAll(int dealershipID){
        List<Vehicle> vehicleList = new ArrayList<>();

        String sql =
                """
                 SELECT\s
                 vehicles.vin
                 ,year
                 ,make
                 ,model
                 ,type
                 ,color
                 ,odometer
                 ,price
                 ,SOLD FROM cardealership.vehicles
                 INNER JOIN cardealership.inventory
                        ON inventory.vin = vehicles.vin
                 INNER JOIN cardealership.dealerships
                        ON dealerships.dealership_id = inventory.dealership_id
                 WHERE inventory.dealership_id = ?;
                """;

        vehicleList = executeQuery(sql,dealershipID);
        return vehicleList;
    }

    public List<Dealership> getDealershipsAll(){
        List<Dealership> dealershipList = new ArrayList<>();

        String sql =
                """
                        SELECT\s
                        *
                        FROM cardealership.dealerships;
                """;

        try (Connection conn = dataSource.getConnection();){
            try(PreparedStatement statement = conn.prepareStatement(sql);){
                try (ResultSet results = statement.executeQuery();){
                    while (results.next()){
                        dealershipList.add(new Dealership(
                                results.getInt("dealership_id"),
                                results.getString("name"),
                                results.getString("address"),
                                results.getString("phone")
                        ));
                    }
                }
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dealershipList;
    }

    public Vehicle addVehicleToDealership(int dealershipID, Vehicle v){
        if (checkForValidDealershipID(dealershipID)) {
            String sqlquery1 =
                    """
                    INSERT INTO cardealership.inventory(
                        dealership_id, vin
                    )
                    VALUES (
                        ?,?
                    );
                    """;
            String sqlquery2 =
                    """ 
                    INSERT INTO cardealership.vehicles(
                        vin, year, make, model, type, color, odometer, price, SOLD
                    )
                    VALUES (
                        ?,?,?,?,?,?,?,?,null
                    );
                    """;
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement(sqlquery1);
                 PreparedStatement statement2 = connection.prepareStatement(sqlquery2)
            ) {
                //set parameters
                statement.setInt(1,dealershipID);
                statement.setString(2,v.getVin());

                statement2.setString(1, v.getVin());
                statement2.setInt(2, v.getYear());
                statement2.setString(3, v.getMake());
                statement2.setString(4, v.getModel());
                statement2.setString(5, v.getVehicleType());
                statement2.setString(6, v.getColor());
                statement2.setInt(7, v.getOdometer());
                statement2.setDouble(8, v.getPrice());

                // update statement
                int rows = statement.executeUpdate();
                statement2.executeUpdate();
                // confirm the update
                System.out.printf("Rows updated %d\n", rows);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return new Vehicle(v.getVin(), v.getYear(),v.getMake(), v.getModel(), v.getVehicleType(), v.getColor(), v.getOdometer(), v.getPrice());
    }


    public BasicDataSource getDataSource() {
        return dataSource;
    }


}
