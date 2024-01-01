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

import static Controllers.Database.CreateDatabase.MESSAGE_DATABSE;

public class ShowConnectedUsers {
    //parcourir table avec utilisateurs et afficher que ceux qui sont connectés
    private JFrame frame;
    private JPanel panel;

    public ShowConnectedUsers() throws IOException {

        frame = new JFrame("ChatSystem");
        //fermer
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //envoyer un messagede déconnexion quand on ferme la frame
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

        frame.add(updateButton, BorderLayout.NORTH); // pour ajouter le vouton en haut
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

                // pour ne pas être dans sa propre liste d'utilisateurs connectés
                if (!Broadcast.receivedFromMyself(strAddress)) {

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
