package Controllers.Database;

import java.net.InetAddress;
import java.sql.*;


public class UpdateUsers {

    // ajouter un utilisateur dans la table
    public static boolean addUser(InetAddress Address, String Nickname, String url) {

        try {
            CreateTables database = new CreateTables(url);

            //s'il existe déjà, on ne l'ajoute pas
            if (userExists(Address, url)) {
                System.out.println("User already exists");
                return true;

            }

            String insertQuery = "INSERT INTO Users (ipAddress, name) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = database.connection.prepareStatement(insertQuery)) {
               preparedStatement.setString(1, Address.getHostAddress());
               preparedStatement.setString(2, Nickname);
                preparedStatement.executeUpdate();
                System.out.println("User added successfully");
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // regarde si l'utilisateur donnée existe déjà ou pas
    public static boolean userExists(InetAddress address, String url) throws SQLException {
        String query = "SELECT COUNT(*) FROM Users WHERE ipAddress = ?";
        CreateTables database = new CreateTables(url);
        try (PreparedStatement preparedStatement = database.connection.prepareStatement(query)) {
            preparedStatement.setString(1, address.getHostAddress());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0; // l'utilisateur existe si count > O
            }
        }
        return false;
    }

    //verifier si le pseudo entré est le meme que celui de la database quand la personne se connecte de nouveau
    public static boolean NicknameIsSame(String nickname, String url) {

        try {
            CreateTables database = new CreateTables(url);
            String query = "SELECT COUNT(*) AS count FROM USERS WHERE name = ?";
            PreparedStatement preparedStatement = database.connection.prepareStatement(query);
            preparedStatement.setString(1, nickname);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt("count");
                    return count > 0; // le pseudo est le même si count > 0
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
            CreateTables database = new CreateTables(url);
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
