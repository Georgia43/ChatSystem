package Controllers;

import java.io.*;
import java.net.*;

public class Client {

    /*lancer la connexion*/

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection(InetAddress ip) throws IOException {
        clientSocket = new Socket(ip, Broadcast.PORT);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    /*envoyer un message*/
    public String sendMessage(String msg) throws IOException {
        out.println(msg);
        return in.readLine();
    }

}
