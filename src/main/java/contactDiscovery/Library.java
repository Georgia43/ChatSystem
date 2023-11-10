package contactDiscovery;

import contactDiscovery.Broadcast;

import java.util.*;

public class Library {

    public static class User {

        /*attributs*/
        private String nickname;
        private String id;

        /*méthodes*/
        public String getNickname() {return this.nickname;}
        public String getId() {return this.id;}


        //set pseudo
        public void setNickname(String name) {
           this.nickname= name;
        }
        //set id
        public void setId(String id) {
           this.id= id; 
        }

    }
    /*attributs*/

    // pour accéder à la liste des contacts connectés
    private static Broadcast.Receive receiveInstance;

    /*constructeur*/
     public Library(Broadcast.Receive receiveInstance) {
        Library.receiveInstance = receiveInstance;
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
        if (receiveInstance.contactList != null) {
            for (Map.Entry<String, String> pers : receiveInstance.contactList.entrySet()) {
            //String id = pers.getKey();
           // String nickname = pers.getValue();

           // User user = new User();
           // user.setNickname(nickname);
           // user.setId(id);

           // connectedUsers.add(user);
                connectedUsers.put(pers.getKey(),pers.getValue());
            }
        }

        return connectedUsers;
    }

  
}
