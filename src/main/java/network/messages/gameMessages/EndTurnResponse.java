package network.messages.gameMessages;

import clientController.LightController;
import network.messages.MessageType;

public class EndTurnResponse extends GameMessage{
    private String newPlayerName ;
    private boolean isSinglePlayer;

    public EndTurnResponse(String oldPlayer, String newPlayer){
        super(oldPlayer, MessageType.ENDTURNUPDATE);
        this.newPlayerName = newPlayer;
    }

    public EndTurnResponse(String username, boolean singlePlayer){
        super(username, MessageType.ENDTURNUPDATE);
        this.isSinglePlayer = singlePlayer;
        System.out.println("TEMPORARY");
    }
    @Override
    public void executeCommand(LightController controller){
        if(this.isSinglePlayer){
            controller.showSuccess("Lorenzo ha fatto qualcosa");
            controller.startTurn();

        }else{
            controller.showSuccess(getUsername()+" ha terminato il turno");
            controller.showSuccess(this.newPlayerName+" ha iniziato il turno");
            if(controller.getUsername().equals(this.newPlayerName))
                controller.startTurn();
        }

    }
}
