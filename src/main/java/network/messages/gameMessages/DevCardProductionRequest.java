package network.messages.gameMessages;

import com.google.gson.Gson;
import controller.Controller;
import exceptions.InsufficientResourcesException;
import exceptions.UnusableCardException;
import model.cards.ExtraProd;
import model.resources.Resource;
import network.messages.MessageType;
import view.VirtualClient;

public class DevCardProductionRequest extends GameMessage{
    private int slot;
    private Resource chosenResource;
    private ExtraProd card;

    public DevCardProductionRequest(String username, int slot, Resource chosenResource, ExtraProd card){
        super(username, MessageType.PRODUCTION);
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
        } catch (InsufficientResourcesException | UnusableCardException e){
            DevCardProductionResponse notify = new DevCardProductionResponse(this.getUsername(), e.getMessage());
            client.getVirtualView().updateDevCardProduction(notify);

        }
    }

    private void update(Controller controller){
        DevCardProductionResponse response = new DevCardProductionResponse(getUsername(),
                controller.getCurrentPlayer().getPlayerBoard().getWarehouseDepot(),
                controller.getCurrentPlayer().getPlayerBoard().getStrongbox());
        controller.getViews().forEach((element)-> { element.getVirtualView().updateDevCardProduction(response);});
    }
}
