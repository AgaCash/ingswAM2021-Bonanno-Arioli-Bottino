package network.messages.gameMessages;

import clientController.LightController;
import clientModel.player.LightPlayer;
import clientModel.singleplayer.LightLorenzo;
import clientModel.table.LightDevelopmentBoard;
import clientModel.table.LightMarketBoard;
import network.messages.MessageType;

import java.util.ArrayList;

/**
 * Response that updates LightModel with the tha Player's starting status classes and starts the LightController turns
 *
 */
public class SetupResponse extends GameMessage {
    private String firstPlayer;
    private LightDevelopmentBoard board;
    private LightMarketBoard market;
    private ArrayList<LightPlayer> players;
    private LightLorenzo lorenzo;
    private LightPlayer singlePlayer;
    private boolean isSinglePlayer;


    /**Multi Player Game session Constructor
     * @param username the request sender's username
     * @param firstPlayer the first turn Player's username
     * @param marketBoard the initial LightMarketBoard status
     * @param board the initial LightDevelopmentBoard status
     * @param players the entire LightPlayer ArrayList
     */
    public SetupResponse(String username, String firstPlayer,
                         LightMarketBoard marketBoard, LightDevelopmentBoard board, ArrayList<LightPlayer> players){
        super(username, MessageType.SETUPRESPONSE);
        this.firstPlayer = firstPlayer;
        this.market = marketBoard;
        this.board = board;
        this.players = players;
        this.isSinglePlayer = false;
    }

    /**Single Player Game session Constructor
     * @param username the request sender's username
     * @param player the Player's username
     * @param marketBoard the initial LightMarketBoard status
     * @param board the initial LightDevelopmentBoard status
     * @param lorenzo the LightLorenzo startup status
     */
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
