import clientView.CLI;
import clientView.View;

import java.util.Scanner;

public class ClientApp4 {
    public static void main(String[] args){
        View view = null;

        Scanner s = new Scanner(System.in);
        System.out.println("1-CLI\n2-GUI");
        switch (s.nextInt()){
            case 1:
                view = new CLI();
                break;
            case 2:
                //GUI
                break;
            default:
                System.out.println("numero non vaildo");
        }
        //per ora lo metto fuori così sarà sempre cli
        // |  |  |
        // V  V  V
        view = new CLI();
        // /\ /\ /\
        // || || ||

        view.askServerInfo();
    }
}