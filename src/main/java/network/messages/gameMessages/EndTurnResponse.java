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
    private boolean isSinglePlayer;

    public EndTurnResponse(String oldPlayer, String newPlayer){
        super(oldPlayer, MessageType.ENDTURNUPDATE);
        this.newPlayerName = newPlayer;
        this.isSinglePlayer = false;
    }

    public EndTurnResponse(String username, LightDevelopmentBoard board, LightFaithTrack track, String message,
                           ArrayList<LightDevelopmentCard> newSlots, LightStrongbox newStrongbox){
        super(username, MessageType.ENDTURNUPDATE);
        this.board = board;
        this.track = track;
        this.message = message;
        this.slots = newSlots;
        this.strongbox = newStrongbox;
        this.isSinglePlayer = true;
    }
    @Override
    public void executeCommand(LightController controller){
        if(this.isSinglePlayer){
            controller.updateDevBoard(board);
            //controller.updateFaithTrack(this.track);
            controller.updateStrongbox(getUsername(), this.strongbox);
            controller.updateCardSlots(getUsername(), this.slots);
            controller.showSuccess(message);
            controller.startTurn();

        }else{
            controller.showSuccess(getUsername()+" ha terminato il turno");
            controller.showSuccess(this.newPlayerName+" ha iniziato il turno");
            if(controller.getUsername().equals(this.newPlayerName))
                controller.startTurn();
        }

    }
}
