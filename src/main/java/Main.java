import GUI.GuiStart;
import clientView.CLI;
import clientView.View;
import javafx.application.Application;
import network.server.Server;

public class Main {

    public static void main(String[] args) {
        boolean cli = false;
        boolean help = false;
        boolean isServer = false;
        int serverPort = 0;

        for(int i = 0; i< args.length; i++){
            if(args[i].equalsIgnoreCase("--cli"))
                cli = true;
            if(args[i].equalsIgnoreCase("--help"))
                help = true;
            if(args[i].equalsIgnoreCase("--server")){
                try {
                    isServer = true;
                    serverPort = Integer.parseInt(args[i + 1]);
                }catch(IndexOutOfBoundsException | NumberFormatException e){
                    help = true;
                    break;
                }
            }
        }

        if(help){
            System.out.println("OPTIONS: --cli: runs Master Of Renaissance in Command Line modality\n" +
                               "         --server <port>: runs Master Of Renaissance server at port <port>\n" +
                               "         --help: print this menu");
        }
        else if(isServer){
                Server server = new Server(serverPort);
                server.startServer();
        }
        else if(cli){
            View view = new CLI();
            view.askServerInfo();
        }
        else
            Application.launch(GuiStart.class);
    }
}
