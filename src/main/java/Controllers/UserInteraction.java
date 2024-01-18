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
            // initialisation de l'expéditeur avec l'adresse IP locale de l'utilisateur
            this.sender = InetAddress.getLocalHost();
        }
        catch (UnknownHostException e) {
        e.printStackTrace();}
    }

    // pour envoyer le message au bon client
    public void sendMess(String message,String ipAddress) throws IOException {
        try{
            InetAddress ip = InetAddress.getByName(ipAddress);
            // récupérer le client handler qui correspond à l'adresse IP ipAddress
            Server.ClientHandler clientHandler = ClientsList.getClientHandlerByIP(ip);
            assert clientHandler != null;
            clientHandler.sendMessage(message);
            // ajouter le message envoyé dans la base de données
            UpdateMessages.addSentMessage(ip,message, CreateTables.createURL(Objects.requireNonNull(getNonLoopbackAddress()))); //ajouter le message envoyé dans la database
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
