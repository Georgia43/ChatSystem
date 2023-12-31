package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Objects;

import Controllers.Server;
import Controllers.UserInteraction;

public class Conversation {

    public Conversation (String name, String ipaddress) {
        JFrame frame = new JFrame("Conversation with " + name);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the JTextArea for displaying messages
        JTextArea messageArea = new JTextArea();
        messageArea.setEditable(false);

        // Create the JTextField for typing messages
        JTextField messageField = new JTextField();

        // Create the JButton for sending messages
        JButton sendMessageButton = new JButton("Send");

        // Set layouts
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Add components to the panel
        panel.add(new JScrollPane(messageArea), BorderLayout.CENTER);
        panel.add(messageField, BorderLayout.SOUTH);
        panel.add(sendMessageButton, BorderLayout.EAST);

        // Add the panel to the frame
        frame.getContentPane().add(panel);

        sendMessageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String message = messageField.getText();
                UserInteraction inter = null;
                try {
                    inter = new UserInteraction();
                } catch (UnknownHostException e) {
                    throw new RuntimeException(e);
                }
                try {
                    System.out.println("action listener send");
                    inter.sendMess(message,ipaddress);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                messageArea.append("You : " + message + "\n");
                messageField.setText("");
            }
        });


        frame.setSize(400, 300); // Set frame size
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setVisible(true);

        startReceiving(messageArea,name,ipaddress);
    }

    private volatile boolean receivingMessages = true;
    private void startReceiving(JTextArea messageArea,String name,String ip) {
        new Thread(()->{
            UserInteraction inter = null;
            try {
                inter = new UserInteraction();
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            }
          while (receivingMessages){
                String receivedMess=inter.getMessageReceived();
                if (Objects.equals(inter.getSender(), ip)){
                SwingUtilities.invokeLater(()->{
                            messageArea.append(name+": "+receivedMess+"\n");
                        }
                );
           }}
        }).start();
    }

    public void stopReceiving() {
        receivingMessages = false;
    }
    public static void main(String[] args) {
        // Create an instance of the Conversation class
        Conversation conversation = new Conversation("Mary", "1.1.1.1");
    }
}

