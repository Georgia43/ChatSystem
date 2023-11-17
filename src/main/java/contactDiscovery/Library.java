package contactDiscovery;

import Model.AppData;
import Model.User;

import java.net.InetAddress;
import java.util.*;

public class Library {

    public Library() {
        //constructeur
    }


    /*méthodes*/
    //envoyer premier message
    public static void sendFirstMessage(){
        // String name = this.nickname;
        Broadcast.sendFirstPacket();
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
