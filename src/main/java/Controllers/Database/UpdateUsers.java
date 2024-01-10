package Controllers.Database;

import java.net.InetAddress;
import java.sql.*;


public class UpdateUsers {
    public static boolean addUser(InetAddress Address, String Nickname, String url) {

        try {
            CreateDatabase database = new CreateDatabase(url);

            // Check if the user already exists
            if (userExists(Address, database)) {
                System.out.println("User already exists");
                return false;

            }
            String insertQuery = "INSERT INTO Users (ipAddress, name) VALUES (?, ?)";

            try (PreparedStatement preparedStatement = database.connection.prepareStatement(insertQuery)) {
                // Set values for the parameters using the Users class methods
                // the preparedStatement.setString() method is used to set the values for the placeholders (?) in the SQL query
               preparedStatement.setString(1, Address.getHostAddress());
               preparedStatement.setString(2, Nickname);

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

    private static boolean userExists(InetAddress address, CreateDatabase database) throws SQLException {
        String query = "SELECT COUNT(*) FROM Users WHERE ipAddress = ?";

        try (PreparedStatement preparedStatement = database.connection.prepareStatement(query)) {
            preparedStatement.setString(1, address.getHostAddress());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0; // User exists if count is greater than 0
            }
        }

        return false;
    }

   /* public static boolean changeStatusToDisconnected(InetAddress Address, String url){
        try {
            CreateDatabase database = new CreateDatabase(url);
          //  Statement statement = database.connection.createStatement();
            String strAddress = Address.getHostAddress();
            System.out.println("[UpdateUsers] Connected to the database to disconnect.");
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
    }*/

   /* public static boolean changeStatusToConnected(InetAddress Address, String url){
        try {
            CreateDatabase database = new CreateDatabase(url);
            Statement statement = database.connection.createStatement();
            String strAddress = Address.getHostAddress();

            String updateQuery = "UPDATE Users SET Status = ? WHERE ipAddress = '"+strAddress+ "'";

            try (PreparedStatement preparedStatement = database.connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, "Connected");

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
    }*/
    //verifier si le pseudo entré est le meme que celui de la database quand la personne se connecte de nouveau
    public static boolean NicknameIsSame(String nickname, String url) {

        try {
            CreateDatabase database = new CreateDatabase(url);
            Statement statement = database.connection.createStatement();
            String query = "SELECT COUNT(*) AS count FROM USERS WHERE name = ?";
            PreparedStatement preparedStatement = database.connection.prepareStatement(query);
            preparedStatement.setString(1, nickname);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt("count");
                    return count > 0; // If count is greater than 0, the nickname exists
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    // changer le pseudo dans la database
    public static boolean changeNicknameDB(InetAddress Address, String name, String url){
        try {
            CreateDatabase database = new CreateDatabase(url);
            Statement statement = database.connection.createStatement();
            String strAddress = Address.getHostAddress();

            String updateQuery = "UPDATE Users SET name = ? WHERE ipAddress = '"+strAddress+ "'";

            try (PreparedStatement preparedStatement = database.connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, name);

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
