package Controllers;

import java.io.*;
import java.net.*;

public class Server {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void start() throws IOException {
        /*Création du serveur*/
        serverSocket = new ServerSocket(Broadcast.PORT);
        /*Attente d'une connexion client*/
        clientSocket = serverSocket.accept();
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        InetAddress clientIP = clientSocket.getInetAddress();
        /*lire message*/
        String message = in.readLine();
        /*handle*/
        HandleMessage.handle(clientIP,message);

    }
    public void stop() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }
}
