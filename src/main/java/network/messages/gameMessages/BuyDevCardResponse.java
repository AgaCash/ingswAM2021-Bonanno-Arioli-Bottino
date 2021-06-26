package network.messages.gameMessages;

import clientController.LightController;
import clientModel.player.LightPlayer;
import clientModel.table.LightDevelopmentBoard;
import network.messages.MessageType;

import java.util.ArrayList;

public class BuyDevCardResponse extends GameMessage{
    private ArrayList<LightPlayer> players = new ArrayList<>();
    private LightDevelopmentBoard board;
    private boolean success;
    private String message;

    public BuyDevCardResponse(String username, ArrayList<LightPlayer> players, LightDevelopmentBoard board) {
        super(username, MessageType.BUYDEVCARDSUPDATE);
        this.players = players;
        this.board = board;
        this.success= true;
    }

    public BuyDevCardResponse(String username, String message){
        super(username, MessageType.MARKET);
        this.message = message;
        this.success = false;
    }
    @Override
    public void executeCommand(LightController controller){
        if(this.success) {
            controller.updateBuyDevCard(getUsername(), players);
            controller.updateDevBoard(board);
            controller.showSuccess(getUsername(), "successful purchase!");
        }else{
            controller.showError(getUsername(), message);
        }
    }


}
