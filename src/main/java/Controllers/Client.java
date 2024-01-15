package Controllers;

import Model.ClientsList;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static Controllers.Broadcast.logger;

public class Client {

    /*lancer la connexion*/

    private Socket clientSocket;
    private static PrintWriter out;
    private static BufferedReader in;


    public Client(InetAddress ip) throws IOException {
        System.out.println("[Client] Creating client");
        clientSocket = new Socket(ip, Server.MESSAGE_PORT);
        Server.ClientHandler clientHandler = new Server.ClientHandler(clientSocket,ip);
        ClientsList.addNewClient(clientHandler);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        clientHandler.start();
    }

}
