package network.messages.gameMessages;

import controller.Controller;
import network.messages.Message;
import network.messages.MessageType;
import view.View;

import view.View;
import view.VirtualView;

import java.util.ArrayList;

public abstract class GameMessage extends Message {
    public GameMessage(String username, MessageType messageType) {
        super(username, messageType);
    }

    public void executeCommand(Controller controller, View view){
        //System.out.println("default executeCommand CONTROLLER");
        //TODO:
        //  TO_PARLARE:
        //      L'IDEA E' CHE ANCHE IL CLIENT FUNZIONI A EXECUTECOMMAND, SOLO CHE
        //      IL CONTROLLER DEL CLIENT FARA' COSE DIVERSE
        //      QUINDI --> BISOGNA CREARE 2 TIPI DI EXECUTE COMMAND (SERVER E CLIENT)
        //          SERVER -> LISTA DI TUTTE LE VIRTUALVIEWS
        //          CLIENT -> LUNICA E VERA VIEW
        //              VEDI SOTTO
    }

    public void executeCommand(Controller controller, ArrayList<VirtualView> views){
        //QUESTO SARA' ESEGUITO DAL SERVER
    }




}
