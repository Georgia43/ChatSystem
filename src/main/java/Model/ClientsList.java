package Model;

import Controllers.Server;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class ClientsList {
    // classe ClientsList gérant la liste des clients connectés.
    // liste de clients
    public static List<Server.ClientHandler> clients = new ArrayList<>();

    public interface  Observer {
        void onNewClient(Server.ClientHandler client);
    }

    // Liste des observateurs
    private static List<ClientsList.Observer> observers = new ArrayList<>();

    // pour ajouter un observer
    public static void addObserver(ClientsList.Observer obs){
        observers.add(obs);
    }

    // ajoute un nouveau client à la liste et notifie les observateurs.
    public static void addNewClient(Server.ClientHandler client) {
        clients.add(client);
        for(var observer : observers) {
            observer.onNewClient(client);
        }
    }

    // pour récupérer le clientHandler à partir de l'adress IP
    public static Server.ClientHandler getClientHandlerByIP(InetAddress ipAddress) {
        for (Server.ClientHandler handler : clients) { // on parcourt la liste de clienthandlers
            if (handler.getIpSender().equals(ipAddress)) {
                return handler;
            }
        }
        return null;
    }

    // pour vérifier si l'adress ip existe déjà dans la liste
    // si c'est le cas, le client handler est dans la liste, on n'a pas besoin de l'ajouter
    public static boolean checkPresenceIP(InetAddress ip){
        for (Server.ClientHandler handler : clients) {
            if (handler.getIpSender().equals(ip)) {
                return true;
            }
        }
        return false;
    }

}
