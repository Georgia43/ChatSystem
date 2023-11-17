package contactDiscovery;

import Model.AppData;
import Model.User;

import java.util.*;

public class Library {


    /*attributs*/

    // pour accéder à la liste des contacts connectés
    //private static Broadcast.Receive receiveInstance;

    /*constructeur*/
    /* public Library(Broadcast.Receive receiveInstance) {
        Library.receiveInstance = receiveInstance;
    }*/
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
        //if (receiveInstance!=null && receiveInstance.contactList != null) {
        if (AppData.getContactList() != null) {
            for (Map.Entry<String, String> pers : AppData.getContactList().entrySet()) {
                String id = pers.getKey();
                String nickname = pers.getValue();
                User user = new User();
                user.setNickname(nickname);
                user.setId(id);
                connectedUsers.add(user);
                // connectedUsers.putAll(Model.contactList.getContactList());
            }
        }

        return connectedUsers;
    }

  
}
