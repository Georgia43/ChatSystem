package Controllers;

import Model.AppData;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


import java.net.InetAddress;
import java.net.UnknownHostException;
public class ClientTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ClientTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(ClientTest.class);
    }

    public void testGetIpByNickname () throws UnknownHostException {
        InetAddress senderAddress = InetAddress.getByName("101.26.81.12");
        AppData.addContactList(senderAddress, "Mary");
        InetAddress res = AppData.getIpByNickname("Mary");
        assertEquals("101.26.81.12",res.getHostAddress());
    }

}

