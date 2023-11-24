package Controllers;

import java.awt.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
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
        /*lire une ligne de texte à partir du flux d'entrée (BufferedReader)*/
        String message = in.readLine();
        /***************************PAS FINI***********************************************/
    }
}
