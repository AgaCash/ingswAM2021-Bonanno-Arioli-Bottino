package network.messages.gameMessages;

import clientController.LightController;
import clientModel.player.LightPlayer;
import clientModel.table.LightDevelopmentBoard;
import clientModel.table.LightMarketBoard;
import network.messages.MessageType;

import java.util.ArrayList;

public class SetupResponse extends GameMessage {
    private String message;
    private String firstPlayer;
    private LightDevelopmentBoard board;
    private LightMarketBoard market;
    private ArrayList<LightPlayer> players = new ArrayList<>();


    public SetupResponse(String username, String firstPlayer,
                         LightMarketBoard marketBoard, LightDevelopmentBoard board, ArrayList<LightPlayer> players){
        super(username, MessageType.SETUPRESPONSE);
        this.firstPlayer = firstPlayer;
        this.market = marketBoard;
        this.board = board;
        this.players = players;
    }
    @Override
    public void executeCommand(LightController controller){
        controller.setPlayer(getUsername(), this.players);
        if (players.size() == 1)
            controller.getPlayerBoard().getFaithTrack().setLorenzoPos(0);
        else {
            controller.setPlayers(this.players);
        }
        controller.setDevBoard(this.board);
        controller.setMarketBoard(this.market);

        if(firstPlayer.equals(controller.getUsername())) {
            controller.start();
        }
        else
            controller.waitForMyTurn();

    }
}
