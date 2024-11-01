package reseau;

import java.io.PrintWriter;
import java.util.Scanner;

public class EnvoyerMessageClient implements Runnable{
    public PrintWriter out;
    public Scanner scanner;

    
    public EnvoyerMessageClient(PrintWriter out, Scanner scanner) {
        this.out = out;
        this.scanner = scanner;
    }

    public PrintWriter getOut() {
        return out;
    }


    public void setOut(PrintWriter out) {
        this.out = out;
    }


    public Scanner getScanner() {
        return scanner;
    }


    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }


    @Override
    public void run() {
        String string = null;
        while (true){
            string = scanner.nextLine();
            out.println(string);
            out.flush();
        }
    }
}
