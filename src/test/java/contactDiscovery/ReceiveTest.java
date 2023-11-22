package contactDiscovery;

import Model.AppData;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

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
    public static Test suite() { return new TestSuite( ReceiveTest.class );}

    //test de AddontactList lorsque la personne n'est pas dans notre contact list
    public void testAddContactList() throws UnknownHostException {
        InetAddress senderAddress = InetAddress.getByName("101.26.81.12");
        assert Library.GetConnectedUserList().isEmpty();
        AppData.addContactList(senderAddress, "Mary");
        System.out.println(Library.GetConnectedUserList());
        assert Library.GetConnectedUserList().size() == 1;
    }

    //test du handleReceived lorsqu'on reçoit un nickname
    public void testReceiveNickname() throws InterruptedException, UnknownHostException {
        InetAddress senderAddress = InetAddress.getByName("100.26.81.12");
        Broadcast.Receive.handleReceived(senderAddress,"MY_NICKNAME_John");
        System.out.println(Library.GetConnectedUserList());
    }

    //test de AddontactList lorsque on a déjà une personne avec ce nom dans notre contact liste
    public void testAddContactList_b() throws UnknownHostException {
        InetAddress senderAddress = InetAddress.getByName("101.26.81.12");
        InetAddress senderAddress_2 = InetAddress.getByName("102.26.81.12");
        AppData.addContactList(senderAddress, "Mary");
        AppData.addContactList(senderAddress_2, "Mary");
        System.out.println(Library.GetConnectedUserList());
    }

    //test pour enlever quelqu'un de notre contact list lorsqu'il n'est plus connecté
    public void testDeletefromContactList() throws UnknownHostException {
        //on ajoute d'abord quelqu'un a la contactlist
        InetAddress senderAddress = InetAddress.getByName("102.26.81.12");
        AppData.addContactList(senderAddress, "Mary");
        System.out.println(Library.GetConnectedUserList());
        //on supprime la personne
        AppData.DeletefromContactList(senderAddress);
        System.out.println(Library.GetConnectedUserList());
    }

}
