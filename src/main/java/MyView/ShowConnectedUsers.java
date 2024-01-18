package MyView;
import Controllers.Broadcast;
import Controllers.Database.CreateTables;
import Controllers.Database.UpdateMessages;
import Controllers.Library;
import Controllers.StartEverything;
import Model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Objects;

import static Model.AppData.getNonLoopbackAddress;

public class ShowConnectedUsers {
    //parcourir table avec utilisateurs et afficher que ceux qui sont connectés
    private JFrame frame;
    private JPanel panel;
    JPanel ButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

    public ShowConnectedUsers() throws IOException {

        frame = new JFrame("ChatSystem");
        //fermer
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //envoyer un message de déconnexion quand on ferme la frame
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Broadcast.sendExitMessage();
            }
        });

        panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));

        // création du bouton updateButton qui permet de mettre à jour la liste des utilisateurs connectés
        JButton updateButton = new JButton("Update");
        // création du bouton changeNickButton qui permet à l'utilisateur de changer son nickname
        JButton changeNickButton = new JButton("Change Nickname");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateConnectedUsers();
            }
        });
        changeNickButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                ChangeNickname change = new ChangeNickname();
            }
        });

    
        ButtonsPanel.add(updateButton); // pour ajouter le bouton en haut
        ButtonsPanel.add(changeNickButton);
        // ajouter les éléments à la frame
        frame.add(ButtonsPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(panel));
        frame.setSize(500, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Mise à jour initiale de la liste des utilisateurs connectés
        updateConnectedUsers();
    }

    // pour mettre à jour les utilisateurs connectés sur la frame
    public void updateConnectedUsers() {
        panel.removeAll();

        ArrayList<User> connectedUsers = Library.GetConnectedUserList();

        // on parcourt la liste d'utilisateurs connectés
        for (User user : connectedUsers) {
            String name = Library.getNameUser(user);
            InetAddress ipAddress = Library.getIpUser(user);

            // pour ne pas être dans sa propre liste d'utilisateurs connectés ni apparaitre quand on a pas choisi le nickname
            if (Broadcast.receivedFromMyself(ipAddress) && !name.equals("null")) {

                JPanel userPanel = new JPanel(new BorderLayout()); //panel pour utilisateurs
                JPanel infoPanel = new JPanel(new GridLayout(1, 1));
                infoPanel.add(new JLabel("name:"));
                infoPanel.add(new JLabel(name));
                userPanel.add(infoPanel, BorderLayout.CENTER);

                //pour choisir la personne avec laquelle je veux échanger des messages ou voir mon historique de messages
                JButton button_message = new JButton("Accept");

                // on ouvre une nouvelle conversation lorsqu'on appuie sur "accept"
                button_message.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        Conversation conv = new Conversation(name, ipAddress.getHostAddress());
                        StartEverything.conversationList.add(conv); //ajout de la conversation dans la liste avec les conversations afin de savoir dans quelle frame on doit afficher un message reçu ainsi que l'historique des messages 
                        conv.displayChatHistory(UpdateMessages.loadMessagesFromDatabase(ipAddress, CreateTables.createURL(Objects.requireNonNull(getNonLoopbackAddress())))); //pour l'affichage de l'historique des messages échangés avec l'utilisateur correspondant à ipAddress
                    }
                });

                userPanel.add(button_message, BorderLayout.SOUTH);
                //ajouter panel pour utilisateurs au panel principal
                panel.add(userPanel);
            }
            }
        frame.revalidate();
        frame.repaint();
    }

}
