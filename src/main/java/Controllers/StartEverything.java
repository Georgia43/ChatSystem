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
import Model.ClientsList;

public class StartEverything {
    //Broadcast
    public StartEverything() throws IOException, InterruptedException {
        Broadcast.Receive receiverThread = new Broadcast.Receive();
        receiverThread.start();

        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!before starting server");
        Server server = new Server();
        ClientsList.addObserver(new ClientsList.Observer() {
            @Override
            public void onNewClient(Server.ClientHandler client) {
                client.addObserver(new Server.ClientHandler.Observer() {
                    @Override
                    public void handleMess(String message) {
                        System.out.println("I receive " + message);
                     //   MyView.Conversation.displayMessage(client.getIpSender(),message);
                    }
                });
            }
        });
        server.start();
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!i just starded the server");

        //envoyer le premier message
        Library.sendFirstMessage();
    }

}
