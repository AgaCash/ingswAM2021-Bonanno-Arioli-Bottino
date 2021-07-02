package network.messages.gameMessages;

import clientModel.cards.LightLeaderCard;
import controller.Controller;
import exceptions.NoSuchUsernameException;
import model.cards.LeaderCard;
import model.player.Player;
import network.messages.MessageType;
import view.VirtualClient;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Message that ask the Server for the initial items to choose (LeaderCards and Resources)
 */
public class StartGameRequest extends GameMessage{

    /**Constructor
     * @param username the Player's username who asks for start items
     */
    public StartGameRequest(String username){
        super(username, MessageType.STARTGAME);
    }

    @Override
    public void executeCommand(Controller controller, VirtualClient view) {
        try {
            int numPlayers = controller.getPlayers().size();
            ArrayList<String> usersList = controller.getPlayers().stream().map(Player::getNickname)
                    .collect(Collectors.toCollection(ArrayList::new));
            System.out.println(usersList);
            controller.setOrder();
            System.out.println("USERNAME REQUEST: "+controller.getPlayer(getUsername()));
            switch (controller.getPlayer(getUsername()).getStartingTurn()) {
                case 0: {
                    ArrayList<LightLeaderCard> quartet = getQuartet(controller);
                    view.getVirtualView().updateStartGame(new StartGameResponse(getUsername(), quartet,
                            0, false, numPlayers, usersList));
                    break;}
                case 1: {
                    ArrayList<LightLeaderCard> quartet = getQuartet(controller);
                    view.getVirtualView().updateStartGame(new StartGameResponse(getUsername(), quartet,
                            1, false, numPlayers, usersList));
                    break;}
                case 2: {
                    ArrayList<LightLeaderCard> quartet = getQuartet(controller);
                    view.getVirtualView().updateStartGame(new StartGameResponse(getUsername(), quartet,
                            1, true, numPlayers, usersList));
                    break;}
                case 3: {
                    ArrayList<LightLeaderCard> quartet = getQuartet(controller);
                    view.getVirtualView().updateStartGame(new StartGameResponse(getUsername(), quartet,
                            2, true, numPlayers, usersList));
                    break;}
                default: System.out.println("bordello fratm "); break;
            }
        } catch(NoSuchUsernameException e){
            view.getVirtualView().updateStartGame(new StartGameResponse(getUsername(), e.getMessage()));
            System.out.println("errore"+e.getMessage());
        }
    }

    /**Calls the Controller 4-length LeaderCard ArrayList to choose and convert it in a LightLeaderCard ArrayList
     * @param controller the controller instance
     * @return a LightLeaderCard ArrayList
     */
    private ArrayList<LightLeaderCard> getQuartet(Controller controller){
        ArrayList<LeaderCard> quartet = controller.getLeaderCards();
        ArrayList<LightLeaderCard> lQuartet = new ArrayList<>();
        for(LeaderCard card : quartet)
            lQuartet.add(card.convert());
        return lQuartet;
    }
}
