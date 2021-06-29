package network.messages.gameMessages;

import clientModel.cards.LightLeaderCard;
import clientModel.resources.LightResource;
import controller.Controller;
import exceptions.EmptySlotException;
import exceptions.InsufficientResourcesException;
import exceptions.InvalidActionException;
import exceptions.UnusableCardException;
import network.messages.MessageType;
import view.VirtualClient;

/**
 * Message that implements a Production from DevelopmentCard Request
 */
public class DevCardProductionRequest extends GameMessage{
    private int slot;
    private LightResource chosenResource;
    private LightLeaderCard card;

    /**Request constructor
     * @param username Sender's username
     * @param slot the CardSlot number where DevelopmentCard is on the top of
     * @param chosenResource the free choice Resource will be produced if LeaderCard will be successfully added to production
     * @param card a LightLeaderCard copy of the LeaderCard that Player want to add to the action, null if Player asked for simple action
     */
    public DevCardProductionRequest(String username, int slot, LightResource chosenResource, LightLeaderCard card){
        super(username, MessageType.DEVCARDPRODUCTION);
        this.slot = slot;
        this.chosenResource = chosenResource;
        this.card = card;
    }

    @Override
    public void executeCommand(Controller controller, VirtualClient client){
        try {
            controller.devCardProduction(slot, chosenResource, card);
            update(controller);
        } catch (InsufficientResourcesException | UnusableCardException | InvalidActionException | EmptySlotException e){
            DevCardProductionResponse notify = new DevCardProductionResponse(this.getUsername(), e.getMessage());
            client.getVirtualView().updateDevCardProduction(notify);

        }
    }

    /**Creates the Response instance if action was successful (executeCommand didn't threw Exceptions)
     * @param controller the Controller in the Server
     */
    private void update(Controller controller){
        DevCardProductionResponse response = new DevCardProductionResponse(getUsername(),
                controller.getLightPlayers());
        controller.getViews().forEach((element)-> element.getVirtualView().updateDevCardProduction(response));
    }
}
