package Model;
import java.util.Scanner;

public class InputScanner {
    private static final Scanner scanner = new Scanner(System.in);

    private InputScanner() {
        // Private constructor to prevent instantiation
    }

    public static Scanner getScanner() {
        return scanner;
    }
}


