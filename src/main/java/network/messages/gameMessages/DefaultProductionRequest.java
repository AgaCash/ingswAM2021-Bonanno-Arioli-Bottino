package network.messages.gameMessages;

import clientModel.cards.LightExtraProd;
import clientModel.resources.LightResource;
import com.google.gson.Gson;
import controller.Controller;
import exceptions.InsufficientResourcesException;
import exceptions.UnusableCardException;
import network.messages.MessageType;
import view.VirtualClient;

import java.util.ArrayList;

public class DefaultProductionRequest extends GameMessage{
    private ArrayList<LightResource> input;
    private LightResource output;
    private LightExtraProd card;
    private LightResource chosenOutput;

    public DefaultProductionRequest(String username, ArrayList<LightResource> input, LightResource output, LightExtraProd card, LightResource chosenOutput){
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
            System.out.println("defaultProdRequest riga 33");
            controller.defaultProduction(input, output, card, chosenOutput);
            update(controller);
        } catch (InsufficientResourcesException | UnusableCardException e) {
            DefaultProductionResponse notify = new DefaultProductionResponse(this.getUsername(), e.getMessage());
            client.getVirtualView().updateDefaultProduction(notify);
        }
    }

    private void update(Controller controller){
        DefaultProductionResponse response = new DefaultProductionResponse(getUsername(),
                                                                            controller.getCurrentPlayer().getPlayerBoard().getWarehouseDepot().convert(),
                                                                            controller.getCurrentPlayer().getPlayerBoard().getStrongbox().convert());
        controller.getViews().forEach((element)-> {element.getVirtualView().updateDefaultProduction(response);});
    }
}
