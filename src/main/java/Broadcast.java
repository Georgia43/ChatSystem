import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.net.InterfaceAddress;
import static jdk.internal.net.http.common.Log.logError;
import java.util.Map;
import java.util.HashMap;

public class Broadcast {

    public final InetAddress getBroadcastAddress() {
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
    public final void sendFirstPacket(String nickname) {
        try {
            DatagramSocket socket = new DatagramSocket();
            socket.setBroadcast(true);
            String mess = nickname;
            byte [] FirstMessage = mess.getBytes();
            DatagramPacket message= new DatagramPacket(FirstMessage, FirstMessage.length, getBroadcastAddress(), 4445);
            socket.send(message);
        }
        catch (IOException e) {
            logError("IOException: " + e.getMessage());
        }
    }

    /*RECEPTION*/
    public static class Receive extends Thread {
        private final Map<String, String> contactList = new HashMap<>();

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
                    if (!contactList.containsValue(received)) {
                        contactList.put(sender, received);
                    }

                    

                    if (received.equals("end")) {
                        running = false;
                        continue;
                    }

                }
                socket.close();
            } catch (IOException e) {
                logError("IOException: "+e.getMessage());
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
    		     logError("IOException: " + e.getMessage());
            }


}

}



}
