package Controllers.Database;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;

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

  public void testSentMessages() throws UnknownHostException, SQLException {
       CreateDatabase dbTest = new CreateDatabase(CreateDatabaseTest.TestUrl);
       InetAddress senderAddress = InetAddress.getByName("101.26.81.12");
       assertTrue("Adding the message failed.",UpdateMessages.addSentMessage(senderAddress, "test",CreateDatabaseTest.TestUrl));
    }

   /* public void testReceivedMessages() throws UnknownHostException, SQLException {
        CreateDatabase dbTest = new CreateDatabase(CreateDatabaseTest.TestUrl);
        InetAddress senderAddress = InetAddress.getByName("101.26.81.12");
        assertTrue("Adding the message failed.",UpdateMessages.addReceivedMessage(senderAddress, "test",CreateDatabaseTest.TestUrl));

    }*/

}
