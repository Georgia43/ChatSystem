package Controllers.Database;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

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

        // Méthode pour vérifier si une table existe dans la base de données
        private boolean tableExists(CreateDatabase createDatabase, String Table) throws SQLException {
            DatabaseMetaData metaData = createDatabase.connection.getMetaData();
            ResultSet tables = metaData.getTables(null, null, Table, null);
            return tables.next();
        }
        public static String TestUrl = "jdbc:sqlite:BDDTest.db";
        public void testConnect () throws UnknownHostException { //find a way to make an assert
            CreateDatabase dbTest = new CreateDatabase(TestUrl);
        }
        public void testTableUsers () throws UnknownHostException, SQLException{
            //erreur bizarre mais marche quand meme
            CreateDatabase dbTest = new CreateDatabase(TestUrl);
            dbTest.tableUsers();
            assertTrue(tableExists(dbTest,"Users"));
        }

        public void testTableMessage () throws UnknownHostException, SQLException{
            InetAddress senderAddress = InetAddress.getByName("101.26.81.12");
            CreateDatabase dbTest = new CreateDatabase(TestUrl);
            dbTest.tableMessages(senderAddress);
            assertTrue(tableExists(dbTest,"Messages_101_26_81_12"));
        }
    }
