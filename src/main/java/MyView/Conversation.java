package MyView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import Controllers.UserInteraction;

public class Conversation {
    private String name;
    private String ipaddress;
    // Create the JTextArea for displaying messages
    JTextArea messageArea = new JTextArea();

    public Conversation (String name, String ipaddress) {

        this.name = name;
        this.ipaddress = ipaddress;

        JFrame frame = new JFrame("Conversation with " + name);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      /*  frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                stopReceiving();
            }
        });

*/

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

       // startReceiving(messageArea,name,ipaddress);
    }

   // private Object lock = new Object(); // Créer un objet verrou

    //private Set<String> processedMessages = new HashSet<>();
  //  private volatile boolean receivingMessages = true;
  /*  private void startReceiving(JTextArea messageArea,String name,String ip) {
        new Thread(()->{
            UserInteraction inter = null;
            try {
                inter = new UserInteraction();
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            }
          while (receivingMessages) {
              synchronized (lock) { // Synchroniser sur le verrou
                  try {
                      lock.wait(); // Attendre jusqu'à ce qu'une notification soit reçue
                  } catch (InterruptedException e) {
                      e.printStackTrace();
                  }
                  String receivedMess = inter.getMessageReceived();
                 if (Objects.equals(inter.getSender(), ip) && receivedMess != null) {
                      SwingUtilities.invokeLater(() -> {
                          messageArea.append(name + ": " + receivedMess + "\n");
                      });
                  }
              }
          }
        }).start();
    }
    
        public void notifyNewMessage() {
            synchronized (lock) { // Synchroniser sur le verrou
                lock.notify(); // Notifier qu'un nouveau message est arrivé
            }
        }*/

    /*private void startReceiving(JTextArea messageArea, String name, String ip) {
            new Thread(()->{
                UserInteraction inter = null;
                try {
                    inter = new UserInteraction();
                } catch (UnknownHostException e) {
                    throw new RuntimeException(e);
                }
            while (receivingMessages) {
                String receivedMess = inter.getMessageReceived(); // Simulated received message
                if (Objects.equals(inter.getSender(), ip) && !alreadyProcessed(receivedMess)) {
                    try {
                        UpdateMessages.addReceivedMessage(InetAddress.getByName(ip), receivedMess, CreateDatabase.createURL(Objects.requireNonNull(getNonLoopbackAddress())));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (UnknownHostException e) {
                        throw new RuntimeException(e);
                    }
                    processMessage(receivedMess, name, messageArea);
                }
            }
        }).start();
    }*/

    /*private boolean alreadyProcessed(String message) {
        return processedMessages.contains(message);
    }*/

    /*private void processMessage(String message, String name, JTextArea messageArea) {
        processedMessages.add(message);
        SwingUtilities.invokeLater(() -> {
            messageArea.append(name + ": " + message + "\n");
        });
    }*/

    public void displayMessage(InetAddress sender, String message){
        messageArea.append("Other : "+message + "\n");
    }

    public InetAddress getIp() throws UnknownHostException {
        return InetAddress.getByName(ipaddress);
    }


    /*public void stopReceiving() {
        receivingMessages = false;
    }*/
    public static void main(String[] args) {
        // Create an instance of the Conversation class
        Conversation conversation = new Conversation("Mary", "1.1.1.1");
    }
}

