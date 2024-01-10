package MyView;

import Controllers.Broadcast;
import Controllers.Library;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ChangeNickname {

    private JTextField jtField = new JTextField();

    public ChangeNickname(){
        JFrame frame = new JFrame("Change Nickname");
        //fermer
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JButton button_change = new JButton("Connect");

        //lorsqu'on appuie sur le bouton de connexion
        button_change.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

        //choix du nickname
        String nickname = getNickname();
        try {
            //on vérifie si le nickname est unique et s'il l'est on l'envoie aux contacts
            if (Broadcast.CheckUnicityNickname(nickname)) {
                Broadcast.setCurrentNickname(nickname);
                Library.SendNewNickname(nickname);
                ShowConnectedUsers connectedUsers = new ShowConnectedUsers();
                frame.dispose();
            } else {
                //on demande de choisir de nouveau un nickname
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
        JLabel changeLabel = new JLabel("Change of nickname", JLabel.CENTER);
        //dimensions
        changeLabel.setPreferredSize(new Dimension(175,100));
        //creation du panel pour la zone de texte
        JPanel pText = new JPanel(new GridLayout(1,1));
        //ajouter la zone de texte pour le nickname
        pText.add(new JLabel("Choose a nickname : "));
        pText.setBorder(new EmptyBorder(50,50,50,50));
        pText.add(jtField);
        //creation du panel pour le bouton
        JPanel pButton = new JPanel(new GridLayout(1,1));
        pButton.add(button_change);

        pButton.setBorder(new EmptyBorder(50,50,50,50));

        frame.setLayout(new GridLayout(3, 1));

        //we add the elements to the frame
        frame.add(changeLabel, BorderLayout.PAGE_START);
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
}
