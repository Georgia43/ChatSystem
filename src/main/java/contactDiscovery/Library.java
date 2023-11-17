package contactDiscovery;

import Model.User;
import contactDiscovery.Broadcast;

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
    public static void sendMessage(String name){
        // String name = this.nickname;
        Broadcast.sendFirstPacket(name);
    }

    //récupérer liste avec les utilisateurs connectés
    public static ArrayList<Model.User> GetConnectedUserList() {
        ArrayList<Model.User> connectedUsers = new ArrayList<User>();

        // pour accéder à la liste des contacts connectés
        //if (receiveInstance!=null && receiveInstance.contactList != null) {
        if (Model.contactList.getContactList() != null) {
            for (Map.Entry<String, String> pers : Model.contactList.getContactList().entrySet()) {
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
