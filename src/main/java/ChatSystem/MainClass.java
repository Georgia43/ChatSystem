package ChatSystem;

import Model.AppData;
import Model.InputScanner;
import contactDiscovery.Broadcast;
import contactDiscovery.Library;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

//class to run the application

public class MainClass {
    public static void main(String[] args) {

        final Logger logger = Logger.getLogger("chatsystem");
        Scanner scanner = InputScanner.getScanner();

        try {
            Broadcast.Receive receiverThread = new Broadcast.Receive();
            receiverThread.start();
            //envoyer le premier message
            Library.sendFirstMessage();
            //on demande à l'utilisateur de tapper un nickname
            System.out.println("Choose a nickname : ");
            String nickname = scanner.nextLine();
            //on vérifie si le nickname est unique
            AppData.CheckUnicityNickname(nickname);
            //envoyer le nickname choisi aux contacts
            Library.SendCurrentNickname(AppData.getNicknameCurrentUser());

           System.out.println("Press enter to finish");
            System.err.println(scanner.hasNextLine());
            String none = scanner.nextLine();
            receiverThread.join(10000);
            System.out.println(Library.GetConnectedUserList());
            //envoyer le message pour se déconnecter
            //Library.sendMessageDisconnect();
        }
        catch (InterruptedException e) {
           logger.log(Level.SEVERE,"InterruptedException" + e.getMessage());
        }
    }
}
