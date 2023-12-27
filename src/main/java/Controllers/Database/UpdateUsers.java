package Controllers.Database;

import java.net.InetAddress;
import java.sql.*;

import static Controllers.Database.CreateDatabase.MESSAGE_DATABSE;

public class UpdateUsers {
    public static boolean addUser(InetAddress Address, String Nickname, String url) {

        try {
            CreateDatabase database = new CreateDatabase(url);
            Statement statement = database.connection.createStatement();
            String insertQuery = "INSERT INTO Users (ipAddress, name, status) VALUES (?, ?, ?)";

            try (PreparedStatement preparedStatement = database.connection.prepareStatement(insertQuery)) {
                // Set values for the parameters using the Users class methods
                // the preparedStatement.setString() method is used to set the values for the placeholders (?) in the SQL query
               preparedStatement.setString(1, Address.getHostAddress());
               preparedStatement.setString(2, Nickname);
               preparedStatement.setString(3,"Connected");

                preparedStatement.executeUpdate();
                System.out.println("User added successfully");

            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception
            return false;
        }
    }

    public static boolean changeStatus(InetAddress Address, String url){
        try {
            CreateDatabase database = new CreateDatabase(url);
            Statement statement = database.connection.createStatement();
            String strAddress = Address.getHostAddress();
            String updateQuery = "UPDATE Users SET Status = ? WHERE ipAddress = '"+strAddress+ "'";

            try (PreparedStatement preparedStatement = database.connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, "Diconnected");

                int rowsUpdated = preparedStatement.executeUpdate();

                if (rowsUpdated > 0) {
                    System.out.println("User updated successfully");
                } else {
                    System.out.println("No User found with Address: " + Address);
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
