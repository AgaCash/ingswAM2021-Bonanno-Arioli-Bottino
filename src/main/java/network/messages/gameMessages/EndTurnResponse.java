package network.messages.gameMessages;

import clientController.LightController;
import clientModel.cards.LightDevelopmentCard;
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
    private int lorenzoPos;
    private boolean isSinglePlayer;
    private boolean gameOver = false;
    private boolean victory;

    public EndTurnResponse(String oldPlayer, String newPlayer, LightStrongbox strongbox){
        super(oldPlayer, MessageType.ENDTURNUPDATE);
        this.newPlayerName = newPlayer;
        this.isSinglePlayer = false;
        this.strongbox = strongbox;
    }

    public EndTurnResponse(String username, LightDevelopmentBoard board, LightFaithTrack track, String message,
                           ArrayList<LightDevelopmentCard> newSlots, LightStrongbox newStrongbox, int lorenzoPos){
        super(username, MessageType.ENDTURNUPDATE);
        this.board = board;
        this.track = track;
        this.message = message;
        this.slots = newSlots;
        this.strongbox = newStrongbox;
        this.lorenzoPos = lorenzoPos;
        this.isSinglePlayer = true;
        this.gameOver = false;
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
                controller.updateFaithTrack(getUsername(), this.track);
                controller.updateCardSlots(getUsername(), this.slots);
                controller.getPlayerBoard().getFaithTrack().setLorenzoPos(lorenzoPos);
                controller.showLorenzoActions(message);
                controller.startTurn();
            }

        }else{
            if(gameOver){
                controller.endMultiPlayerGame(newPlayerName, message);
            }
            else {
                controller.showOthersActions(getUsername() , " has ended the turn");

                if (controller.getUsername().equals(this.newPlayerName)) {
                    controller.showSuccess("IT'S YOUR TURN!");
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
