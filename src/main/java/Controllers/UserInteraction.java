package Controllers;

import Controllers.Database.CreateTables;
import Controllers.Database.UpdateMessages;
import Model.ClientsList;

import java.net.InetAddress;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.Objects;

import static Model.AppData.getNonLoopbackAddress;

public class UserInteraction {

    static String messageReceived;
    static InetAddress sender;

    public UserInteraction() throws UnknownHostException {
        try{
        this.sender = InetAddress.getLocalHost(); 
    } catch (UnknownHostException e) {
        e.printStackTrace();}
    }

  
    public void sendMess(String message,String ipAddress) throws IOException {
        try{
            InetAddress ip = InetAddress.getByName(ipAddress);
            Server.ClientHandler clientHandler = ClientsList.getClientHandlerByIP(ip); //récupérer le client handler qui correspond à l'adresse IP ipAddress
            assert clientHandler != null;
            clientHandler.sendMessage(message);
            UpdateMessages.addSentMessage(ip,message, CreateTables.createURL(Objects.requireNonNull(getNonLoopbackAddress()))); //ajouter le message envoyé dans la database
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
