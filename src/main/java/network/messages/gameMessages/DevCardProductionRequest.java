package network.messages.gameMessages;

import clientModel.cards.LightLeaderCard;
import clientModel.resources.LightResource;
import com.google.gson.Gson;
import controller.Controller;
import exceptions.InsufficientResourcesException;
import exceptions.InvalidActionException;
import exceptions.UnusableCardException;
import network.messages.MessageType;
import view.VirtualClient;

public class DevCardProductionRequest extends GameMessage{
    private int slot;
    private LightResource chosenResource;
    private LightLeaderCard card;

    public DevCardProductionRequest(String username, int slot, LightResource chosenResource, LightLeaderCard card){
        super(username, MessageType.DEVCARDPRODUCTION);
        this.slot = slot;
        this.chosenResource = chosenResource;
        this.card = card;
    }

    @Override
    public void executeCommand(Controller controller, VirtualClient client){
        Gson gson = new Gson();
        try {
            controller.devCardProduction(slot, chosenResource, card);
            update(controller);
        } catch (InsufficientResourcesException | UnusableCardException | InvalidActionException e){
            DevCardProductionResponse notify = new DevCardProductionResponse(this.getUsername(), e.getMessage());
            client.getVirtualView().updateDevCardProduction(notify);

        }
    }

    private void update(Controller controller){
        DevCardProductionResponse response = new DevCardProductionResponse(getUsername(),
                controller.getCurrentPlayer().getPlayerBoard().getWarehouseDepot().convert(),
                controller.getCurrentPlayer().getPlayerBoard().getStrongbox().convert(),
                controller.getCurrentPlayer().getPlayerBoard().getFaithTrack().getFaithBox().getPosition());
        controller.getViews().forEach((element)-> { element.getVirtualView().updateDevCardProduction(response);});
    }
}
