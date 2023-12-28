package View;
import Controllers.*;
import Controllers.Library;
import Model.AppData;
import Model.User;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.sql.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import static Controllers.Database.CreateDatabase.MESSAGE_DATABSE;


public class Connection {

    //for nickname
    private JTextField jtField = new JTextField();

    public Connection() {

        JFrame frame = new JFrame("ChatSystem");
        //fermer
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //creation des boutons
        JButton button_connect = new JButton("Connect");

        //lorsqu'on appuie sur le bouton de connexion
        button_connect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //Broadcast
                Broadcast.Receive receiverThread = new Broadcast.Receive();
                receiverThread.start();
                //envoyer le premier message
                Library.sendFirstMessage();
                //choix du nickname
                while (true) {

                    String nickname = getNickname();
                    try {
                        //on vérifie si le nickname est unique
                        if (Broadcast.CheckUnicityNickname(nickname)) {
                            Broadcast.setCurrentNickname(nickname);
                            break;
                        } else {
                            //on demande de choisir de nouveau un nickname
                            JFrame popupFrame = new JFrame("Enter a new nickname");
                            JTextField newNicknameField = new JTextField();
                            JButton confirmButton = new JButton("Confirm");

                            confirmButton.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent actionEvent) {
                                    String newNickname = newNicknameField.getText();
                                    Broadcast.setCurrentNickname(newNickname);
                                    popupFrame.dispose();

                                }
                            });
                            popupFrame.setLayout(new GridLayout(2, 1));
                            popupFrame.add(newNicknameField);
                            popupFrame.add(confirmButton);
                            popupFrame.setSize(300, 150);
                            popupFrame.setLocationRelativeTo(null);
                            popupFrame.setVisible(true);


                            while (popupFrame.isVisible()) {
                                try {
                                    Thread.sleep(100); //Attente courte
                                } catch (InterruptedException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    //envoyer le nickname choisi aux contacts
                    try {
                        Library.SendCurrentNickname(AppData.getNicknameCurrentUser());
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    // Start the server
                    Controllers.Server server = new Server();
                    server.start();

                }
            }
        });

        //creation du label "connection"
        JLabel connectLabel = new JLabel("Connection", JLabel.CENTER);
        //dimensions
        connectLabel.setPreferredSize(new Dimension(175,100));

        //creation du panel pour la zone de texte
        JPanel pText = new JPanel(new GridLayout(1,1));
        //ajouter la zone de texte pour le nickname
        pText.add(new JLabel("Choose a nickname : "));
        pText.setBorder(new EmptyBorder(50,50,50,50));
        pText.add(jtField);
        //creation du panel pour le bouton
        JPanel pButton = new JPanel(new GridLayout(1,1));
        pButton.add(button_connect);
        pButton.setBorder(new EmptyBorder(50,50,50,50));

        frame.setLayout(new GridLayout(3, 1));

        //we add the elements to the frame
        frame.add(connectLabel, BorderLayout.PAGE_START);
        frame.add(pText);
        frame.add(pButton);

        frame.setSize(500, 600);
        // Center the frame on the screen
        frame.setLocationRelativeTo(null);
        // Display the window.
        frame.setVisible(true);




    }

    public String getNickname() {
        return this.jtField.getText();
    }

    public static void main(String[] args) {
        // Create an instance of the Connection class
        Connection connection = new Connection();}

}
