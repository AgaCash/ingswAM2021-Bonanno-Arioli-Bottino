package network.messages.gameMessages;

import clientController.LightController;
import clientModel.player.LightPlayer;
import network.messages.MessageType;

import java.util.ArrayList;

public class DevCardProductionResponse extends GameMessage{
    private ArrayList<LightPlayer> players;
    private String message;
    private boolean success;

    public DevCardProductionResponse(String username, ArrayList<LightPlayer> players) {
        super(username, MessageType.DEVCARDPRODUCTIONUPDATE);
        this.players = players;
        this.success = true;
    }

    public DevCardProductionResponse(String username, String message){
        super(username, MessageType.DEVCARDPRODUCTIONUPDATE);
        this.message = message;
        this.success = false;
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
