package Controllers;

import Model.AppData;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;

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

    //test de AddcontactList lorsque la personne n'est pas dans notre contact list
    public void testAddContactList() throws UnknownHostException {
        InetAddress senderAddress = InetAddress.getByName("101.26.81.12");
        assert Library.GetConnectedUserList().isEmpty();
        AppData.addContactList(senderAddress, "Mary");
        System.out.println(Library.GetConnectedUserList());
        assert Library.GetConnectedUserList().size() == 1;
       assertEquals("User{username='Mary', id='/101.26.81.12'}", (Library.GetConnectedUserList().get(0).toString()));
       AppData.DeletefromContactList(senderAddress); //on vide la contact list à la fin de chaque test
    }

    //test du handleReceived lorsqu'on reçoit un nickname
    public void testReceiveNickname() throws IOException, SQLException {
        InetAddress senderAddress = InetAddress.getByName("100.26.81.12");
        Broadcast.Receive.handleReceived(senderAddress,"MY_NICKNAME_John");
        System.out.println(Library.GetConnectedUserList());
        assertEquals("User{username='John', id='/100.26.81.12'}", (Library.GetConnectedUserList().get(0).toString()));
        AppData.DeletefromContactList(senderAddress);
    }

    //test de AddcontactList lorsque on a déjà une personne avec ce nom dans notre contact liste
    public void testAddContactList_b() throws UnknownHostException {
        InetAddress senderAddress = InetAddress.getByName("101.26.81.12");
        InetAddress senderAddress_2 = InetAddress.getByName("102.26.81.12");
        AppData.addContactList(senderAddress, "Mary");
        AppData.addContactList(senderAddress_2, "Mary");
        System.out.println(Library.GetConnectedUserList());
        assert Library.GetConnectedUserList().size() == 1; //on vérifie qu'on a bien 1 élément dans la liste et pas 2
        AppData.DeletefromContactList(senderAddress);
    }

    //test pour enlever quelqu'un de notre contact list lorsqu'il n'est plus connecté
    public void testDeletefromContactList() throws UnknownHostException {
        //on ajoute une personne dans la contact list
        InetAddress senderAddress = InetAddress.getByName("101.26.81.12");
        AppData.addContactList(senderAddress, "Mary");
        assert Library.GetConnectedUserList().size() == 1;
        System.out.println(Library.GetConnectedUserList());
        //on supprime la personne
        AppData.DeletefromContactList(senderAddress);
        System.out.println(Library.GetConnectedUserList());
        assertTrue(Library.GetConnectedUserList().isEmpty());
    }



    //test du handleReceived lorsqu'on reçoit DISCONNECTING (lorsqu'un user veut se déconnecter, on l'enlève de notre contactlist)
    public void testReceiveDisconnecting() throws IOException, SQLException {
        InetAddress senderAddress = InetAddress.getByName("100.26.81.12");
        AppData.addContactList(senderAddress, "Mary");
        assert Library.GetConnectedUserList().size() == 1;
        System.out.println(Library.GetConnectedUserList());
        Broadcast.Receive.handleReceived(senderAddress,"DISCONNECTING");
        System.out.println(Library.GetConnectedUserList());
        assert Library.GetConnectedUserList().isEmpty();

    }
}
