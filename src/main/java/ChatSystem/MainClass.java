package ChatSystem;

import contactDiscovery.Broadcast;
import contactDiscovery.Library;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

//class to run the application

public class MainClass {
    public static void main(String[] args) {

        final Logger logger = Logger.getLogger("chatsystem");

        try { //we try the whole app
            Broadcast.Receive receiverThread = new Broadcast.Receive();
            receiverThread.start();
            //Library.sendMessage("user1");
           // Library.sendMessage("user2");

            //  Thread.sleep(200);
            receiverThread.join(300);
            //receiverThread.join();
            System.out.println(Library.GetConnectedUserList());
        }
        catch (InterruptedException e) {
           logger.log(Level.SEVERE,"InterruptedException" + e.getMessage());
        }
    }
}
