package ChatSystem;

import Model.AppData;
import contactDiscovery.Broadcast;
import contactDiscovery.Library;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

//class to run the application

public class MainClass {
    public static void main(String[] args) {

        final Logger logger = Logger.getLogger("chatsystem");
        Scanner scanner = new Scanner(System.in);

        if (args.length < 1) {
            System.out.println("Usage: java MainClass <port>");
            System.exit(1);
        }

        int port = Integer.parseInt(args[0]);

        try {
            Broadcast.Receive receiverThread = new Broadcast.Receive(port);
            receiverThread.start();
            //envoyer le premier message
            Library.sendFirstMessage();
            //on demande à l'utilisateur de tapper un nickname
            System.out.println("Choose a nickname : ");
            String nickname = scanner.nextLine();
            //on vérifie si le nickname est unique
            AppData.CheckUnicityNickname(nickname);
            //envoyer le nickname choisi aux contacts
            Library.SendCurrentNickname(nickname);
            receiverThread.join(10000);
            System.out.println(Library.GetConnectedUserList());
            //envoyer le message pour se déconnecter
            Library.sendMessageDisconnect();
        }
        catch (InterruptedException e) {
           logger.log(Level.SEVERE,"InterruptedException" + e.getMessage());
        }
    }
}
