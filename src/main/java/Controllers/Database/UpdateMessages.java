package Controllers.Database;

import java.net.InetAddress;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.*;
import java.time.format.DateTimeFormatter;


import static Controllers.Database.CreateDatabase.connection;
import static Controllers.Database.CreateDatabase.url;

public class UpdateMessages {

    public static void addMessage(InetAddress Address, String Content) {

        try {
            CreateDatabase.Connect(url);
            Statement statement = connection.createStatement();
            String strAddress = Address.getHostAddress().replace('.', '_');
            String insertQuery = "INSERT INTO Messages_" + strAddress + "(content, dateHeure) VALUES (?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                // Set values for the parameters using the Users class methods
                // the preparedStatement.setString() method is used to set the values for the placeholders (?) in the SQL query
                LocalDateTime currentDateTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDateTime = currentDateTime.format(formatter);
                preparedStatement.setString(1, Content);
                preparedStatement.setString(2,formattedDateTime);

                preparedStatement.executeUpdate();
                System.out.println("User added successfully");

            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception
        }
    }
}
