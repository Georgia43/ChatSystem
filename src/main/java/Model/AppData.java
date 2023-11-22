package Model;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

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

    public static void DeletefromContactList(InetAddress sender){
        contactList.remove(sender);

    }

    public static void CheckUnicityNickname(String CurrentNickname) throws InterruptedException {
        Scanner scan = InputScanner.getScanner();
        boolean valid_name = true;
        do {
            valid_name = true;
            for (Map.Entry<InetAddress, String> pers : AppData.getContactList().entrySet()) {
                String nickname = pers.getValue();
                if (nickname.equals(CurrentNickname)) {
                    valid_name = false;
                    break;
                }
            }
            if(!valid_name) {
                System.out.println("nickname taken. Choose a new one : ");
                CurrentNickname = scan.nextLine();
            }
        } while (!valid_name);

        currentUser.setNickname(CurrentNickname);
        //scan.close();
    }

    public static String getNicknameCurrentUser() {return currentUser.getNickname();}

}






