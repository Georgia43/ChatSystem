package Model;


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;



public class HandleMessageTest extends TestCase {

    public HandleMessageTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(HandleMessageTest.class);
    }

    public void testHandleValidMessage() throws UnknownHostException {
        // Mock the database interaction

        // Mock data for testing
        InetAddress sender = InetAddress.getByName("127.0.0.1");
        String validMessage = "MESSAGE_Hello, this is a valid message.";

        try {
            String result = HandleMessage.handle(sender, validMessage);
            assertEquals(validMessage.substring("MESSAGE_".length()), result);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void testHandleInvalidMessage() throws UnknownHostException {
        // Mock data for testing
        InetAddress sender = InetAddress.getByName("127.0.0.1");
        String invalidMessage = "INVALID_MESSAGE_Hello, this is an invalid message.";

        try {
            HandleMessage.handle(sender, invalidMessage);
            // If no exception is thrown, fail the test
            fail("Expected RuntimeException, but no exception was thrown.");
        } catch (RuntimeException e) {
            // Assert the expected message
            assertEquals("invalid message received", e.getMessage());
        } catch (SQLException e) {
            // If SQLException is thrown, fail the test with an unexpected exception
            fail("Unexpected SQLException: " + e.getMessage());
        }
    }
}
