package Model;

import java.net.InetAddress;
import java.net.Socket;

public class User {

        /*attributs*/
        private String nickname;
        private InetAddress id;

        /*méthodes*/
        // récupérer le pseudo
        public String getNickname() {return this.nickname;}
        // récupérer l'id (adresse IP)
        public InetAddress getId() {return this.id;}

        public void setNickname(String name) {
            this.nickname= name;
        }

        public void setId(InetAddress id) {
            this.id= id;
        }

        @Override
        public String toString() {
                return "User{"
                        + "username='" + nickname + '\''
                        + ", id='" + id + '\''
                        + '}';
        }
}
