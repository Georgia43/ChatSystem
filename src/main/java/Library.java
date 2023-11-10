import java.util.*;

public class Library {

    public static class User {

        /*attributs*/
        private String nickname;
        private String id;

        /*méthodes*/
        public String getNickname() {return this.nickname;}
        public String getId() {return this.id;}

        //envoyer premier message
        public void sendMessage() {
            String name = this.nickname;
            Broadcast.sendFirstPacket(name);
        }

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
    private final Broadcast.Receive receiveInstance;

    /*constructeur*/
     public Library(Broadcast.Receive receiveInstance) {
        this.receiveInstance = receiveInstance;
    }


    /*méthodes*/

    //récupérer liste avec les utilisateurs connectés
    public ArrayList<User> GetConnectedUserList() {
        ArrayList<User> connectedUsers = new ArrayList<>();

        // pour accéder à la liste des contacts connectés
        if (receiveInstance != null) {
            for (Map.Entry<String, String> pers : receiveInstance.contactList.entrySet()) {
            String id = pers.getKey();
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
