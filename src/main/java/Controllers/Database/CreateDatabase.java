package Controllers.Database;
import Controllers.Broadcast;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;


public class CreateDatabase {

    public static String url = "jdbc:sqlite:BDDchatsystem";
    public static void tableUsers() throws SQLException {

        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement()){

            String Users = "CREATE TABLE IF NOT EXISTS Users ("
                    + "ipAddress VARCHAR(30) PRIMARY KEY, "
                    + "name VARCHAR(20) NOT NULL"
                    + "status INT NOT NULL)";
            statement.executeUpdate(Users);

        }
        catch (SQLException e) {
            Broadcast.logger.log(Level.SEVERE,"IOException: " + e.getMessage());
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
                Broadcast.logger.log(Level.SEVERE,"IOException: " + e.getMessage());
            }
    }

    public static void main(String[] args) throws SQLException {
        tableUsers();
    }
    }
