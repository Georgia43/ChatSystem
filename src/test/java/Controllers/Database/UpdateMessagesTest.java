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

    public void testAddMessages() throws UnknownHostException {
        assert CreateDatabase.Connect(CreateDatabase.url);
        InetAddress senderAddress = InetAddress.getByName("101.26.81.12");
        UpdateMessages.addMessage(senderAddress, "test");
    }

}
