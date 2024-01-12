package Controllers.Database;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class UpdateMessagesTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public UpdateMessagesTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(Controllers.Database.UpdateMessagesTest.class);
    }

    // pour vérifier qu'il y a bien un message dans la base de données
    private List<String> getMessagesFromDatabase(InetAddress Address, String url) throws SQLException {
        List<String> messages = new ArrayList<>();
        try {
            CreateTables database = new CreateTables(url);
            Statement statement = database.connection.createStatement();

            String strAddress = Address.getHostAddress().replace('.', '_');
            String selectQuery = "SELECT content FROM Messages_" + strAddress;

            try (ResultSet resultSet = statement.executeQuery(selectQuery)) {
                while (resultSet.next()) {
                    messages.add(resultSet.getString("content"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }
  public void testSentMessages() throws UnknownHostException, SQLException {
       CreateTables dbTest = new CreateTables(CreateTablesTest.TestUrl);
       InetAddress senderAddress = InetAddress.getByName("101.26.81.12");
       dbTest.tableMessages(senderAddress); //au cas ou la table n'a pas été créée au préalable
      // ajoute le message
       assertTrue("Adding the message failed.",UpdateMessages.addSentMessage(senderAddress, "test addSentMessage", CreateTablesTest.TestUrl));
      // vérifie que le message a été ajouté
       List<String> messages = getMessagesFromDatabase(senderAddress, CreateTablesTest.TestUrl);
       assertTrue("The received message is not in the database.", messages.contains("test addSentMessage"));
       dbTest.closeConnection();
    }
  public void testReceivedMessages() throws UnknownHostException, SQLException, InterruptedException {
        CreateTables dbTest = new CreateTables(CreateTablesTest.TestUrl);
        InetAddress senderAddress = InetAddress.getByName("101.26.81.12");
        dbTest.tableMessages(senderAddress); //au cas ou la table n'a pas été créée au préalable
      Thread.sleep(1000); // la clé primaire est l'heure d'envoi du message donc il faut attendre un peu avant d'ajouter le prochain message
      // ajoute le message a base de données
      assertTrue("Adding the message failed.",UpdateMessages.addReceivedMessage(senderAddress, "test addReceivedMessage", CreateTablesTest.TestUrl));
      // vérifie que le message a été ajouté
      List<String> messages = getMessagesFromDatabase(senderAddress, CreateTablesTest.TestUrl);
      assertTrue("The received message is not in the database.", messages.contains("test addReceivedMessage"));
        dbTest.closeConnection();
 }

}
