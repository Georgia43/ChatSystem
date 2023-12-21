package Controllers.Database;
import Controllers.Broadcast;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class CreateDatabase {

    public static String url = "jdbc:sqlite:BDDchatsystem.db";
    public static Connection connection = null;
    public static boolean Connect(String url) {
        try{
            connection = DriverManager.getConnection(url);
            System.out.println("Connecté à la base de données.");
            return true;
        } catch (SQLException e) {
            System.out.println("Connection a échoué.");
            return false;
        }
    }

    public static void tableUsers() throws SQLException {
        try {
            Statement statement = connection.createStatement();

            String usersTable = "CREATE TABLE IF NOT EXISTS Users ("
                    + "ipAddress VARCHAR(30) PRIMARY KEY, "
                    + "name VARCHAR(20) NOT NULL, "
                    + "status INT NOT NULL)";
            statement.executeUpdate(usersTable);
            System.out.println("Table Users créée.");
        } catch (SQLException e) {
            System.out.println("Creation de la table a échoué.");
        }
    }

    public static void tableMessages(InetAddress address) throws SQLException {

        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement()) {
            String Messages = "CREATE TABLE IF NOT EXISTS Messages_" + address + "("
                    + "content VARCHAR(100) PRIMARY KEY,"
                    + "dateHeure DATETIME)";
            statement.executeUpdate(Messages);
            System.out.println("Table 'Messages_' " + address + " créée.");
        }
        catch (SQLException e) {
                System.out.println("Erreur création table 'Messages_' " + address + ".");
            }
    }
}
