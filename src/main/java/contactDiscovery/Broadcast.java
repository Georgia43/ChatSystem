package contactDiscovery;

import Model.AppData;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.net.InterfaceAddress;
import java.util.logging.Logger;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;

public class Broadcast {

    private static final Logger logger = Logger.getLogger("chatsystem");

    public static InetAddress getBroadcastAddress() {
        try {
            Enumeration<NetworkInterface> interfaces =
                    NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();
                if (networkInterface.isLoopback())
                    continue;
                for (InterfaceAddress interfaceAddress :
                        networkInterface.getInterfaceAddresses()) {
                    InetAddress broadcast = interfaceAddress.getBroadcast();
                    if (broadcast == null)
                        continue;
                    return broadcast;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
    /*ENVOI*/
    public static void sendFirstPacket() {
        try {
            DatagramSocket socket = new DatagramSocket();
            socket.setBroadcast(true);
            String mess = "FIRST_MESSAGE";
            byte [] FirstMessage = mess.getBytes();
            DatagramPacket message= new DatagramPacket(FirstMessage, FirstMessage.length, getBroadcastAddress(), 4445);
            socket.send(message);
        }
        catch (IOException e) {
            logger.log(Level.SEVERE,"IOException: " + e.getMessage());
        }
    }

    /*RECEPTION*/
    public static class Receive extends Thread {
       // Map<String, String> contactList = new HashMap<>(); ON A MAINTENANT CREE UNE CLASSE CONTACTLIST
        private final int port;
	public Receive(int port ) {
        //constructeur
        this.port=port;
    	}

        @Override
        public void run() {
            try {
                DatagramSocket socket = new DatagramSocket(port); //4445 est le numéro de port qui va recevoir le message
                boolean running = true;

                socket.setBroadcast(true);
                byte[] buf = new byte[256];

                while (running) {
                    DatagramPacket inPacket = new DatagramPacket(buf, buf.length);
                    socket.receive(inPacket);
                    String received = new String(inPacket.getData(), 0, inPacket.getLength());
                    InetAddress sender = InetAddress.getByName(inPacket.getAddress().getHostAddress());
		            //String receiver = InetAddress.getLocalHost().getHostAddress();
		            handleReceived(sender,received);

                    if (received.equals("end")) {
                        running = false;
                        continue;
                    }
                }
                socket.close();
                //Thread.sleep(2000);
            } catch (IOException e) {
                logger.log(Level.SEVERE,"IOException: " + e.getMessage());
            } //catch (InterruptedException e) {
               // throw new RuntimeException(e);
           // }
        }
        static void handleReceived(InetAddress sender, String received) {
            if (received.equals("FIRST_MESSAGE")) {
                sendNickname(AppData.getNicknameCurrentUser(), sender); //j'envoie mon nickname à la personne qui souhaite se connecter
            } else if (received.startsWith("MY_NICKNAME_")) {
                String prefix = "MY_NICKNAME_";
                String nickname = received.substring(prefix.length());
                AppData.addContactList(sender, nickname);
            } else if (received.equals("DISCONNECTING")) {
                AppData.DeletefromContactList(sender);
            }
        }
    }
        /*pour envoyer le nickname*/
        public static void sendNickname (String nickname, InetAddress address) {
	        try {
        		DatagramSocket respSocket = new DatagramSocket();
        		respSocket.setBroadcast(true);
                    	String mess = "MY_NICKNAME_"+nickname;
                   	byte [] respMessage= mess.getBytes();
                DatagramPacket respPacket = new DatagramPacket(respMessage, respMessage.length, address, 4445);
        		respSocket.send(respPacket);
        		respSocket.close(); 
            }
        	catch (IOException e) {
                logger.log(Level.SEVERE,"IOException: " + e.getMessage());
            }


	}

    //envoyer un message à sa liste de contacts lorsqu'on se déconnecte
    public static void sendExitMessage(){
        Map<InetAddress, String> contactList = AppData.getContactList();

        try {
            DatagramSocket exitSocket = new DatagramSocket();
            exitSocket.setBroadcast(true);

            for (Map.Entry<InetAddress, String> pers : AppData.getContactList().entrySet()) {
                InetAddress id = pers.getKey(); //récupérer l'adresse IP des personnes dans la liste de contacts

                String exit = "DISCONNECTING";
                byte[] exitMessage = exit.getBytes();
                DatagramPacket exitPacket = new DatagramPacket(exitMessage, exitMessage.length, id, 4445);
                exitSocket.send(exitPacket);
            }
            exitSocket.close();
        }

        catch (IOException e) {
            logger.log(Level.SEVERE,"IOException: " + e.getMessage());
        }
    }

}



