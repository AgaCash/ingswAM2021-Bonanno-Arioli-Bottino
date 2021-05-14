package network.messages.gameMessages;

import com.google.gson.Gson;
import controller.Controller;
import network.messages.MessageType;
import view.*;
import java.util.ArrayList;

public class EndTurnRequest extends GameMessage{

    public EndTurnRequest(String username){
        super(username, MessageType.ENDTURN);
    }

    @Override
    public void executeCommand(Controller controller, ArrayList<VirtualClient> views){
        Gson gson = new Gson();
        controller.endTurn();
       //out.println(gson.toJson(new EndTurnResponse(this.getUsername()), MarketResponse.class));
        update();
    }

    private void update(){

    }
}
