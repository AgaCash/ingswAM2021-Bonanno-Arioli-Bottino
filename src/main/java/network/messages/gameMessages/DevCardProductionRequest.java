package network.messages.gameMessages;

import com.google.gson.Gson;
import controller.Controller;
import exceptions.InsufficientResourcesException;
import model.cards.ExtraProd;
import model.resources.Resource;
import network.messages.MessageType;
import network.messages.notifies.FailedActionNotify;

import java.io.PrintWriter;

public class DevCardProductionRequest extends GameMessage{
    private int slot;
    private Resource chosenResource;
    private ExtraProd card;

    public DevCardProductionRequest(String username){
        super(username, MessageType.PRODUCTION);
    }

    @Override
    public void executeCommand(Controller controller, PrintWriter out){
        Gson gson = new Gson();
        try {
            controller.devCardProduction(slot, chosenResource, card);
            out.println(gson.toJson(new DevCardProductionResponse(this.getUsername()), MarketResponse.class));
            update();
        } catch (InsufficientResourcesException e){
            out.println(gson.toJson(new FailedActionNotify(this.getUsername(), e.getMessage()), FailedActionNotify.class));
        }
    }

    private void update(){

    }
}
