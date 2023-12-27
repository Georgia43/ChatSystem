package Controllers.Database;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

import static Controllers.Broadcast.logger;


public class CreateDatabase {

    public static String MESSAGE_DATABSE = "jdbc:sqlite:BDDchatsystem.db";
    public Connection connection = null;

    public String url;

    public CreateDatabase(String url) {
        this.url = url;
        try{
            connection = DriverManager.getConnection(url);
            System.out.println("Connecté à la base de données.");
        } catch (SQLException e) {
            System.out.println("Connection a échoué.");
        }
    }

    public void tableUsers() throws SQLException {
        try {
            Statement statement = connection.createStatement();

            String usersTable = "CREATE TABLE IF NOT EXISTS Users ("
                    + "ipAddress VARCHAR(30) PRIMARY KEY, "
                    + "name VARCHAR(20) NOT NULL, "
                    + "status VARCHAR(20) NOT NULL)";
            statement.executeUpdate(usersTable);
            System.out.println("Table Users créée.");
        } catch (SQLException e) {
            System.out.println("Creation de la table a échoué.");
        }
    }

    public void tableMessages(InetAddress address) throws SQLException {

        try{
            Statement statement = connection.createStatement();
            String strAddress = address.getHostAddress().replace('.', '_');
            String Messages = "CREATE TABLE IF NOT EXISTS Messages_" + strAddress + "("
                    + "idMessage INTEGER PRIMARY KEY,"
                    + "content VARCHAR(200),"
                    + "dateHeure DATETIME)";
            statement.executeUpdate(Messages);
            System.out.println("test");
            System.out.println("Table Messages_" + strAddress + " créée.");
        }
        catch (SQLException e) {
            logger.log(Level.SEVERE,"IOException: " + e.getMessage());
            }
    }
}
