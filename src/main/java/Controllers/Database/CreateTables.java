package Controllers.Database;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

import static Controllers.Broadcast.logger;

public class CreateTables {
    public Connection connection = null;

    public String url;

    public CreateTables(String url) { // pour se connecter à la base de données
        this.url = url;
        try{
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
          e.printStackTrace();
        }
    }

    // on créé une URL par adress IP, pour créer une base de données différente pour chaque machine
    public static String createURL(InetAddress ipaddress) {
        String strAddress = ipaddress.getHostAddress().replace('.', '_');
        System.out.println("[CreateDatabase] database -- "+ strAddress);
        return "jdbc:sqlite:BDDchatsystem" + strAddress + ".db";
    }

    // création de la table Users
    public void tableUsers() throws SQLException {
        try {
            Statement statement = connection.createStatement();

            String usersTable = "CREATE TABLE IF NOT EXISTS Users ("
                    + "ipAddress VARCHAR(30) PRIMARY KEY, "
                    + "name VARCHAR(20) NOT NULL) ";
            statement.executeUpdate(usersTable);
            System.out.println("Table Users créée.");
        } catch (SQLException e) {
            System.out.println("Creation de la table a échoué.");
        }
    }

    // création d'une table messages par contact (avec l'adresse IP pour l'individualiser)
    // la clé primaire correspond à l'heure et la date d'envoi du message
    public void tableMessages(InetAddress address) {

        try{
            Statement statement = connection.createStatement();
            String strAddress = address.getHostAddress().replace('.', '_');
            String Messages = "CREATE TABLE IF NOT EXISTS Messages_" + strAddress + "("
                    + "dateHeure DATETIME INTEGER PRIMARY KEY,"
                    + "sender VARCHAR(20),"
                    + "content VARCHAR(200))";
            statement.executeUpdate(Messages);
            System.out.println("Table Messages_" + strAddress + " créée.");
        }
        catch (SQLException e) {
            logger.log(Level.SEVERE,"IOException: " + e.getMessage());
            }
    }

    // se déconnecter de la base de données
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException("Error closing the database connection", e);
            }
        }
    }
}
