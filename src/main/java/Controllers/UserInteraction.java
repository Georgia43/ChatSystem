package Controllers;

import Controllers.Client;
import Controllers.Database.CreateDatabase;
import Controllers.Database.UpdateMessages;
import Model.AppData;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;

import static Controllers.Broadcast.logger;

public class UserInteraction {

    private Client client ;

    public UserInteraction() {
        this.client=new Client();
    }

   /* public void changeUser(String ipAddress){
        try{
            InetAddress ip = InetAddress.getByName(ipAddress);
            client.startConnection(ip);
        } catch (IOException e){
            e.printStackTrace();
        }
    }*/

    public static void sendTCPconnection() throws IOException {

        try {
            for (Map.Entry<InetAddress, String> pers : AppData.getContactList().entrySet()) {
                InetAddress id = pers.getKey(); //récupérer l'adresse IP des membres de la liste de contact
                Client.startConnection(id);
                System.out.println("connection lancée avec "+ pers);
            }
        }

        catch (IOException e) {
            logger.log(Level.SEVERE,"IOException: " + e.getMessage());
        }
    }

    public void sendMess(String message,String ipAddress) throws IOException {
        try{
            InetAddress ip = InetAddress.getByName(ipAddress);
            client.sendMessage(message,ip);
            CreateDatabase database = new CreateDatabase(CreateDatabase.MESSAGE_DATABSE);
            UpdateMessages.addMessage(ip,message,CreateDatabase.MESSAGE_DATABSE);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
