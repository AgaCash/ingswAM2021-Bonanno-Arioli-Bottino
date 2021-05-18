package network.messages.gameMessages;

import controller.Controller;
import exceptions.NoSuchUsernameException;
import model.cards.LeaderCard;
import network.messages.MessageType;
import view.VirtualClient;

import java.util.ArrayList;

public class StartGameRequest extends GameMessage{

    public StartGameRequest(String username){
        super(username, MessageType.STARTGAME);
    }

    @Override
    public void executeCommand(Controller controller, VirtualClient view) {
        try {
            switch (controller.getPlayer(getUsername()).getStartingTurn()) {
                case 0: {
                    ArrayList<LeaderCard> quartet = controller.getLeaderCards();
                    view.getVirtualView().update(new StartGameResponse(getUsername(), quartet, 0, false));}
                case 1: {
                    ArrayList<LeaderCard> quartet = controller.getLeaderCards();
                    view.getVirtualView().update(new StartGameResponse(getUsername(), quartet, 1, false));}
                case 2: {
                    ArrayList<LeaderCard> quartet = controller.getLeaderCards();
                    view.getVirtualView().update(new StartGameResponse(getUsername(), quartet, 1, true));}
                case 3: {
                    ArrayList<LeaderCard> quartet = controller.getLeaderCards();
                    view.getVirtualView().update(new StartGameResponse(getUsername(), quartet, 2, true));}
            }
        } catch(NoSuchUsernameException e){
            controller.handleError(e.getMessage());
        }
    }
}
