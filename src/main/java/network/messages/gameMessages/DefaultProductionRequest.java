package network.messages.gameMessages;

import com.google.gson.Gson;
import controller.Controller;
import exceptions.InsufficientResourcesException;
import model.cards.ExtraProd;
import model.resources.Resource;
import network.messages.MessageType;
import network.messages.notifies.FailedActionNotify;

import java.io.PrintWriter;
import java.util.ArrayList;

public class DefaultProductionRequest extends GameMessage{
    private ArrayList<Resource> input;
    private Resource output;
    private ExtraProd card;
    private Resource chosenOutput;

    public DefaultProductionRequest(String username){
        super(username, MessageType.PRODUCTION);
    }

    @Override
    public void executeCommand(Controller controller, PrintWriter out){
        Gson gson = new Gson();
        try{
            controller.defaultProduction(input, output, card, chosenOutput);
            out.println(gson.toJson(new DefaultProductionResponse(this.getUsername()), MarketResponse.class));
            update();
        } catch (InsufficientResourcesException e) {
            out.println(gson.toJson(new FailedActionNotify(this.getUsername(), e.getMessage()), FailedActionNotify.class));

        }
    }

    private void update(){

    }
}
