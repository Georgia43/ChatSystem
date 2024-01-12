package Controllers.Database;

import Model.AppData;

import java.net.InetAddress;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Objects;


public class UpdateMessages {


    public static boolean addReceivedMessage(InetAddress Address, String Content, String url) throws SQLException {
        return addMessage(Address, Content, url, true);
    }
    public static boolean addSentMessage(InetAddress Address, String Content, String url) throws SQLException {
        return addMessage(Address, Content, url, false);
    }

    private static boolean addMessage(InetAddress Address, String Content, String url, boolean received) throws SQLException {

        try {
           CreateTables database = new CreateTables(url);
           // Statement statement = database.connection.createStatement();
            String strAddress = Address.getHostAddress().replace('.', '_');
            String insertQuery = "INSERT INTO Messages_" + strAddress + "(dateHeure, sender, content) VALUES (?, ?, ?)";
            String MyAddress = Objects.requireNonNull(AppData.getNonLoopbackAddress()).getHostAddress().replace('.', '_');

            try (PreparedStatement preparedStatement = database.connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
                // Set values for the parameters using the Users class methods
                // the preparedStatement.setString() method is used to set the values for the placeholders (?) in the SQL query
                //to add the date and time of the message
                LocalDateTime currentDateTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDateTime = currentDateTime.format(formatter);
                preparedStatement.setString(1,formattedDateTime);
                preparedStatement.setString(3, Content);
                if (received) {
                preparedStatement.setString(2, strAddress);}
                else {preparedStatement.setString(2, MyAddress);}


                preparedStatement.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception
            return false;
        }
    }
}
