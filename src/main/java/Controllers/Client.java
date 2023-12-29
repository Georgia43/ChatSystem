package Controllers;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static Controllers.Broadcast.logger;

public class Client {

    /*lancer la connexion*/

    private static Socket clientSocket;
    private static PrintWriter out;
    private static BufferedReader in;

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


    public static void startConnection(InetAddress ip) throws IOException {
        clientSocket = new Socket(ip, Server.MESSAGE_PORT);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        //Model.User.recordConnectionSocket();
    }

    /*envoyer un message*/
    public void sendMessage(String message) throws IOException {
        //try {
            //DatagramSocket respSocket = new DatagramSocket();
            String mess = "MESSAGE_"+message;
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

}
