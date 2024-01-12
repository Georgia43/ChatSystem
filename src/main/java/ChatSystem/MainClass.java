package ChatSystem;

import Model.InputScanner;
import MyView.Welcome;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Logger;

//class to run the application

public class MainClass {
    public static void main(String[] args) throws IOException, InterruptedException {

        final Logger logger = Logger.getLogger("chatsystem");
        Scanner scanner = InputScanner.getScanner();

         Welcome welcome = new Welcome();
        //StartEverything startEverything = new StartEverything();
          /*  Broadcast.Receive receiverThread = new Broadcast.Receive();
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
            String none = scanner.nextLine();
            receiverThread.join(5000);
            System.out.println(Library.GetConnectedUserList());*/
        //envoyer le message pour se déconnecter
        //Library.sendMessageDisconnect(); on commente cette ligne pour bien visualiser la contactlist car elle enlève la personne envoyant disconnect de la contactlist
    }
}
