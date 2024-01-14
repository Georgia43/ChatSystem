package Model;
import Controllers.Server;

import java.net.InetAddress;
import java.util.*;
import java.net.*;

public class AppData {
    private static Map<InetAddress, String> contactList = new HashMap<>();
    public static User currentUser = new User();

    private AppData(){
        //constructeur

    }

    /*POUR LISTE DE CONTACTS*/
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

    public static void changeContactList(InetAddress sender, String received) {
        for (Map.Entry<InetAddress, String> pers : AppData.getContactList().entrySet()) {
            if (pers.getKey().equals(sender)){
                pers.setValue(received);
            }
        }
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

    public static String getNicknameByIp(InetAddress ipAddress) {
        for (Map.Entry<InetAddress, String> entry : contactList.entrySet()) {
            if (entry.getKey().equals(ipAddress)) {
                return entry.getValue();
            }
        }
        return null;
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
            return null;

        } catch (SocketException ex) {
            throw new RuntimeException(ex);
        }
    }
}






