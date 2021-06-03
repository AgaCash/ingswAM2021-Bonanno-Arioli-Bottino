package network.messages.gameMessages;

import clientModel.cards.LightLeaderCard;

import controller.Controller;
import exceptions.InvalidActionException;

import exceptions.UnusableCardException;
import network.messages.MessageType;
import view.VirtualClient;

public class BuyResourcesRequest extends GameMessage{
    private boolean line;
    private int num;
    private LightLeaderCard card;

    public BuyResourcesRequest(String username, boolean line, int num, LightLeaderCard card){
        super(username, MessageType.MARKET);
        this.line = line;
        this.num = num;
        this.card = card;
    }

    @Override
    public void executeCommand(Controller controller, VirtualClient client){
        try {
            controller.buyResources(line, num, card);
            update(controller);
        }catch(UnusableCardException | InvalidActionException e){
            BuyResourcesResponse notify =  new BuyResourcesResponse(this.getUsername(), e.getMessage());
            client.getVirtualView().updateBuyResources(notify);
        }
    }

    public void update(Controller controller){
        BuyResourcesResponse response = new BuyResourcesResponse(getUsername(),
                                                                controller.getCurrentPlayer().getPlayerBoard().getWarehouseDepot().convert(),
                                                                controller.getMarketBoard().convert(),
                                                                controller.getCurrentPlayer().getFaithTrack().convert(),
                                                                controller.getThrewResources());
        controller.getViews().forEach((element)-> { element.getVirtualView().updateBuyResources(response);});
    }


}
