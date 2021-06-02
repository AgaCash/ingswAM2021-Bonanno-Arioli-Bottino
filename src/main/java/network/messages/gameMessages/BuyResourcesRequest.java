package network.messages.gameMessages;

import clientModel.cards.LightLeaderCard;
import clientModel.table.LightMarketBoard;
import clientModel.warehouse.LightWarehouseDepot;
import controller.Controller;
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
        }catch(UnusableCardException e){
            BuyResourcesResponse notify =  new BuyResourcesResponse(this.getUsername(), e.getMessage());
            client.getVirtualView().updateBuyResources(notify);
        }
    }

    public void update(Controller controller){
        //todo aggiornamento faithtrack
        LightWarehouseDepot warehouse = controller.getCurrentPlayer().getPlayerBoard().getWarehouseDepot().convert();
        LightMarketBoard market = controller.getMarketBoard().convert();

        BuyResourcesResponse response = new BuyResourcesResponse(getUsername(),
                                                                warehouse,
                                                                market,
                                                                controller.getThrewResources());
        controller.getViews().forEach((element)-> { element.getVirtualView().updateBuyResources(response);});
    }


}
