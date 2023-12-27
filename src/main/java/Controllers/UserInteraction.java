package Controllers;

import Controllers.Client;
import Controllers.Database.CreateDatabase;
import Controllers.Database.UpdateMessages;

import java.net.InetAddress;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;

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
            CreateDatabase database = new CreateDatabase(CreateDatabase.MESSAGE_DATABSE);
            UpdateMessages.addMessage(ip,message,CreateDatabase.MESSAGE_DATABSE);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
