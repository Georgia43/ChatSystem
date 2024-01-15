package ChatSystem;

import Model.InputScanner;
import MyView.Welcome;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Logger;

//class to run the application

public class MainClass {
    public static void main(String[] args) throws IOException, InterruptedException {

        final Logger logger = Logger.getLogger("chatsystem");
        Scanner scanner = InputScanner.getScanner();

         Welcome welcome = new Welcome();

    }
}
