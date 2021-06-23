package network.messages.gameMessages;

import clientController.LightController;
import clientModel.cards.LightDevelopmentCard;
import clientModel.singleplayer.LightLorenzo;
import clientModel.strongbox.LightStrongbox;
import clientModel.table.LightDevelopmentBoard;
import clientModel.table.LightFaithTrack;
import network.messages.MessageType;

import java.util.ArrayList;

public class EndTurnResponse extends GameMessage{
    private String newPlayerName ;
    private LightDevelopmentBoard board;
    private LightFaithTrack track;
    private ArrayList<LightDevelopmentCard> slots;
    private LightStrongbox strongbox;
    private String message;
    private boolean isSinglePlayer;
    private boolean gameOver = false;
    private boolean victory;
    private LightLorenzo lorenzo;

    public EndTurnResponse(String oldPlayer, String newPlayer, LightStrongbox strongbox){
        super(oldPlayer, MessageType.ENDTURNUPDATE);
        this.newPlayerName = newPlayer;
        this.isSinglePlayer = false;
        this.strongbox = strongbox;
    }

    public EndTurnResponse(String username, LightDevelopmentBoard board, LightFaithTrack track,
                           ArrayList<LightDevelopmentCard> newSlots, LightStrongbox newStrongbox,
                           LightLorenzo lorenzo){
        super(username, MessageType.ENDTURNUPDATE);
        this.board = board;
        this.track = track;
        this.slots = newSlots;
        this.strongbox = newStrongbox;
        this.isSinglePlayer = true;
        this.gameOver = false;
        this.lorenzo = lorenzo;
    }

    public EndTurnResponse(String username, String message){
        super(username, MessageType.ENDTURNUPDATE);
        this.gameOver=true;
        this.isSinglePlayer = true;
        this.message = message;
    }

    public EndTurnResponse(String username, String rank, String winner){
        super(username, MessageType.ENDTURNUPDATE);
        this.gameOver = true;
        this.isSinglePlayer = false;
        this.newPlayerName = winner;
        this.message = rank;
    }
    @Override
    public void executeCommand(LightController controller){
        controller.updateStrongbox(getUsername(), this.strongbox);
        if(this.isSinglePlayer){
            if(gameOver){
                controller.endSinglePlayerGame(this.message);
            }
            else {
                controller.updateDevBoard(board);
                //controller.updateFaithTrack(getUsername(), this.track);
                controller.updateCardSlots(getUsername(), this.slots);
                controller.updateLorenzo(lorenzo);
                controller.startTurn();
            }

        }else{
            if(gameOver){
                controller.endMultiPlayerGame(newPlayerName, message);
            }
            else {
                controller.showOthersActions(getUsername() , " has ended the turn");

                if (controller.getUsername().equals(this.newPlayerName)) {
                    controller.showSuccess(this.newPlayerName, "IT'S YOUR TURN!");
                    controller.startTurn();
                }
                else
                    controller.showOthersActions(this.newPlayerName , " has started the turn");
            }
        }

    }

    public String getNewPlayerName() {
        return newPlayerName;
    }
}
