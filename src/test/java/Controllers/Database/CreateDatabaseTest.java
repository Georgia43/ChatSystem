package Controllers.Database;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;

    public class CreateDatabaseTest extends TestCase {
        /**
         * Create the test case
         *
         * @param testName name of the test case
         */
        public CreateDatabaseTest(String testName) {
            super(testName);
        }

        public static Test suite() {
            return new TestSuite(Controllers.Database.CreateDatabaseTest.class);
        }

        public void testConnect () throws UnknownHostException {
            assert CreateDatabase.Connect(CreateDatabase.url);
        }
        public void testTableUsers () throws UnknownHostException, SQLException{
            //erreur bizarre mais marche quand meme
            CreateDatabase.Connect(CreateDatabase.url);
            CreateDatabase.tableUsers();
        }

        public void testTableMessage () throws UnknownHostException, SQLException{
            InetAddress senderAddress = InetAddress.getByName("101.26.81.12");
            CreateDatabase.Connect(CreateDatabase.url);
            CreateDatabase.tableMessages(senderAddress);
        }
    }
