package Controllers;

import Model.ClientsList;
import Model.HandleMessage;
import Model.User;

import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import View.Conversation;

public class Server {

    private static ServerSocket serverSocket;
    private static String mess;
    private static volatile boolean isRunning = true;

    public static final int MESSAGE_PORT = 37555;

    private List<Observer> observers = new ArrayList<>();

    interface  Observer {

    }


    public void start() {
        new Thread (()-> {
            /*Création du serveur*/
            try {
                serverSocket = new ServerSocket(MESSAGE_PORT);

                /*Attente d'une connexion client*/
                while (isRunning) {
                    Socket socketAccepted = serverSocket.accept();
                    ClientHandler clientHandler = new ClientHandler(socketAccepted);
                    ClientsList.clients.add(clientHandler);
                    clientHandler.start();

                    //User.recordConnectionSocket(socketAccepted);
                    System.out.println("!!!!!!!!!!!!!!!!!! i will enter add connection");
                    //User.addConnection(serverSocket);
                    System.out.println("!!!!!!!!!!!!!!!!!! i am waiting");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

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
    public static void stop() {
        isRunning=false;
        try{
            serverSocket.close();
            for (ClientHandler client : ClientsList.clients) {
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
                System.out.println("i am in client handler");
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
                    UserInteraction.messageReceived = HandleMessage.handle(clientSocket.getInetAddress(), message);
                    UserInteraction.sender = clientSocket.getInetAddress();


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
