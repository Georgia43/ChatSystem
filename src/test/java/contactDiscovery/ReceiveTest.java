package contactDiscovery;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.example.AppTest;

public class ReceiveTest extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ReceiveTest(String testName )
    {
        super( testName );
    }
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    public void testReceiveFirstMessage() throws InterruptedException { //test du handleReceived lorsqu'on reçoit FIRST_MESSAGE
        Broadcast.Receive receiverThread = new Broadcast.Receive();
        receiverThread.start();
        Library.sendFirstMessage();
        handleReceived
      //  Thread.sleep(200);
        receiverThread.join(300);
        //receiverThread.join();
        System.out.println(Library.GetConnectedUserList());
    }
    //sendnickname
    public void testSendNickname()
    //addcontactlist


}
