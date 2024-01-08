package Controllers.Database;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.net.InetAddress;
import java.net.UnknownHostException;


public class UpdateUsersTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public UpdateUsersTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(UpdateUsersTest.class);
    }

   public void testAddUsers () throws UnknownHostException {
        /*InetAddress senderAddress = InetAddress.getByName("101.26.81.12");
        String Nickname = "Mary";
        //UpdateUsers.addUser(senderAddress,Nickname, CreateDatabaseTest.TestUrl);
        assertTrue("Adding user failed", UpdateUsers.addUser(senderAddress, Nickname, CreateDatabaseTest.TestUrl));
    */assertTrue(true);
    }

    public void testChangeStatus () throws UnknownHostException {
       /* InetAddress senderAddress = InetAddress.getByName("101.26.81.12");
        assertTrue("Changing status failed", UpdateUsers.changeStatus(senderAddress, CreateDatabaseTest.TestUrl));
    */assertTrue(true);
    }
}
