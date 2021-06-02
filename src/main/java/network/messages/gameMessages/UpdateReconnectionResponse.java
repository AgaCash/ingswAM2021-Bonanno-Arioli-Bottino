package network.messages.gameMessages;

import clientController.LightController;
import clientModel.player.LightPlayer;
import clientModel.table.LightDevelopmentBoard;
import clientModel.table.LightMarketBoard;
import network.messages.MessageType;

import java.util.ArrayList;

public class UpdateReconnectionResponse extends GameMessage{
    private LightMarketBoard market;
    private LightDevelopmentBoard board;
    private ArrayList<LightPlayer> players;

    public UpdateReconnectionResponse(String username, LightMarketBoard market, LightDevelopmentBoard board, ArrayList<LightPlayer> players) {
        super(username, MessageType.RECONNECTION_UPDATE_RESPONSE);
        this.market = market;
        this.board = board;
        this.players = players;
    }

    @Override
    public void executeCommand(LightController controller) {
        controller.setMarketBoard(this.market);
        controller.setDevBoard(this.board);
        controller.setPlayers(this.players);
        controller.waitForMyTurn();
    }
}
