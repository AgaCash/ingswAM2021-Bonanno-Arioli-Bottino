package network.messages.gameMessages;

import com.google.gson.Gson;
import controller.Controller;
import network.messages.MessageType;

import java.io.PrintWriter;

public class EndTurnRequest extends GameMessage{

    public EndTurnRequest(String username){
        super(username, MessageType.ENDTURN);
    }

    @Override
    public void executeCommand(Controller controller, PrintWriter out){
        Gson gson = new Gson();
        controller.endTurn();
        out.println(gson.toJson(new EndTurnResponse(this.getUsername()), MarketResponse.class));
        update();
    }

    private void update(){

    }
}
