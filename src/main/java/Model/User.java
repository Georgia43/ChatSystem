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

        private static Client client = new Client();

        private static Socket clientSocket; // pour maintenir la connexion


        /*méthodes*/
        public String getNickname() {return this.nickname;}
        public InetAddress getId() {return this.id;}

    public static void recordConnectionSocket(Socket socket) {
            // record a socket that canbe created
          //  - when we establish ourselves
          //  - when the other user connects to our global server socket
        System.out.println("i am in recordConnectionSocket");
        clientSocket = socket;
        client.setSocket(clientSocket);
        Server.ClientHandler clientHandler = new Server.ClientHandler(clientSocket);
        Server.clients.add(clientHandler);
        clientHandler.start();
    }

      public static void addConnection(ServerSocket socket) {
                try{
                  // ServerSocket socket = new ServerSocket(Server.MESSAGE_PORT);
                    System.out.println("!!!!!!!!!!!!!!!!!! i am in add connection");
                    Socket socketAccepted = socket.accept();
                    recordConnectionSocket(socketAccepted);

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
