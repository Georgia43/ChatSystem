package Controllers;

import Controllers.Client;
import Controllers.Database.CreateDatabase;
import Controllers.Database.UpdateMessages;
import Model.AppData;
import Model.User;

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
    static String messageReceived;
    static InetAddress sender;

    public UserInteraction() throws UnknownHostException {
        try{
        this.client=new Client();
        this.sender = InetAddress.getLocalHost(); // Initialize the sender variable here
    } catch (UnknownHostException e) {
        e.printStackTrace();}
    }

   /* public void changeUser(String ipAddress){
        try{
            InetAddress ip = InetAddress.getByName(ipAddress);
            client.startConnection(ip);
        } catch (IOException e){
            e.printStackTrace();
        }
    }*/

  /*  public static void sendTCPconnection() throws IOException {

        try {
            System.out.println("rentre dans send tcp");
            for (Map.Entry<InetAddress, String> pers : AppData.getContactList().entrySet()) {
                System.out.println("je rentre dans le for");
                InetAddress id = pers.getKey(); //récupérer l'adresse IP des membres de la liste de contact
                Client.startConnection(id);
                System.out.println("connection TCP lancée avec "+ pers);
            }
        }

        catch (IOException e) {
            logger.log(Level.SEVERE,"IOException: " + e.getMessage());
        }
    }*/

    public void sendMess(String message,String ipAddress) throws IOException {
        try{
            InetAddress ip = InetAddress.getByName(ipAddress);
            client.sendMessage(message);
          UpdateMessages.addMessage(ip,message,CreateDatabase.MESSAGE_DATABSE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getMessageReceived(){
        return messageReceived;
    }

    public String getSender() {
       if(sender != null) {return sender.getHostAddress();}
       else {return "this shouldn't happen";}
    }
}
