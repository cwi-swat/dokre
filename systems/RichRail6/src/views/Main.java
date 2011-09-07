package views;

import helpers.Command;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    Command command = new Command();
                    new InputView(command);
                } catch (Exception e) {
                    System.err.print(e.getMessage());
                }
            }
        });
    }
}
