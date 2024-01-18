package Controllers.Database;

import Model.AppData;

import java.net.InetAddress;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UpdateMessages {

    // les deux fonctions sont créées pour plus de clareté pendant leur utilisation
    // on fixe le booléen a true pour les messages reçus
    public static boolean addReceivedMessage(InetAddress Address, String Content, String url) throws SQLException {
        return addMessage(Address, Content, url, true);
    }
    // on fixe le booléen a false pour les messages envoyés
    public static boolean addSentMessage(InetAddress Address, String Content, String url) throws SQLException {
        return addMessage(Address, Content, url, false);
    }

    // pour ajouter un message a la base de données
    // on met en paramètre un booléen pour savoir si le message a été recu ou envoyé
    // permet de savoir quelle adresse IP on met dans la table (celle de l'utilisateur ou du correspondant
    private static boolean addMessage(InetAddress Address, String Content, String url, boolean received) {

        try {
           CreateTables database = new CreateTables(url);
            String strAddress = Address.getHostAddress().replace('.', '_'); // il faut remplacer les . par des _ dans les tables pour éviter les erreurs
            String insertQuery = "INSERT INTO Messages_" + strAddress + "(dateHeure, sender, content) VALUES (?, ?, ?)";
            String MyAddress = Objects.requireNonNull(AppData.getNonLoopbackAddress()).getHostAddress().replace('.', '_');

            try (PreparedStatement preparedStatement = database.connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
                LocalDateTime currentDateTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // avoir le bon format pour la date et l'heure
                String formattedDateTime = currentDateTime.format(formatter);
                preparedStatement.setString(1,formattedDateTime);
                preparedStatement.setString(3, Content);
                if (received) { // si vrai, le message est recu: on met l'adresse de la personne avec laquelle on parle
                preparedStatement.setString(2, strAddress);}
                else {preparedStatement.setString(2, MyAddress);} // si faux, message envoyé donc on met notre adresse


                preparedStatement.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<String> loadMessagesFromDatabase(InetAddress address, String url) {
        List<String> messages = new ArrayList<>();

        try {
            CreateTables database = new CreateTables(url);
            Statement statement = database.connection.createStatement();
            String strAddress = address.getHostAddress().replace('.', '_');
            String query = "SELECT dateHeure, sender, content FROM Messages_" + strAddress;
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String dateHeure = resultSet.getString("dateHeure");
                String sender = resultSet.getString("sender");
                String formatedSender = sender.replace('_', '.');
                String content = resultSet.getString("content");

                // Construire le message et l'ajouter à la liste
                String message = dateHeure + " " + formatedSender + " " + content;
                messages.add(message);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }
}
