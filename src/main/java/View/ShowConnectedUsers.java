package View;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
public class ShowConnectedUsers {
    //parcourir table avec utilisateurs et afficher que ceux qui sont connectés

    public ShowConnectedUsers() {

        JFrame frame = new JFrame("ChatSystem");
        //fermer
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1)); // Layout pour les étiquettes

        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM USERS")) {



        }
}
