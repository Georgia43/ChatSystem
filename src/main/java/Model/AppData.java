package Model;
import java.util.HashMap;
import java.util.Map;

public class AppData {
    private static Map<String, String> contactList = new HashMap<>();
    private static User currentUser = new User();

    private AppData(){
        //constructeur

    }

    /*MÉTHODES*/

    public static Map<String,String> getContactList(){
        return contactList;
    }

    public static void addContactList(String sender, String received) {
        if (!contactList.containsValue(received)) {
            contactList.put(sender, received);
        }
    }

    public static String getNicknameCurrentUser() {return currentUser.getNickname();}
}




