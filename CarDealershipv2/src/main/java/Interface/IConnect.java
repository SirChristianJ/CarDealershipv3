package Interface;

import DataManager.VehicleDoa;
import com.yearup.Dealership.Console;
import org.apache.commons.dbcp2.BasicDataSource;

public interface IConnect {
    default BasicDataSource connectDB(String[] args){
        String username = args[0];
        String password = args[1];
        // Create the datasource
        BasicDataSource dataSource = new BasicDataSource();
        // Configure the datasource
        dataSource.setUrl("jdbc:mysql://localhost:3306/sakila");
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;
    }
}
