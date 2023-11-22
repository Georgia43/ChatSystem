package Model;
import java.util.Scanner;

public class InputScanner {
    private static final Scanner scanner = new Scanner(System.in);

    private InputScanner() {
        // constructeur
    }

    public static Scanner getScanner() {
        return scanner;
    }
}


