package Model;

import java.net.InetAddress;
import java.util.*;
import java.net.*;

public class AppData {
    private static Map<InetAddress, String> contactList = new HashMap<>(); // map avec les contacts qui sera ensuite converti en liste
    public static User currentUser = new User(); //utilsateur actuel de l'application

    private AppData(){
        //constructeur
    }

    /*POUR LISTE DE CONTACTS*/

    //récupérer les contacts
    public static Map<InetAddress,String> getContactList(){
        return contactList;
    }

    // ajouter quelqu'un à la liste de contacts
    public static void addContactList(InetAddress sender, String received) {
        if (!contactList.containsValue(received)) { // on vérifie que la personne n'est pas déjà dans la liste
            contactList.put(sender, received);
            System.out.println("[AppData] " + received + " has been added to the contact list.");
        }
    }

    // pour supprimer quelqu'un de la liste de contacts
    public static void DeletefromContactList(InetAddress sender){
        contactList.remove(sender);
    }

    // pour changer le nom de l'utilisateur dans la liste de contacts
    public static void changeContactList(InetAddress sender, String received) {
        for (Map.Entry<InetAddress, String> pers : AppData.getContactList().entrySet()) {
            if (pers.getKey().equals(sender)){
                pers.setValue(received);
            }
        }
    }

    // récupérer le pseudo de l'utilisateur courant
    public static String getNicknameCurrentUser() {return currentUser.getNickname();}

    /*POUR L'ÉCHANGE DE MESSAGES*/

    // pour récuperer l'adresse IP à partir du pseudo
    public static InetAddress getIpByNickname (String name) {
        InetAddress res = null;
        for (Map.Entry<InetAddress, String> pers : AppData.getContactList().entrySet()) { // on parcourt les contacts
            if (pers.getValue().equals(name)) {
                res = pers.getKey();
            }
        }
        return res;
    }

    // pour récupérer l'adresse IP de sa propre machine
    // la méthode getLocalHost() renvoyait l'adresse de loopback, nous avons donc du créer une méthode pour le faire
    public static InetAddress getNonLoopbackAddress() {
        try {
            // recupérer les interfaces du réseau
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();

            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();

                // récupérer les inetAdresses
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();

                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();

                    // on vérifie qu'il ne s'agisse pas de l'adresse de loopback
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






