package network.messages.gameMessages;

import clientModel.cards.LightLeaderCard;
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
            controller.setOrder();
            System.out.println("USERNAME REQUEST: "+controller.getPlayer(getUsername()));
            switch (controller.getPlayer(getUsername()).getStartingTurn()) {
                case 0: {
                    ArrayList<LightLeaderCard> quartet = getQuartet(controller);
                    view.getVirtualView().updateStartGame(new StartGameResponse(getUsername(), quartet, 0, false));
                    break;}
                case 1: {
                    ArrayList<LightLeaderCard> quartet = getQuartet(controller);
                    view.getVirtualView().updateStartGame(new StartGameResponse(getUsername(), quartet, 1, false));
                    break;}
                case 2: {
                    ArrayList<LightLeaderCard> quartet = getQuartet(controller);
                    view.getVirtualView().updateStartGame(new StartGameResponse(getUsername(), quartet, 1, true));
                    break;}
                case 3: {
                    ArrayList<LightLeaderCard> quartet = getQuartet(controller);
                    view.getVirtualView().updateStartGame(new StartGameResponse(getUsername(), quartet, 2, true));
                    break;}
                default: System.out.println("bordello fratm "); break;
            }
        } catch(NoSuchUsernameException e){
            view.getVirtualView().updateStartGame(new StartGameResponse(getUsername(), e.getMessage()));
            System.out.println("errore"+e.getMessage());
        }
    }

    private ArrayList<LightLeaderCard> getQuartet(Controller controller){
        ArrayList<LeaderCard> quartet = controller.getLeaderCards();
        ArrayList<LightLeaderCard> lQuartet = new ArrayList<>();
        for(LeaderCard card : quartet)
            lQuartet.add(card.convert());
        return lQuartet;
    }
}
