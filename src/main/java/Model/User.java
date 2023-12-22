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

        private Client client = new Client();

        /*méthodes*/
        public String getNickname() {return this.nickname;}
        public InetAddress getId() {return this.id;}

      public void addConnection() {
                try{
                        ServerSocket serverSocket = new ServerSocket(Broadcast.PORT);
                        Socket clientSocket = serverSocket.accept();
                        client.setSocket(clientSocket);
                        Server.ClientHandler clientHandler = new Server.ClientHandler(clientSocket);
                        Server.clients.add(clientHandler);
                        clientHandler.start();
                        serverSocket.close();;


                } catch (IOException e) {
                        e.printStackTrace();
              }
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
