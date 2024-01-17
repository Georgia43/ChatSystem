package Model;

import Controllers.Broadcast;
import Controllers.Client;
import Controllers.Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class User {

        /*attributs*/
        private String nickname;
        private InetAddress id;
        private Socket clientSocket; // pour maintenir la connexion


        /*méthodes*/
        public String getNickname() {return this.nickname;}
        
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
