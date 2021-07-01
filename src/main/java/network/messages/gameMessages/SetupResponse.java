package network.messages.gameMessages;

import clientController.LightController;
import clientModel.player.LightPlayer;
import clientModel.singleplayer.LightLorenzo;
import clientModel.table.LightDevelopmentBoard;
import clientModel.table.LightMarketBoard;
import network.messages.MessageType;

import java.util.ArrayList;

public class SetupResponse extends GameMessage {
    private String firstPlayer;
    private LightDevelopmentBoard board;
    private LightMarketBoard market;
    private ArrayList<LightPlayer> players;
    private LightLorenzo lorenzo;
    private LightPlayer singlePlayer;
    private boolean isSinglePlayer;


    public SetupResponse(String username, String firstPlayer,
                         LightMarketBoard marketBoard, LightDevelopmentBoard board, ArrayList<LightPlayer> players){
        super(username, MessageType.SETUPRESPONSE);
        this.firstPlayer = firstPlayer;
        this.market = marketBoard;
        this.board = board;
        this.players = players;
        this.isSinglePlayer = false;
    }

    public SetupResponse(String username, LightPlayer player,
                         LightMarketBoard marketBoard, LightDevelopmentBoard board, LightLorenzo lorenzo){
        super(username, MessageType.SETUPRESPONSE);
        this.singlePlayer = player;
        this.market = marketBoard;
        this.board = board;
        this.lorenzo = lorenzo;
        this.isSinglePlayer = true;
    }
    @Override
    public void executeCommand(LightController controller){
        //controller.setPlayer(this.players);
        controller.setDevBoard(this.board);
        controller.setMarketBoard(this.market);
        if (isSinglePlayer) {
            controller.setPlayer(singlePlayer);
            controller.getPlayerBoard().getFaithTrack().setLorenzoPos(lorenzo.getPosition());
            controller.showSuccess(singlePlayer.getNickname(), "IT'S YOUR TURN!");
            controller.startTurn();
        }
        else {
            controller.setPlayers(this.players);
            if (firstPlayer.equals(controller.getUsername())) {
                controller.showSuccess(firstPlayer, "IT'S YOUR TURN!");
                controller.startTurn();
            } else
                controller.waitForMyTurn();
        }

    }
}
