package network.messages.gameMessages;

import clientController.LightController;
import clientModel.player.LightPlayer;
import clientModel.table.LightDevelopmentBoard;
import clientModel.table.LightMarketBoard;
import model.player.Player;
import network.messages.MessageType;

import java.util.ArrayList;

public class UpdateReconnectionResponse extends GameMessage{
    private LightMarketBoard market;
    private LightDevelopmentBoard board;
    private ArrayList<LightPlayer> players;
    private boolean isSinglePlayer;

    public UpdateReconnectionResponse(String username, LightMarketBoard market, LightDevelopmentBoard board, ArrayList<LightPlayer> players, boolean isSinglePlayer) {
        super(username, MessageType.RECONNECTION_UPDATE_RESPONSE);
        this.market = market;
        this.board = board;
        this.players = players;
        this.isSinglePlayer = isSinglePlayer;
    }

    @Override
    public void executeCommand(LightController controller) {
        LightPlayer tmpPlayer = null;
        for(LightPlayer player : players)
            if(player.getNickname().equals(getUsername())) {
                tmpPlayer = player;
            }
        controller.setMarketBoard(this.market);
        controller.setDevBoard(this.board);
        controller.setPlayers(this.players);
        controller.setPlayer(tmpPlayer);
        if(isSinglePlayer)
            controller.reconnectSinglePlayer();
        else
            controller.waitForMyTurn();
    }
}
