package Controllers.Database;
import Controllers.Broadcast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;


public class CreateDatabase {

    public static String url = "jdbc:sqlite:BDDchatsystem";
    public static void tables(String username) throws SQLException {

        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement()){

            String Users = "CREATE TABLE IF NOT EXISTS Users ("
                    + "ipAdress INTEGER PRIMARY KEY, "
                    + "name VARCHAR(20) NOT NULL"
                    + "status INT NOT NULL)";
            statement.executeUpdate(Users);


            String Messages = "CREATE TABLE IF NOT EXISTS Messages_"+ username + "("
                    + "content VARCHAR(100) PRIMARY KEY,"
                    + "dateHeure DATETIME)";
            statement.executeUpdate(Users);

            System.out.println("Table 'Messages_' "+username+" créée.");
        }
        catch (SQLException e) {
            Broadcast.logger.log(Level.SEVERE,"IOException: " + e.getMessage());
        }
    }
}
