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
        Map<String, String> list = Library.GetConnectedUserList();

        Broadcast.Receive receiverThread = new Broadcast.Receive(list);
        receiverThread.start();

        Library.sendMessage("user1");
        Library.sendMessage("user2");
        Library.sendMessage("user3");

        Thread.sleep(100);
        //receiverThread.join();
        System.out.println(list);
    }


}
