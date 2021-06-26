package network.messages.gameMessages;

import clientController.LightController;
import clientModel.player.LightPlayer;
import network.messages.MessageType;

import java.util.ArrayList;

public class LeaderCardThrowResponse extends GameMessage{
    private ArrayList<LightPlayer> players;
    private boolean success;
    private String message;

    public LeaderCardThrowResponse(String username, ArrayList<LightPlayer> players) {
        super(username, MessageType.LEADERCARDTHROWUPDATE);
        this.success = true;
        this.players = players;
    }

    public LeaderCardThrowResponse(String username, String message){
        super(username, MessageType.LEADERCARDUPDATE);
        this.message = message;
        this.success = false;
    }

    @Override
    public void executeCommand(LightController controller){
        if(this.success){
            controller.updateLeaderCardThrow(getUsername(), players);
            controller.showSuccess(getUsername(), "successfully throwing!");
        }
        else{
            controller.showError(getUsername(), message);
        }
    }
}
