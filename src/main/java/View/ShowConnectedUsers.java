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
import java.sql.*;

import static Controllers.Database.CreateDatabase.MESSAGE_DATABSE;

public class ShowConnectedUsers {
    //parcourir table avec utilisateurs et afficher que ceux qui sont connectés
    public ShowConnectedUsers() throws IOException {

        JFrame frame = new JFrame("ChatSystem");
        //fermer
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //envoyer un messagede déconnexion quand on ferme la frame
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Call the sendExitMessage function when the window is closing
                Broadcast.sendExitMessage();
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1)); // Layout pour les étiquettes

        try {
            java.sql.Connection connection = DriverManager.getConnection(MESSAGE_DATABSE);
             Statement statement = connection.createStatement();
             ResultSet result_users = statement.executeQuery("SELECT * FROM USERS where status = 'Connected'");
             while(result_users.next()){
                   // int status=result_users.getInt("status");
                    String name = result_users.getString("name");
                    String ipAddress = result_users.getString("ipAddress");

                 JPanel p = new JPanel(new BorderLayout()); //panel pour utilisateurs

                 JPanel infoPanel = new JPanel(new GridLayout(1,1));
                 infoPanel.add(new JLabel("name:"));
                 infoPanel.add(new JLabel(name));

                 p.add(infoPanel, BorderLayout.CENTER);

                 JButton button_message = new JButton("Accept"); //choisir utilisateur parmi ceux qui sont connectes

                 button_message.addActionListener(new ActionListener() {
                     @Override
                     public void actionPerformed(ActionEvent actionEvent) {
                        //je choisis la personne avec laquelle je veux échanger des messages ou voir mon historique de messages
                         /*UserInteraction inter = new UserInteraction();
                         inter.changeUser(ipAddress);*/
                         Conversation conv = new Conversation(name,ipAddress);
                     }
                 });

                 p.add(button_message,BorderLayout.SOUTH); //ajouter panel pour utilisateurs au panel principal
                 panel.add(p);
             }
             frame.add(new JScrollPane(panel));
             frame.setSize(500,600);
             frame.setLocationRelativeTo(null);
             frame.setVisible(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) throws IOException {
        // Create an instance of the Connection class
        ShowConnectedUsers ConnectedUsers = new ShowConnectedUsers();}
}
