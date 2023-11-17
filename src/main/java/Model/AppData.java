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

    public static void CheckUnicityNickname(String CurrentNickname) {
        Map<InetAddress, String> contactList = getContactList();
        Scanner scanner = new Scanner(System.in);
        for (Map.Entry<InetAddress, String> pers : AppData.getContactList().entrySet()) {
            String nickname = pers.getValue();
            while (nickname.equals(CurrentNickname)) {
                System.out.println("nickname taken. Choose a new one : ");
                CurrentNickname = scanner.nextLine();
            }

            currentUser.setNickname(CurrentNickname);

        }
        scanner.close();
    }

    public static String getNicknameCurrentUser() {return currentUser.getNickname();}
}




