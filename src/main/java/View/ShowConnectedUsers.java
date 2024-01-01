package View;
import Controllers.Broadcast;
import Controllers.Server;
import Controllers.UserInteraction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.*;
import java.sql.Connection;

import static Controllers.Database.CreateDatabase.MESSAGE_DATABSE;

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

        // Create and add the "Update" button
        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateConnectedUsers();
            }
        });

      /*  JButton DisconnectButton = new JButton("Disconnect");
        DisconnectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Welcome welcome = new Welcome();
                Broadcast.sendExitMessage();
                frame.dispose();
              //  Server.stop();
                Broadcast.Receive.closeSocket();
            }
        });*/

        ButtonsPanel.add(updateButton); // pour ajouter le bouton en haut
      //  ButtonsPanel.add(DisconnectButton);
        frame.add(ButtonsPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(panel));
        frame.setSize(500, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Initial update
        updateConnectedUsers();
    }

    public void updateConnectedUsers() {
        try {
            panel.removeAll();

            java.sql.Connection connection = DriverManager.getConnection(MESSAGE_DATABSE);
            Statement statement = connection.createStatement();
             //choisir utilisateur parmi ceux qui sont connectes
            ResultSet result_users = statement.executeQuery("SELECT * FROM USERS WHERE status = 'Connected'");

            while (result_users.next()) {
                String name = result_users.getString("name");
                String ipAddress = result_users.getString("ipAddress");
                InetAddress strAddress = InetAddress.getByName(ipAddress);

                // pour ne pas être dans sa propre liste d'utilisateurs connectés ni apparaitre quand on a pas choisi le pseudo
                if (!Broadcast.receivedFromMyself(strAddress) && !name.equals("null")) {

                    JPanel userPanel = new JPanel(new BorderLayout()); //panel pour utilisateurs
                    JPanel infoPanel = new JPanel(new GridLayout(1, 1));
                    infoPanel.add(new JLabel("name:"));
                    infoPanel.add(new JLabel(name));
                    userPanel.add(infoPanel, BorderLayout.CENTER);

                    //je choisis la personne avec laquelle je veux échanger des messages ou voir mon historique de messages
                    JButton button_message = new JButton("Accept");

                    button_message.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            Conversation conv = new Conversation(name, ipAddress);
                        }
                    });

                    userPanel.add(button_message, BorderLayout.SOUTH);
                    //ajouter panel pour utilisateurs au panel principal
                    panel.add(userPanel);
                }
                }

                frame.revalidate();
                frame.repaint();
           // }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException {
        // Create an instance of the Connection class
        ShowConnectedUsers ConnectedUsers = new ShowConnectedUsers();}
}
