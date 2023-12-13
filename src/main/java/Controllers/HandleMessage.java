package Controllers;

import Model.AppData;

import java.net.*;
import java.io.IOException;
import java.util.Enumeration;
import java.util.logging.Logger;
import java.util.Map;
import java.util.logging.Level;

public class handleMessage {

    public static void handle(InetAddress sender, String mess_received) {
    if ( mess_received.startsWith("MESSAGE_")){
            String prefix = "MESSAGE_";
            String message = mess_received.substring(prefix.length());
            //Base de Données
        }

    }
}
