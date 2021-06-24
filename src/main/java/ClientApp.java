
import GUI.GuiStart;
import clientView.CLI;
import clientView.View;
import javafx.application.Application;

import java.util.Scanner;

public class ClientApp {
    public static void main(String[] args){
        View view = null;

        Scanner s = new Scanner(System.in);
        System.out.println("1-CLI\n2-GUI");
        switch (s.nextInt()){
            case 1:
                view = new CLI();
                view.askServerInfo();
                break;
            case 2:
                Application.launch(GuiStart.class);
                break;
            default:
                System.out.println("invalid number!");
        }
    }
}
