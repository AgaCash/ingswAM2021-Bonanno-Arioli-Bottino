package network.messages.gameMessages;

import com.google.gson.Gson;
import controller.Controller;
import network.messages.MessageType;
import view.VirtualClient;

public class EndTurnRequest extends GameMessage{

    public EndTurnRequest(String username){
        super(username, MessageType.ENDTURN);
    }

    @Override
    public void executeCommand(Controller controller, VirtualClient client){
        Gson gson = new Gson();
        controller.endTurn();
       //out.println(gson.toJson(new EndTurnResponse(this.getUsername()), MarketResponse.class));
        //TODO: aho che famo sti turni?
        update();
    }

    private void update(){

    }
}
