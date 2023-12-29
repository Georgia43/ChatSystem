package Controllers;

import Model.HandleMessage;
import Model.User;

import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private ServerSocket serverSocket;

    public static final int MESSAGE_PORT = 37555;
    public static List<ClientHandler> clients = new ArrayList<>();

    public void start() {
        /*Création du serveur*/
        try {
            serverSocket = new ServerSocket(MESSAGE_PORT);

        while (true) {
                /*Attente d'une connexion client*/
                User.addConnection(serverSocket);
            }
       }
        catch (IOException e){
        e.printStackTrace();
       }

        /*user = getUser(client.source)
                user.addConnection(clientSocket)*/
        /*out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        InetAddress clientIP = clientSocket.getInetAddress();*/
        /*lire message*/
       /* while(true) {
            String message = in.readLine();

            HandleMessage.handle(clientIP,message);
        }*/


    }
    public void stop() {
        try{
            serverSocket.close();
            for (ClientHandler client : clients) {
                client.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    public static class ClientHandler extends Thread {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;

        public ClientHandler(Socket socket){
            this.clientSocket = socket;
            try {
                out = new PrintWriter(clientSocket.getOutputStream(),true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            } catch (IOException e){
                e.printStackTrace();;
            }
        }

        @Override
        public void run() {
            String message;
            try {
                while ((message = in.readLine())!=null) {
                    HandleMessage.handle(clientSocket.getInetAddress(),message);

                }
            } catch (IOException e){
                e.printStackTrace();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                close();
            }
        }
        public void close(){
            try{
                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
