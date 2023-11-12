package contactDiscovery;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.net.InterfaceAddress;
import java.util.logging.Logger;
import java.util.Map;
import java.util.HashMap;
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
    public static void sendFirstPacket(String nickname) {
        try {
            DatagramSocket socket = new DatagramSocket();
            socket.setBroadcast(true);
            String mess = nickname;
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
        Map<String, String> contactList = new HashMap<>();
	 //   private User currentUser; //pour le changement de pseudo si nécessaire

	//constructeur    
	public Receive( Map<String, String> contactList) {
        	this.contactList = contactList;
    	}

        @Override
        public void run() {
            try {
                DatagramSocket socket = new DatagramSocket(4445); //4445 est le numéro de port qui va recevoir le message
                boolean running = true;

                socket.setBroadcast(true);
                byte[] buf = new byte[256];

                while (running) {
                    DatagramPacket inPacket = new DatagramPacket(buf, buf.length);
                    socket.receive(inPacket);
                    String received = new String(inPacket.getData(), 0, inPacket.getLength());
                    String sender = inPacket.getAddress().getHostAddress();
		    String receiver = InetAddress.getLocalHost().getHostAddress();
		    if (received.equals("CHANGE_NICKNAME")) {
                    String newNickname = promptUserForNewNickname();
                    // changer pseudo
                    //currentUser.setNickname(newNickname);
                   // sendFirstPacket(newNickname);
                } else {
                    if (!contactList.containsValue(received)) {
                        contactList.put(sender, received);
			            sendResponse("me",receiver);
                        System.out.println(contactList);
                    } else if (!contactList.containsKey(sender)) {
                        changeNickname(sender);
        		}

                    }

                    

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

	/*pour envoyer une demande du changement du pseudo*/
	public void changeNickname (String senderIp) {
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
	}

        /*pour envoyer le nickname après avoir ajouté la personne dans notre liste de contacts*/
        private void sendResponse (String nickname, String address) {
	        try {
        		DatagramSocket respSocket = new DatagramSocket();
        		respSocket.setBroadcast(true);
                    	String mess = nickname;
                   	byte [] respMessage= mess.getBytes();
        		InetAddress respAddress = InetAddress.getByName(address);
        		DatagramPacket respPacket = new DatagramPacket(respMessage, respMessage.length, respAddress, 4445); 
        		respSocket.send(respPacket);
        		respSocket.close(); 
            }
        	catch (IOException e) {
                logger.log(Level.SEVERE,"IOException: " + e.getMessage());
            }


	}
private String promptUserForNewNickname() {
        //pour que l'utilisateur puisse changer son nickname
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your new nickname: ");
        return scanner.nextLine();
    }

}



}
