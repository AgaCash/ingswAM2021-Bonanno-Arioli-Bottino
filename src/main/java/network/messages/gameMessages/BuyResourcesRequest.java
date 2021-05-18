package network.messages.gameMessages;

import controller.Controller;
import exceptions.UnusableCardException;
import model.cards.WhiteConverter;
import network.messages.MessageType;
import view.VirtualClient;

public class BuyResourcesRequest extends GameMessage{
    private boolean line;
    private int num;
    private WhiteConverter card;

    public BuyResourcesRequest(String username){
        super(username, MessageType.MARKET);
    }

    @Override
    public void executeCommand(Controller controller, VirtualClient client){

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
                                                                controller.getMarketBoard(),
                                                                controller.getThrewResources());
        controller.getViews().forEach((element)-> { element.getVirtualView().update(response);});
        //todo passa tutti i faithtrack

    }

}
