package network.messages.gameMessages;

import clientController.LightController;
import clientModel.player.LightPlayer;
import clientModel.table.LightDevelopmentBoard;
import clientModel.table.LightMarketBoard;
import network.messages.MessageType;

import java.util.ArrayList;

//todo aga
public class UpdateReconnectionResponse extends GameMessage{
    private LightMarketBoard market;
    private LightDevelopmentBoard board;
    private ArrayList<LightPlayer> players;
    private boolean isSinglePlayer;
    private int numPlayersInLobby;

    public UpdateReconnectionResponse(String username, LightMarketBoard market, LightDevelopmentBoard board, ArrayList<LightPlayer> players, boolean isSinglePlayer, int numPlayersInLobby) {
        super(username, MessageType.RECONNECTION_UPDATE_RESPONSE);
        this.market = market;
        this.board = board;
        this.players = players;
        this.isSinglePlayer = isSinglePlayer;
        this.numPlayersInLobby = numPlayersInLobby;
    }

    @Override
    public void executeCommand(LightController controller) {
        LightPlayer tmpPlayer = null;
        for(LightPlayer player : players)
            if(player.getNickname().equals(getUsername())) {
                tmpPlayer = player;
            }
        controller.setNumOfPlayerInLobby(numPlayersInLobby);
        controller.setMarketBoard(this.market);
        controller.setDevBoard(this.board);
        ArrayList<String> ul = new ArrayList<>();
        for(LightPlayer p: players){
            ul.add(p.getNickname());
            System.out.println(p.getNickname());
        }
        controller.setUsernamesList(ul);
        controller.setPlayers(this.players);
        controller.setPlayer(tmpPlayer);
        if(isSinglePlayer)
            controller.reconnectSinglePlayer();
        else
            controller.waitForMyTurn();
    }
}
