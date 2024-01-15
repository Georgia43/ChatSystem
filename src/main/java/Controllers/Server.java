package Controllers;

import Model.ClientsList;
import Model.HandleMessage;

import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private static ServerSocket serverSocket;
    private static String mess;
    private static volatile boolean isRunning = true;

    public static final int MESSAGE_PORT = 37555;



    public void start() {
        new Thread (()-> {
            /*Création du serveur*/
            try {
                serverSocket = new ServerSocket(MESSAGE_PORT);

                /*Attente d'une connexion client*/
                while (isRunning) {
                    System.out.println("[TCP-Server] Waiting for incoming TCP connections");
                    Socket socketAccepted = serverSocket.accept();
                    InetAddress clientAddress = socketAccepted.getInetAddress();
                    ClientHandler clientHandler = new ClientHandler(socketAccepted, clientAddress);
                    ClientsList.addNewClient(clientHandler);
                    clientHandler.start();

                    //User.recordConnectionSocket(socketAccepted);
                   // System.out.println("!!!!!!!!!!!!!!!!!! i will enter add connection");
                    //User.addConnection(serverSocket);

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

        InetAddress ipSender;

        interface  Observer {
            void handleMess(String message) throws UnknownHostException;

        }
        private List<Observer> observers = new ArrayList<>();

        public void addObserver(Observer obs){
            this.observers.add(obs);
        }

        public ClientHandler(Socket socket, InetAddress ipaddress) {
            this.clientSocket = socket;
            this.ipSender = ipaddress;
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            } catch (IOException e) {
                e.printStackTrace();
                ;
            }}
        public InetAddress getIpSender(){
            return this.ipSender;
        }

            public void sendMessage (String message) throws IOException {
                //try {
                //DatagramSocket respSocket = new DatagramSocket();
                String mess = "MESSAGE_" + message;
                //byte [] respMessage= mess.getBytes();
                if (out != null) {
                    out.println(mess);
                } else {
                    System.err.println("Connection not established. Cannot send message.");
                }
                //DatagramPacket respPacket = new DatagramPacket(respMessage, respMessage.length, address, Server.MESSAGE_PORT);
                // respSocket.send(respPacket);
                //respSocket.close();
                //BDD STOCKER MESSAGE
                //}
                //catch (IOException e) {
                //  logger.log(Level.SEVERE,"IOException: " + e.getMessage());
                // }

            }


            @Override
            public void run () {
            System.out.println("[ClientHandler] Waiting for messages -- " + this.clientSocket.getInetAddress());
            String message;
                try {
                    while ((message = in.readLine()) != null) {
                        UserInteraction.messageReceived = HandleMessage.handle(clientSocket.getInetAddress(), message);
                        UserInteraction.sender = clientSocket.getInetAddress();
                        System.out.println("[ClientHandler] message received: " + UserInteraction.messageReceived +" -- " + this.clientSocket.getInetAddress());

                        for (Observer obs : this.observers){
                            obs.handleMess(UserInteraction.messageReceived);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } finally {
                    System.out.println("[ClientHandler] Exiting -- " + this.clientSocket.getInetAddress());
                    close();
                }
            }
            public void close () {
                try {
                    in.close();
                    out.close();
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
}
