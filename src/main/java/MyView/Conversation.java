package MyView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.List;

import Controllers.UserInteraction;

public class Conversation {
    private String name;
    private String ipaddress;
    // création de messageArea pour afficher les messages sur la frame
    JTextArea messageArea = new JTextArea();

    public Conversation (String name, String ipaddress) {

        this.name = name;
        this.ipaddress = ipaddress;

        JFrame frame = new JFrame("Conversation with " + name);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
     
       messageArea.setEditable(false);


        // création du messageField pour taper des messages
        JTextField messageField = new JTextField();

        // création du sendMessageButton pour l'envoi des messages 
        JButton sendMessageButton = new JButton("Send");

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        panel.add(new JScrollPane(messageArea), BorderLayout.CENTER);
        panel.add(messageField, BorderLayout.SOUTH);
        panel.add(sendMessageButton, BorderLayout.EAST);

        frame.getContentPane().add(panel);

        sendMessageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String message = messageField.getText(); //récupérer le message tapé par l'utilisateur
                UserInteraction inter = null;
                try {
                    inter = new UserInteraction();
                } catch (UnknownHostException e) {
                    throw new RuntimeException(e);
                }
                try {
                    inter.sendMess(message,ipaddress); //envoie du message  
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                messageArea.append("You : " + message + "\n"); //ajouter le message envoyé au messageArea avec un You devant pour le distinguer des messages reçus
                messageField.setText("");
            }
        });


        frame.setSize(400, 300); 
        frame.setLocationRelativeTo(null); 
        frame.setVisible(true);

    }

    //afficher l'historique des messages 
    public void displayChatHistory(List<String> chatHistory) {
        for (String message : chatHistory) {
            String[] parts = message.split(" ", 4); //pour distinguer les parties du message
            if (parts.length == 4) {
                String date = parts[0];
                String time = parts[1];
                String sender = parts[2];
                String content = parts[3];
                String displayMessage = date + " - " + time + " - " + (isReceivedMessage(sender) ? name : "You") + ": " + content;
                messageArea.append(displayMessage + "\n");
            } else {
                System.err.println("Invalid message format: " + message);
            }
        }
    }
    
    //pour distinguer les messages reçus et envoyés lors de l'affichage de l'historique
    private boolean isReceivedMessage(String sender) {
        return sender.equals(ipaddress);
    }

    //pour afficher les messages reçus
    public void displayMessage(InetAddress sender, String message){
        messageArea.append(name +" : "+message + "\n");
    }

    public InetAddress getIp() throws UnknownHostException {
        return InetAddress.getByName(ipaddress);
    }

}

