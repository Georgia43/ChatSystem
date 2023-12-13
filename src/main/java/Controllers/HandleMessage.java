package Controllers;

import java.net.*;

public class HandleMessage {

    public static void handle(InetAddress sender, String mess_received) {
    if ( mess_received.startsWith("MESSAGE_")){
            String prefix = "MESSAGE_";
            String message = mess_received.substring(prefix.length());
            //Base de Données
        }

    }
}
