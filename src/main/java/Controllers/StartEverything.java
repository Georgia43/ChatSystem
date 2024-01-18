package Controllers;

import Model.ClientsList;
import MyView.*;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class StartEverything {

    // liste des conversations
    public static List <Conversation> conversationList = new ArrayList<>();

    public StartEverything() {
        // lacement du broadcast
        Broadcast.Receive receiverThread = new Broadcast.Receive();
        receiverThread.start();
        Server server = new Server();

        // ajout d'un observateur pour chaque nouveau client
        ClientsList.addObserver(new ClientsList.Observer() {
            @Override
            public void onNewClient(Server.ClientHandler client) {
                client.addObserver(new Server.ClientHandler.Observer() {
                    @Override
                    public void handleMess(String message) throws UnknownHostException {
                        System.out.println("[StartEverything] I receive " + message);
                        // parcours des conversations pour trouver la correspondance avec l'adresse IP du client
                        for (Conversation conv : conversationList) {
                            if (conv.getIp().equals(client.getIpSender())) {
                                // affichage du message dans la conversation correspondante
                                conv.displayMessage(client.getIpSender(),message);
                            }
                        }

                    }
                });
            }
        });
        server.start();

        //envoyer le premier message indiquant la demande de connexion
        Library.sendFirstMessage();
    }

}
