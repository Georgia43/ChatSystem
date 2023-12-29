package View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import Controllers.UserInteraction;

public class Conversation {

    public Conversation (String name, String ipaddress) {
            JFrame frame = new JFrame("Conversartion with "+name);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JFrame messageFrame = new JFrame("Messaging");
            JTextArea messageArea = new JTextArea();
            messageArea.setEditable(false);
            JTextField messageField = new JTextField();
            JButton sendMessageButton = new JButton("Send");

        // Set layouts
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(messageArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Add components to the panel
        panel.add(scrollPane);
        panel.add(messageField);
        panel.add(sendMessageButton);

        // Add the panel to the frame
        frame.getContentPane().add(panel);

            sendMessageButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    String message = messageField.getText();
                    UserInteraction inter = new UserInteraction();
                    try {
                        inter.sendMess(message,ipaddress);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    messageArea.append("You : "+message + "\n");
                    messageField.setText("");
                }
            });
        frame.setSize(400, 400); // Set frame size
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        // Create an instance of the Connection class
        Conversation conversation = new Conversation("Mary","1.1.1.1");}
}
