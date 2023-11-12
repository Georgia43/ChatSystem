package Model;
import java.util.HashMap;
import java.util.Map;

public class contactList {
    private static Map<String, String> contactList = new HashMap<>();

    private contactList(){
        //constructeur

    }

    public static Map<String,String> getContactList(){
        return contactList;
    }
}
