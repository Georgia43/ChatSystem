package Controllers;
import Model.AppData;
import Model.ClientsList;
import Model.User;
import MyView.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static Model.AppData.getNonLoopbackAddress;

public class StartEverything {

    public static List <Conversation> conversationList = new ArrayList<>();
    //Broadcast
    public StartEverything() throws IOException, InterruptedException {
        Broadcast.Receive receiverThread = new Broadcast.Receive();
        receiverThread.start();
        Server server = new Server();
        ClientsList.addObserver(new ClientsList.Observer() {
            @Override
            public void onNewClient(Server.ClientHandler client) {
                client.addObserver(new Server.ClientHandler.Observer() {
                    @Override
                    public void handleMess(String message) throws UnknownHostException {
                        System.out.println("I receive " + message);
                        for (Conversation conv : conversationList) {
                            if (conv.getIp().equals(client.getIpSender())) {
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
