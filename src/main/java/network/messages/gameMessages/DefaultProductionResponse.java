package network.messages.gameMessages;

import clientController.LightController;
import clientModel.player.LightPlayer;
import network.messages.MessageType;

import java.util.ArrayList;

public class DefaultProductionResponse extends GameMessage {
    private ArrayList<LightPlayer> players = new ArrayList<>();
    private boolean success;
    private String message;

    public DefaultProductionResponse(String username, ArrayList<LightPlayer> players) {
        super(username, MessageType.DEFPRODUCTIONUPDATE);
        this.players = players;
        this.success = true;
    }

    public DefaultProductionResponse(String username, String message){
        super(username, MessageType.DEFPRODUCTIONUPDATE);
        this.success = false;
        this.message = message;
    }
    @Override
    public void executeCommand(LightController controller){
        if(this.success) {
            controller.updateProduction(getUsername(), players);
            controller.showSuccess(getUsername(), "successful production!");
        }
        else{
            controller.showError(getUsername(), message);
        }
    }

}
