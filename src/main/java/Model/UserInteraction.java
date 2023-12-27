package Model;

import Controllers.Client;

import java.net.InetAddress;
import java.io.IOException;
import java.net.UnknownHostException;

public class UserInteraction {

    private Client client ;

    public UserInteraction() {
        this.client=new Client();
    }

    public void changeUser(String ipAddress){
        try{
            InetAddress ip = InetAddress.getByName(ipAddress);
            client.startConnection(ip);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public void sendMess(String message,String ipAddress) throws IOException {
        try{
            InetAddress ip = InetAddress.getByName(ipAddress);
            client.sendMessage(message,ip);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
