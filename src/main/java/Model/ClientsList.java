package Model;

import Controllers.Server;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class ClientsList {
    public static List<Server.ClientHandler> clients = new ArrayList<>();

    public interface  Observer {
        void onNewClient(Server.ClientHandler client);

    }
    private static List<ClientsList.Observer> observers = new ArrayList<>();

    public static void addObserver(ClientsList.Observer obs){
        observers.add(obs);
    }

    public static void addNewClient(Server.ClientHandler client) {
        clients.add(client);
        for(var observer : observers) {
            observer.onNewClient(client);
        }
    }

    public static Server.ClientHandler getClientHandlerByIP(InetAddress ipAddress) {
        for (Server.ClientHandler handler : clients) {
            if (handler.getIpSender().equals(ipAddress)) {
                return handler;
            }
        }
        return null;
    }

    public static boolean checkPresenceIP(InetAddress ip){
        for (Server.ClientHandler handler : clients) {
            if (handler.getIpSender().equals(ip)) {
                return true;
            }
        }
        return false;
    }

}
