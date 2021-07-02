package network.messages.gameMessages;

import controller.Controller;
import network.messages.MessageType;
import view.VirtualClient;

/**
 * Message that implements the calls the Controller end turn request
 */
public class EndTurnRequest extends GameMessage{
    private String message;

    /**Request constructor
     * @param username the Player's username who ended the turn
     */
    public EndTurnRequest(String username){
        super(username, MessageType.ENDTURN);
        message = username+" has ended turn ...";
    }

    @Override
    public void executeCommand(Controller controller, VirtualClient client){
        controller.endTurn(getUsername());
    }
}
