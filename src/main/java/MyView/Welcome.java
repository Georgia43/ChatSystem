package MyView;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Welcome {

    public Welcome() {
        JFrame frame = new JFrame("ChatSystem");
        //fermer
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //message d'accueil
        JLabel welcomeLabel = new JLabel("Welcome to Chatsystem", JLabel.CENTER);
        //dimensions
        welcomeLabel.setPreferredSize(new Dimension(175,100));

        JButton startButton = new JButton("Start");

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    Connection connection = new Connection();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                frame.dispose();
            }
        });

        frame.setLayout(new GridLayout(3, 1));

        frame.add(welcomeLabel, BorderLayout.PAGE_START);

        //creation du panel pour le bouton
        JPanel pButton = new JPanel(new GridLayout(1,1));
        pButton.add(startButton);

        pButton.setBorder(new EmptyBorder(50,50,50,50));
        frame.add(pButton);


        frame.setSize(500, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
}
