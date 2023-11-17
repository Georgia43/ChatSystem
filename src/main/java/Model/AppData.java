package Model;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

public class AppData {
    private static Map<InetAddress, String> contactList = new HashMap<>();
    private static User currentUser = new User();

    private AppData(){
        //constructeur

    }

    /*MÉTHODES*/

    public static Map<InetAddress,String> getContactList(){
        return contactList;
    }

    public static void addContactList(InetAddress sender, String received) {
        if (!contactList.containsValue(received)) {
            contactList.put(sender, received);
        }
    }

    public static String getNicknameCurrentUser() {return currentUser.getNickname();}
}




