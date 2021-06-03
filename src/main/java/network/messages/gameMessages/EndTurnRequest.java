package network.messages.gameMessages;

import com.google.gson.Gson;
import controller.Controller;
import network.messages.MessageType;
import view.VirtualClient;

public class EndTurnRequest extends GameMessage{
    private String message;

    public EndTurnRequest(String username){
        super(username, MessageType.ENDTURN);
        message = username+" has ended turn ...";
    }

    @Override
    public void executeCommand(Controller controller, VirtualClient client){
        Gson gson = new Gson();
        controller.endTurn(getUsername());
    }
}
