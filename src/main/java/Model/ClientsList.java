package Model;

import Controllers.Server;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class ClientsList {
    public static List<Server.ClientHandler> clients = new ArrayList<>();

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
