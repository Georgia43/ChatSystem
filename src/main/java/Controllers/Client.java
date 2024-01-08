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

    /*public void setSocket(Socket socket){
        this.clientSocket = socket;
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }*/


    public Client(InetAddress ip) throws IOException {
        System.out.println("!!!!!!!!!!!!!!!!!enter in startconnection");
        clientSocket = new Socket(ip, Server.MESSAGE_PORT);
        Server.ClientHandler clientHandler = new Server.ClientHandler(clientSocket);
        ClientsList.clients.add(clientHandler);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        System.out.println("!!!!!!!!!!!!!!!!!end of startconnection");
    }

    /*public void startConnection(InetAddress ip) throws IOException {

       // Model.User.recordConnectionSocket(clientSocket);
    }*/

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
