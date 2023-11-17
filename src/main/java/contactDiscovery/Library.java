package contactDiscovery;

import Model.AppData;
import Model.User;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Library {

    private static final Logger logger = Logger.getLogger("chatsystem");

    public Library() {
        //constructeur
    }


    /*méthodes*/
    //envoyer premier message
    public static void sendFirstMessage(){
        // String name = this.nickname;
        Broadcast.sendFirstPacket();
    }

    //send nickname when chosen and unique
    public static void SendCurrentNickname(String CurrentNickname){
        Map<InetAddress, String> contactList = AppData.getContactList();

        try {
            DatagramSocket NameSocket = new DatagramSocket();
            NameSocket.setBroadcast(true);

            for (Map.Entry<InetAddress, String> pers : AppData.getContactList().entrySet()) {
                InetAddress id = pers.getKey(); //get the ip address of the members of the contact list

                String Nickname = "MY_NICKNAME_" + CurrentNickname;
                byte[] NicknameMessage = Nickname.getBytes();
                DatagramPacket NamePacket = new DatagramPacket(NicknameMessage, NicknameMessage.length, id, 4445);
                NameSocket.send(NamePacket);
            }
            NameSocket.close();
        }

        catch (IOException e) {
                logger.log(Level.SEVERE,"IOException: " + e.getMessage());
        }
    }



    //récupérer liste avec les utilisateurs connectés
    public static ArrayList<Model.User> GetConnectedUserList() {
        ArrayList<Model.User> connectedUsers = new ArrayList<User>();

        // pour accéder à la liste des contacts connectés
        if (AppData.getContactList() != null) {
            for (Map.Entry<InetAddress, String> pers : AppData.getContactList().entrySet()) {
                InetAddress id = pers.getKey();
                String nickname = pers.getValue();
                User user = new User();
                user.setNickname(nickname);
                user.setId(id);
                connectedUsers.add(user);

            }
        }

        return connectedUsers;
    }

  
}
