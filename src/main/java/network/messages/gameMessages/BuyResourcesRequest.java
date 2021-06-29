package network.messages.gameMessages;

import clientModel.cards.LightLeaderCard;
import controller.Controller;
import exceptions.InvalidActionException;
import exceptions.UnusableCardException;
import network.messages.MessageType;
import view.VirtualClient;

/**
 * Message class that implements a Resource purchase at MarketBoard request.
 */
public class BuyResourcesRequest extends GameMessage{
    private boolean line;
    private int num;
    private LightLeaderCard card;

    /**Request constructor
     * @param username the Sender username
     * @param line true if it's a MarketBoard line purchase, false if it's a column purchase
     * @param num the number of line/column
     * @param card a LightLeaderCard copy of the LeaderCard that Player want to add to the action, null if Player asked for simple action
     */
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

    /**Creates the Response instance if action was successful (executeCommand didn't threw Exceptions)
     * @param controller the Controller in the Server
     */
    public void update(Controller controller){
        BuyResourcesResponse response = new BuyResourcesResponse(getUsername(),
                controller.getLightPlayers(),
                controller.getThrewResources(),
                controller.getMarketBoard().convert());
            controller.getViews().forEach((element) -> element.getVirtualView().updateBuyResources(response));
    }


}
