package contactDiscovery;

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
    public static Map<String, String> GetConnectedUserList() {
        Map<String, String> connectedUsers =  new HashMap<>();

        // pour accéder à la liste des contacts connectés
        //if (receiveInstance!=null && receiveInstance.contactList != null) {
        if (Model.contactList.getContactList() != null) {
            //String id = pers.getKey();
            // String nickname = pers.getValue();
            // User user = new User();
            // user.setNickname(nickname);
            // user.setId(id);
            // connectedUsers.add(user);
            connectedUsers.putAll(Model.contactList.getContactList());
        }

        return connectedUsers;
    }

  
}
