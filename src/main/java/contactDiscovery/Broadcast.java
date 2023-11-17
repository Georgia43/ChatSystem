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
                    continue;    // Don't want to broadcast to the loopback interface
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
       // Map<String, String> contactList = new HashMap<>(); ON A MAINTENANT CREE UNE CLASS CONTACTLIST
	 //   private User currentUser; //pour le changement de pseudo si nécessaire


	public Receive( ) {
        //constructeur
    	}

        @Override
        public void run() {
            try {
                Map<InetAddress, String> contactList = AppData.getContactList();
                DatagramSocket socket = new DatagramSocket(4445); //4445 est le numéro de port qui va recevoir le message
                boolean running = true;

                socket.setBroadcast(true);
                byte[] buf = new byte[256];

                while (running) {
                    DatagramPacket inPacket = new DatagramPacket(buf, buf.length);
                    socket.receive(inPacket);
                    String received = new String(inPacket.getData(), 0, inPacket.getLength());
                    InetAddress sender = InetAddress.getByName(inPacket.getAddress().getHostAddress());
		            String receiver = InetAddress.getLocalHost().getHostAddress();
		            handleReceived(sender,received);

                    if (received.equals("end")) {
                        running = false;
                        continue;
                    }

                }
	
                socket.close();
            } catch (IOException e) {
                logger.log(Level.SEVERE,"IOException: " + e.getMessage());
            }
        }
        static void handleReceived(InetAddress sender, String received) {
        Map<InetAddress, String> contactList = AppData.getContactList();
       /*if (received.equals("CHANGE_NICKNAME")) {
            String newNickname = promptUserForNewNickname();
            // changer pseudo
            //currentUser.setNickname(newNickname);
            // sendFirstPacket(newNickname);
        } else {*/
        if (received.equals("FIRST_MESSAGE")) {
            sendNickname(AppData.getNicknameCurrentUser(), sender); //j'envoie mon nickname à la personne qui souhaite se connecter
        }
        else if (received.startsWith("MY_NICKNAME_")){
            String prefix ="MY_NICKNAME_";
            String nickname = received.substring(prefix.length());
                AppData.addContactList(sender,nickname);}
        }
    }

	/*pour envoyer une demande du changement du pseudo*/
	/*public void changeNickname (String senderIp) {
		try {
			DatagramSocket requestSocket = new DatagramSocket();
			requestSocket.setBroadcast(true);

			String nicknameRequest = "CHANGE_NICKNAME";
			byte[] requestBytes = nicknameRequest.getBytes();
			DatagramPacket nicknameRequestPacket = new DatagramPacket(requestBytes, requestBytes.length, InetAddress.getByName(senderIp), 4445);

			requestSocket.send(nicknameRequestPacket);
			requestSocket.close();	
		} catch (IOException e) {
            logger.log(Level.SEVERE,"IOException: " + e.getMessage());
			}
	}*/

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
       /* private static String promptUserForNewNickname() {
            //pour que l'utilisateur puisse changer son nickname
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter your new nickname: ");
            return scanner.nextLine();
        }*/

}



