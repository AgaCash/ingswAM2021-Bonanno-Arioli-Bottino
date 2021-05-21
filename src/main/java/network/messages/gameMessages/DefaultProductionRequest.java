package network.messages.gameMessages;

import com.google.gson.Gson;
import controller.Controller;
import exceptions.InsufficientResourcesException;
import exceptions.UnusableCardException;
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

    public DefaultProductionRequest(String username, ArrayList<Resource> input, Resource output, ExtraProd card, Resource chosenOutput){
        super(username, MessageType.PRODUCTION);
        this.input = input;
        this.output = output;
        this.card = card;
        this.chosenOutput = chosenOutput;

    }

    @Override
    public void executeCommand(Controller controller, VirtualClient client){
        Gson gson = new Gson();
        try{
            controller.defaultProduction(input, output, card, chosenOutput);
            update(controller);
        } catch (InsufficientResourcesException | UnusableCardException e) {
            DefaultProductionResponse notify = new DefaultProductionResponse(this.getUsername(), e.getMessage());
            client.getVirtualView().updateDefaultProduction(notify);
        }
    }

    private void update(Controller controller){
        DefaultProductionResponse response = new DefaultProductionResponse(getUsername(),
                                                                            controller.getCurrentPlayer().getPlayerBoard().getWarehouseDepot(),
                                                                            controller.getCurrentPlayer().getPlayerBoard().getStrongbox());
        controller.getViews().forEach((element)-> {element.getVirtualView().updateDefaultProduction(response);});
    }
}
