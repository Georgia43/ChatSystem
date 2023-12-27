package Model;

import Controllers.Client;

import java.net.InetAddress;
import java.io.IOException;

public class UserInteraction {

    public void changeUser(String ipAddress){
        try{
            InetAddress ip = InetAddress.getByName(ipAddress);
            Client client = new Client();
            client.startConnection(ip);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
