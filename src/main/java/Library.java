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
            String message = "Hello from"+nickname ;
            sendFirstPacket(message.getBytes());
        }

    }


    /*méthodes*/

    //récupérer liste avec les utilisateurs connectés
    public ArrayList <User> GetConnectedUserList() {
        ArrayList <User> list = new ArrayList<User> ();
    }

}
