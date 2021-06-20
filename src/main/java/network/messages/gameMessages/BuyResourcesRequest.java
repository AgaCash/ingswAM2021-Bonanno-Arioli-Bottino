package network.messages.gameMessages;

import clientModel.cards.LightLeaderCard;
import clientModel.player.LightPlayer;
import controller.Controller;
import exceptions.InvalidActionException;
import exceptions.UnusableCardException;
import model.player.Player;
import network.messages.MessageType;
import view.VirtualClient;

import java.util.ArrayList;

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
        BuyResourcesResponse response;
        if(!controller.isSinglePlayer()) {
            ArrayList<LightPlayer> players = new ArrayList<>();
            for (Player p : controller.getPlayers())
                players.add(p.convert());
            response = new BuyResourcesResponse(getUsername(),
                    controller.getCurrentPlayer().getPlayerBoard().getWarehouseDepot().convert(),
                    controller.getMarketBoard().convert(),
                    controller.getThrewResources(),
                    players);
            controller.getViews().forEach((element) -> element.getVirtualView().updateBuyResources(response));
        }
        else{
            response = new BuyResourcesResponse(getUsername(),
                    controller.getCurrentPlayer().getPlayerBoard().getWarehouseDepot().convert(),
                    controller.getMarketBoard().convert(),
                    controller.getCurrentPlayer().getFaithTrack().getFaithBox().getPosition(),
                    controller.getThrewResources());
            controller.getViews().get(0).getVirtualView().updateBuyResources(response);
        }

    }


}
