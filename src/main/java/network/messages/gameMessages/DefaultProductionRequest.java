package network.messages.gameMessages;

import com.google.gson.Gson;
import controller.Controller;
import exceptions.InsufficientResourcesException;
import model.cards.ExtraProd;
import model.resources.Resource;
import network.messages.MessageType;
import view.*;

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
    public void executeCommand(Controller controller, ArrayList<VirtualClient> views){
        Gson gson = new Gson();
        try{
            controller.defaultProduction(input, output, card, chosenOutput);
           //out.println(gson.toJson(new DefaultProductionResponse(this.getUsername()), MarketResponse.class));
            update(controller, views);
        } catch (InsufficientResourcesException e) {
           //out.println(gson.toJson(new FailedActionNotify(this.getUsername(), e.getMessage()), FailedActionNotify.class));

        }
    }

    private void update(Controller controller, ArrayList<VirtualClient> views){
        DefaultProductionResponse response = new DefaultProductionResponse(getUsername(),
                                                                            controller.getCurrentPlayer().getWarehouseDepot(),
                                                                            controller.getCurrentPlayer().getStrongbox());
        //views.forEach((element)-> { element.updateBuyDevCard(response);});
    }
}
