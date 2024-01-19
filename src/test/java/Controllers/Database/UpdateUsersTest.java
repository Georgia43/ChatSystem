package Controllers.Database;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.sql.PreparedStatement;



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

    // pour ne pas avoir de problème si l'on effectue les tests plusieurs fois
    private void deleteUser(InetAddress address) throws SQLException {
        String deleteQuery = "DELETE FROM Users WHERE ipAddress = ?";
        try {
            CreateTables dbTest = new CreateTables(CreateTablesTest.TestUrl);
            PreparedStatement preparedStatement = dbTest.connection.prepareStatement(deleteQuery);
            preparedStatement.setString(1, address.getHostAddress());
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }}

    // test de verification que l'utilisateur existe dans la table
    public void testUserExists() throws UnknownHostException, SQLException {
        InetAddress senderAddress = InetAddress.getByName("101.26.81.12");
        InetAddress senderAddress2 = InetAddress.getByName("101.26.81.13");
        String Nickname = "John";
        CreateTables dbTest = new CreateTables(CreateTablesTest.TestUrl);
        dbTest.tableUsers();
        UpdateUsers.addUser(senderAddress, Nickname, CreateTablesTest.TestUrl);
        // cas ou l'utilisateur existe
        assertTrue("UserExists failed when the user exists", UpdateUsers.userExists(senderAddress, CreateTablesTest.TestUrl));
        // cas ou l'utilisateur n'existe pas
        assertFalse("UserExists failed when the user doesn't exists",UpdateUsers.userExists(senderAddress2, CreateTablesTest.TestUrl));
        deleteUser(senderAddress);
        dbTest.closeConnection();
    }
    // test de l'ajout d'un utilisateur dans la table Users
   public void testAddUsers () throws UnknownHostException, SQLException {
        InetAddress senderAddress = InetAddress.getByName("101.26.81.12");
        String Nickname = "Mary";
        CreateTables dbTest = new CreateTables(CreateTablesTest.TestUrl);
        dbTest.tableUsers(); // au cas ou la table, n'a pas été créée avant
        assertTrue("Adding user failed", UpdateUsers.addUser(senderAddress, Nickname, CreateTablesTest.TestUrl));
        // on utilise userExists pour vérifier que la personne est bien dans la table
        assertTrue(UpdateUsers.userExists(senderAddress,CreateTablesTest.TestUrl));
        deleteUser(senderAddress);
        dbTest.closeConnection();
    }
    // test si le nickname est le même dans la database
    public void testNicknameIsSame() throws UnknownHostException, SQLException {
        InetAddress senderAddress = InetAddress.getByName("101.26.81.13");
        String Nickname = "John";
        CreateTables dbTest = new CreateTables(CreateTablesTest.TestUrl);
        dbTest.tableUsers();
        UpdateUsers.addUser(senderAddress, Nickname, CreateTablesTest.TestUrl);
        // cas ou le pseudo est le même
        assertTrue("Checking if nickname is same failed",UpdateUsers.NicknameIsSame("John", CreateTablesTest.TestUrl));
        // cas ou le pseudo n'est pas le même
        assertFalse("Checking if nickname is not same failed",UpdateUsers.NicknameIsSame("Jack", CreateTablesTest.TestUrl));
        deleteUser(senderAddress);
        dbTest.closeConnection();
    }
    // test du changement de nickname dans la database
    public void testChangeNicknameDB() throws UnknownHostException, SQLException {
        InetAddress senderAddress = InetAddress.getByName("101.26.81.13");
        String newNickname = "Jack";
        String oldNickname = "John";
        CreateTables dbTest = new CreateTables(CreateTablesTest.TestUrl);
        dbTest.tableUsers();
        UpdateUsers.addUser(senderAddress, oldNickname, CreateTablesTest.TestUrl);
        // changer le pseudo
        assertTrue("Change nickname failed.",UpdateUsers.changeNicknameDB(senderAddress,newNickname, CreateTablesTest.TestUrl));
        // vérifier qu'il a bien été changé
        assertTrue("Nickname was not changed", UpdateUsers.NicknameIsSame(newNickname, CreateTablesTest.TestUrl));
        assertFalse("Old nickname is still present", UpdateUsers.NicknameIsSame(oldNickname, CreateTablesTest.TestUrl));
        deleteUser(senderAddress);
        dbTest.closeConnection();
    }

}
