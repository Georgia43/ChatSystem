package Controllers;

import Model.HandleMessage;

import java.io.*;
import java.net.*;

public class Server {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void start() throws IOException {
        /*Création du serveur*/
        try {
            serverSocket = new ServerSocket(Broadcast.PORT);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                /*Attente d'une connexion client*/
                clientSocket = serverSocket.accept();
            }
            catch (IOException e){
                System.out.println("erreur : "+e);
            }
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
    public void stop() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }
}
