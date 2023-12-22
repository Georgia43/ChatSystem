package Controllers.Database;

import java.net.InetAddress;
import java.sql.*;

import static Controllers.Database.CreateDatabase.connection;
import static Controllers.Database.CreateDatabase.MESSAGE_DATABSE;

public class UpdateUsers {
    public static void addUser(InetAddress Address, String Nickname) {

        try {
            CreateDatabase.Connect(MESSAGE_DATABSE);
            Statement statement = connection.createStatement();
            String insertQuery = "INSERT INTO Users (ipAddress, name, status) VALUES (?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                // Set values for the parameters using the Users class methods
                // the preparedStatement.setString() method is used to set the values for the placeholders (?) in the SQL query
               preparedStatement.setString(1, Address.getHostAddress());
               preparedStatement.setString(2, Nickname);
               preparedStatement.setString(3,"Connected");

                preparedStatement.executeUpdate();
                System.out.println("User added successfully");

            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception
        }
    }

    public static void changeStatus(InetAddress Address){
        try {
            CreateDatabase.Connect(MESSAGE_DATABSE);
            Statement statement = connection.createStatement();
            String strAddress = Address.getHostAddress();
            String updateQuery = "UPDATE Users SET Status = ? WHERE ipAddress = '"+strAddress+ "'";

            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, "Diconnected");

                int rowsUpdated = preparedStatement.executeUpdate();

                if (rowsUpdated > 0) {
                    System.out.println("User updated successfully");
                } else {
                    System.out.println("No User found with Address: " + Address);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
