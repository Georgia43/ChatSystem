package contactDiscoveryTest;

import contactDiscovery.Broadcast;
import contactDiscovery.Library;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.example.AppTest;

import java.util.ArrayList;
import java.util.Map;

import static junit.framework.Assert.*;

public class LibraryTest extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public LibraryTest( String testName )
    {
        super( testName );
    }
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    public void testLibrary() throws InterruptedException {
        //ArrayList<Model.User> list = Library.GetConnectedUserList();
        Broadcast.Receive receiverThread = new Broadcast.Receive();
        receiverThread.start();
        /*PROBLEME, ON REINITIALISE LA LISTE A CHAQUE FOIS QU ON ENVOIE UN MESSAGE*/
        Library.sendMessage("user1");
        Library.sendMessage("user2");
        Library.sendMessage("user3");
        Library.sendMessage("user4");
        Library.sendMessage("user5");
        Library.sendMessage("user6");

      //  Thread.sleep(200);
        receiverThread.join(300);
        //receiverThread.join();
        System.out.println(Library.GetConnectedUserList());
    }


}
