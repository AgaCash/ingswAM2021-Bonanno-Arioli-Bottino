package network.messages.gameMessages;

import clientModel.cards.LightLeaderCard;
import clientModel.resources.LightResource;
import controller.Controller;
import exceptions.InsufficientResourcesException;
import exceptions.InvalidActionException;
import exceptions.UnusableCardException;
import network.messages.MessageType;
import view.VirtualClient;

import java.util.ArrayList;

/**
 * Message that implements a Default Production Request
 */
public class DefaultProductionRequest extends GameMessage{
    private ArrayList<LightResource> input;
    private LightResource output;
    private LightLeaderCard card;
    private LightResource chosenOutput;

    /**Request constructor
     * @param username the Sender username
     * @param input the Resource couple that will be used as production input
     * @param output the Resource will be produced
     * @param card a LightLeaderCard copy of the LeaderCard that Player want to add to the action, null if Player asked for simple action
     * @param chosenOutput the free choice Resource will be produced if LeaderCard will be successfully added to production
     */
    public DefaultProductionRequest(String username, ArrayList<LightResource> input, LightResource output, LightLeaderCard card, LightResource chosenOutput){
        super(username, MessageType.DEFPRODUCTION);
        this.input = input;
        this.output = output;
        this.card = card;
        this.chosenOutput = chosenOutput;

    }

    @Override
    public void executeCommand(Controller controller, VirtualClient client){
        try{
            controller.defaultProduction(input, output, card, chosenOutput);
            update(controller);
        } catch (InsufficientResourcesException | UnusableCardException | InvalidActionException e) {
            DefaultProductionResponse notify = new DefaultProductionResponse(this.getUsername(), e.getMessage());
            client.getVirtualView().updateDefaultProduction(notify);
        }
    }

    /**Creates the Response instance if action was successful (executeCommand didn't threw Exceptions)
     * @param controller the Controller in the Server
     */
    private void update(Controller controller){
        DefaultProductionResponse response = new DefaultProductionResponse(getUsername(),
                                                                           controller.getLightPlayers());
        controller.getViews().forEach((element)-> element.getVirtualView().updateDefaultProduction(response));
    }
}
