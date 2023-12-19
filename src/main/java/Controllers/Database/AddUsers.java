package Controllers.Database;

import java.net.InetAddress;
import java.sql.*;

import static Controllers.Database.CreateDatabase.url;

public class AddUsers {
    public void addUser(InetAddress Address) {

        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement()){
            String insertQuery = "INSERT INTO Users (ipAddress, name, status) VALUES (?, ?, ?)";

            // Using PreparedStatement to prevent SQL injection
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                // Set values for the parameters using the Users class methods
                // the preparedStatement.setString() method is used to set the values for the placeholders (?) in the SQL query
               /* preparedStatement.setString(1, Address.getId());

                preparedStatement.executeUpdate();
                System.out.println("User added successfully");

                String Id= user.getId();
                String FirstName= user.getFirstName();
                String LastName = user.getLastName();
                String Email = user.getEmail();
                String Password = user.getPassword();*/
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception
        }
    }
}
