package network.messages.gameMessages;

import com.google.gson.Gson;
import controller.Controller;
import exceptions.UnusableCardException;
import model.cards.WhiteConverter;
import model.resources.Resource;
import network.messages.MessageType;
import view.*;

import java.util.ArrayList;

public class BuyResourcesRequest extends GameMessage{
    private boolean line;
    private int num;
    private WhiteConverter card;

    public BuyResourcesRequest(String username){
        super(username, MessageType.MARKET);
    }

    @Override
    public void executeCommand(Controller controller, VirtualClient client){
        Gson gson = new Gson();
        try {
            controller.buyResources(line, num, card);
            controller.getThrewResources();
            update(controller);
        }catch(UnusableCardException e){
            FailedActionNotify notify =  new FailedActionNotify(this.getUsername(), e.getMessage());
            client.getVirtualView().update(notify);
        }
    }

    public void update(Controller controller){
        BuyResourcesResponse response = new BuyResourcesResponse(getUsername(),
                                                                controller.getCurrentPlayer().getPlayerBoard().getWarehouseDepot(),
                                                                controller.getMarketBoard());
        controller.getViews().forEach((element)-> { element.getVirtualView().update(response);});
        ArrayList<Resource> threwResources = controller.getThrewResources();
        for(Resource res: threwResources) {
            ThrewResourcesNotify threwNotify = new ThrewResourcesNotify(getUsername(),
                    "Resource " + res + " couldn't be added: warehouse is full!");
            controller.getViews().forEach((element)-> { element.getVirtualView().update(threwNotify);});
        }
    }

}
