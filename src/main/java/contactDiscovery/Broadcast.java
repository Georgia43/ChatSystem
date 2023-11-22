package contactDiscovery;

import Model.AppData;

import java.net.*;
import java.io.IOException;
import java.util.Enumeration;
import java.util.logging.Logger;
import java.util.Map;
import java.util.logging.Level;

public class Broadcast {

    public static final int PORT = 37842;

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

    //vérifier que je ne reçois pas mon propre broadcast
    public static boolean receivedFromMyself(InetAddress sender) {
        boolean SameAddress = false;
        try {
            // Get all network interfaces
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();

            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();

                // Get all InetAddress associated with the network interface
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();

                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();

                    // Print the IP address
                    if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress() && inetAddress.equals(sender)) {
                        SameAddress = true;
                    }
                }
            }
            return SameAddress;
        } catch (SocketException ex) {
            throw new RuntimeException(ex);
        }
    }
    /*ENVOI*/
    public static void sendFirstPacket() {
        try {
            DatagramSocket socket = new DatagramSocket();
            socket.setBroadcast(true);
            String mess = "FIRST_MESSAGE";
            byte [] FirstMessage = mess.getBytes();
            DatagramPacket message= new DatagramPacket(FirstMessage, FirstMessage.length, getBroadcastAddress(), PORT);
            socket.send(message);
        }
        catch (IOException e) {
            logger.log(Level.SEVERE,"IOException: " + e.getMessage());
        }
    }

    /*RECEPTION*/
    public static class Receive extends Thread {
       // Map<String, String> contactList = new HashMap<>(); ON A MAINTENANT CREE UNE CLASSE CONTACTLIST
	public Receive() {
        //constructeur
    	}

        @Override
        public void run() {
            try {
                DatagramSocket socket = new DatagramSocket(PORT); //4445 est le numéro de port qui va recevoir le message
                boolean running = true;

                socket.setBroadcast(true);
                byte[] buf = new byte[256];

                while (running) {
                    DatagramPacket inPacket = new DatagramPacket(buf, buf.length);
                    socket.receive(inPacket);
                    String received = new String(inPacket.getData(), 0, inPacket.getLength());
                    InetAddress sender = InetAddress.getByName(inPacket.getAddress().getHostAddress());
                  //  logger.log(Level.SEVERE, "Received on port " + PORT + " '" + received + "' from " + sender);
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
            if (received.equals("FIRST_MESSAGE") && (!receivedFromMyself(sender))) {
                System.out.println("receive first message");
                sendNickname(AppData.getNicknameCurrentUser(), sender); //j'envoie mon nickname à la personne qui souhaite se connecter
              //  logger.log(Level.SEVERE, " just sent :" + AppData.getNicknameCurrentUser());
            } else if (received.startsWith("MY_NICKNAME_")) {
                System.out.println("receive msg "+received);
                String prefix = "MY_NICKNAME_";
                String nickname = received.substring(prefix.length());
                System.out.println("i just received "+nickname);
                AppData.addContactList(sender, nickname);
                System.out.println("new user "+nickname);
            } else if (received.equals("DISCONNECTING")) {
                AppData.DeletefromContactList(sender);
            }
        }
    }
        /*pour envoyer le nickname*/
        public static void sendNickname (String nickname, InetAddress address) {
	        try {
        		DatagramSocket respSocket = new DatagramSocket();
        		//respSocket.setBroadcast(true);
                String mess = "MY_NICKNAME_"+nickname;
                byte [] respMessage= mess.getBytes();
                DatagramPacket respPacket = new DatagramPacket(respMessage, respMessage.length, address, PORT);
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
                DatagramPacket exitPacket = new DatagramPacket(exitMessage, exitMessage.length, id, PORT);
                exitSocket.send(exitPacket);
            }
            exitSocket.close();
        }

        catch (IOException e) {
            logger.log(Level.SEVERE,"IOException: " + e.getMessage());
        }
    }
}



