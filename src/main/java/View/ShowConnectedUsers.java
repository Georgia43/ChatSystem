package View;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import static Controllers.Database.CreateDatabase.MESSAGE_DATABSE;

public class ShowConnectedUsers {
    //parcourir table avec utilisateurs et afficher que ceux qui sont connectés
    public ShowConnectedUsers() {

        JFrame frame = new JFrame("ChatSystem");
        //fermer
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1)); // Layout pour les étiquettes

        try {
            java.sql.Connection connection = DriverManager.getConnection(MESSAGE_DATABSE);
             Statement statement = connection.createStatement();
             ResultSet result_users = statement.executeQuery("SELECT * FROM USERS where status = 1");
             while(result_users.next()){
                   // int status=result_users.getInt("status");
                    String name = result_users.getString("name");
                    String ipAddress = result_users.getString("ipAdress");

                 JPanel p = new JPanel(new BorderLayout());

                 JPanel infoPanel = new JPanel(new GridLayout(1,1));
                 infoPanel.add(new JLabel("name:"));
                 infoPanel.add(new JLabel(name));

                 p.add(infoPanel, BorderLayout.CENTER);

                 JButton button_message = new JButton("Accept");

                 button_message.addActionListener(new ActionListener() {
                     @Override
                     public void actionPerformed(ActionEvent actionEvent) {
                        //je choisis la personne avec laquelle je veux échanger des messages
                         //appeler change user change_user(ipAdress)
                     }
                 });


             }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
