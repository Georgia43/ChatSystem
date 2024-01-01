package Model;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.net.*;
import java.util.Enumeration;
import java.util.Scanner;

public class AppData {
    private static Map<InetAddress, String> contactList = new HashMap<>();
    public static User currentUser = new User();

    private AppData(){
        //constructeur

    }

    /*POUR LISTE DE CONTACTES*/

    public static Map<InetAddress,String> getContactList(){
        return contactList;
    }

    public static void addContactList(InetAddress sender, String received) {

        if (!contactList.containsValue(received)) {
            contactList.put(sender, received);
            System.out.println(received + " has been added to the contact list.");
        }
    }

    public static void DeletefromContactList(InetAddress sender){
        contactList.remove(sender);

    }

    public static String getNicknameCurrentUser() {return currentUser.getNickname();}

    /*POUR ÉCHANGE DE MESSAGES*/
    public static InetAddress getIpByNickname (String name) {
        InetAddress res = null;
        for (Map.Entry<InetAddress, String> pers : AppData.getContactList().entrySet()) {
            if (pers.getValue().equals(name)) {
                res = pers.getKey();
            }
        }
        return res;
    }

    public static InetAddress getNonLoopbackAddress() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();

            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();

                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();

                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();

                    // Check if it's a non-loopback, non-link-local address
                    if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress()) {
                        return inetAddress;
                    }
                }
            }
            return null;  // No non-loopback address found
        } catch (SocketException ex) {
            throw new RuntimeException(ex);
        }
    }
}






