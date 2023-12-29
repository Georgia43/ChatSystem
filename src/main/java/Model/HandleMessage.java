package Model;

import Controllers.Database.CreateDatabase;
import Controllers.Database.UpdateMessages;

import java.net.*;
import java.sql.SQLException;

public class HandleMessage {

    public static void handle(InetAddress sender, String mess_received) throws SQLException {
        if ( mess_received.startsWith("MESSAGE_")){
            String prefix = "MESSAGE_";
            String message = mess_received.substring(prefix.length());
            //Base de Données
          //  CreateDatabase database = new CreateDatabase(CreateDatabase.MESSAGE_DATABSE);
           // UpdateMessages.addMessage(sender,message,CreateDatabase.MESSAGE_DATABSE);
            System.out.println(message);
        }

    }
}
