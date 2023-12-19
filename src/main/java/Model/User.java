package Model;

import Controllers.Client;
import Controllers.Server;

import java.net.InetAddress;

public class User {

        /*attributs*/
        private String nickname;
        private InetAddress id;

        private Client client = new Client();

        /*méthodes*/
        public String getNickname() {return this.nickname;}
        public InetAddress getId() {return this.id;}

        addConnection(Socket so) {
                createClient(so):
                launchListener(so);
        }

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
