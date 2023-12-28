package Controllers;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static Controllers.Broadcast.logger;

public class Client {

    /*lancer la connexion*/

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void setSocket(Socket socket){
        this.clientSocket = socket;
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }


    public void startConnection(InetAddress ip) throws IOException {
        clientSocket = new Socket(ip, Broadcast.PORT);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        //Model.User.recordConnectionSocket();
    }

    /*envoyer un message*/
    public void sendMessage(String message, InetAddress address) throws IOException {
        try {
            DatagramSocket respSocket = new DatagramSocket();
            String mess = "MESSAGE_"+message;
            byte [] respMessage= mess.getBytes();
            DatagramPacket respPacket = new DatagramPacket(respMessage, respMessage.length, address, Broadcast.PORT);
            respSocket.send(respPacket);
            respSocket.close();
            //BDD STOCKER MESSAGE
        }
        catch (IOException e) {
            logger.log(Level.SEVERE,"IOException: " + e.getMessage());
        }


    }

}
