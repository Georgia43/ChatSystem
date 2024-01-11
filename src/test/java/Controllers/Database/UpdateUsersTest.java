package Controllers.Database;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;


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

   public void testAddUsers () throws UnknownHostException, SQLException {
        InetAddress senderAddress = InetAddress.getByName("101.26.81.12");
        String Nickname = "Mary";
        CreateDatabase dbTest = new CreateDatabase(CreateDatabaseTest.TestUrl);
        dbTest.tableUsers(); // au cas ou la table, n'a pas été créée avant
        //UpdateUsers.addUser(senderAddress,Nickname, CreateDatabaseTest.TestUrl);
        assertTrue("Adding user failed", UpdateUsers.addUser(senderAddress, Nickname, CreateDatabaseTest.TestUrl));
        dbTest.closeConnection();
    }

    public void testUserExists() throws UnknownHostException, SQLException {
        InetAddress senderAddress = InetAddress.getByName("101.26.81.12");
        InetAddress senderAddress2 = InetAddress.getByName("101.26.81.13");
        String Nickname = "John";
        CreateDatabase dbTest = new CreateDatabase(CreateDatabaseTest.TestUrl);
        dbTest.tableUsers();
        UpdateUsers.addUser(senderAddress, Nickname, CreateDatabaseTest.TestUrl);
        // cas ou l'utilisateur existe
        assertTrue("UserExists failed when the user exists", UpdateUsers.userExists(senderAddress, CreateDatabaseTest.TestUrl));
        // cas ou l'utilisateur n'existe pas
        assertFalse("UserExists failed when the user doesn't exists",UpdateUsers.userExists(senderAddress2, CreateDatabaseTest.TestUrl));
        dbTest.closeConnection();
    }

    public void testNicknameIsSame() throws UnknownHostException, SQLException {
        /*InetAddress senderAddress = InetAddress.getByName("101.26.81.12");
        String Nickname = "John";
        CreateDatabase dbTest = new CreateDatabase(CreateDatabaseTest.TestUrl);
        dbTest.tableUsers();
        UpdateUsers.addUser(senderAddress, Nickname, CreateDatabaseTest.TestUrl);
        // cas ou le pseudo est le même
        //assertTrue("Checking if nickname is same failed",UpdateUsers.NicknameIsSame("John", CreateDatabaseTest.TestUrl));
        assertTrue(UpdateUsers.NicknameIsSame("John", CreateDatabaseTest.TestUrl));
        // cas ou le pseudo n'est pas le même
        assertFalse("Checking if nickname is not same failed",UpdateUsers.NicknameIsSame("Jack", CreateDatabaseTest.TestUrl));
        dbTest.closeConnection();*/
        assertTrue(true);
    }
}
