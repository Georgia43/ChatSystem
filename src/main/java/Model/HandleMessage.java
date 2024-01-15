package Model;

import Controllers.Database.CreateTables;
import Controllers.Database.UpdateMessages;

import java.net.*;
import java.sql.SQLException;
import java.util.Objects;

import static Model.AppData.getNonLoopbackAddress;

public class HandleMessage {

    public static String handle(InetAddress sender, String mess_received) throws SQLException {
        if ( mess_received.startsWith("MESSAGE_")){
            String prefix = "MESSAGE_";
            String message = mess_received.substring(prefix.length());
            //Base de Données
            UpdateMessages.addReceivedMessage(sender, message, CreateTables.createURL(Objects.requireNonNull(getNonLoopbackAddress())));
            //affichage

            System.out.println(message);
            return message;
        } else {
            throw new RuntimeException("invalid message received");
        }

    }
}
