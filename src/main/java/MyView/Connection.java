package MyView;
import Controllers.*;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class Connection {

    private JTextField jtField = new JTextField();

    public Connection() throws IOException, InterruptedException {

        JFrame frame = new JFrame("ChatSystem");
        //fermer la frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // pour lancer le broadcast
        StartEverything start = new StartEverything();

        //creation des boutons
        JButton button_connect = new JButton("Connect");

        //lorsqu'on appuie sur le bouton de connexion
        button_connect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                //choix du nickname
                    String nickname = getNickname();
                    try {
                        //on vérifie si le nickname est unique et s'il l'est on l'envoie aux contacts
                        if (Broadcast.CheckUnicityNickname(nickname)) {
                            Broadcast.setCurrentNickname(nickname);
                            Library.SendCurrentNickname(nickname);
                            // on ouvre la frame avec les contacts
                            ShowConnectedUsers connectedUsers = new ShowConnectedUsers();
                            frame.dispose();
                        } else {
                            //on demande de choisir de nouveau un nickname si il a dejà été choisi
                          JOptionPane.showMessageDialog(
                                    null,
                                    "Nickname taken. Please choose a new one.",
                                    "Dialog Title",
                                    JOptionPane.INFORMATION_MESSAGE
                            );
                        }
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
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

        // on ajoute les éléments à la frame
        frame.add(connectLabel, BorderLayout.PAGE_START);
        frame.add(pText);
        frame.add(pButton);

        frame.setSize(500, 600);
        // centrer la frame
        frame.setLocationRelativeTo(null);
        // rendre la fenêtre visible
        frame.setVisible(true);


    }

    // récupérer le nickname tapé dans le champ de texte
    public String getNickname() {
        return this.jtField.getText();
    }
}
