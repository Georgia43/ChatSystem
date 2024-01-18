package Controllers;

import Model.ClientsList;
import Model.ProcessMessage;

import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private static ServerSocket serverSocket;
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
                    // on créé un client handler et on l'ajoute à la liste
                    ClientHandler clientHandler = new ClientHandler(socketAccepted, clientAddress);
                    ClientsList.addNewClient(clientHandler);
                    clientHandler.start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }


    // pour gérer les clients: ils ont tous leur propre socket
    public static class ClientHandler extends Thread {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;

        InetAddress ipSender;

        // on créé l'observeur qui sera utilisé pour afficher les messages sur les frames correspondantes
        interface  Observer {
            // méthode à implémenter dans les classes qui observent (ici la classe StartEverything)
            void handleMess(String message) throws UnknownHostException;
        }
        private List<Observer> observers = new ArrayList<>(); // Liste des observateurs

        // pour ajouter un observateur à la liste
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

        // pour récupérer l'adresse ip du sender
        public InetAddress getIpSender(){
            return this.ipSender;
        }

        // pour envoyer un message au client
            public void sendMessage (String message) throws IOException {
                String mess = "MESSAGE_" + message; // on ajoute une entête aux messages pour filtrer la réception
                if (out != null) {
                    out.println(mess);
                } else {
                    System.err.println("Connection not established. Cannot send message.");
                }
            }

            // lors du lancement du thread
            @Override
            public void run () {
            System.out.println("[ClientHandler] Waiting for messages -- " + this.clientSocket.getInetAddress());
            String message;
                try {
                    // boucle pour recevoir les messages du client
                    while ((message = in.readLine()) != null) {
                        // gestion du message et on récupère la personne qui l'a envoyé
                        UserInteraction.messageReceived = ProcessMessage.process(clientSocket.getInetAddress(), message);
                        UserInteraction.sender = clientSocket.getInetAddress();
                        System.out.println("[ClientHandler] message received: " + UserInteraction.messageReceived +" -- " + this.clientSocket.getInetAddress());

                        // notification des observers
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

            // pour fermer la connexion
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
