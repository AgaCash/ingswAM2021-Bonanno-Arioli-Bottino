package network.messages.gameMessages;

import clientController.LightController;
import clientModel.player.LightPlayer;
import clientModel.singleplayer.LightLorenzo;
import clientModel.table.LightDevelopmentBoard;
import network.messages.MessageType;

import java.util.ArrayList;

/**
 * Message that implements the end turn LightModel's update . It last Turn is ended, sends the End Game updates to Light Model
 */
public class EndTurnResponse extends GameMessage{
    private String newPlayerName ;
    private LightDevelopmentBoard board;
    private ArrayList<LightPlayer> players;
    private LightLorenzo lorenzo;
    private boolean isSinglePlayer;
    private boolean gameOver;
    private String message;

    /**Multi Player end turn response
     * @param oldPlayer the Player's username that ended the turn
     * @param newPlayer the Player's username that starts the turn
     * @param players the entire LightPlayer ArrayList
     */
    public EndTurnResponse(String oldPlayer, String newPlayer, ArrayList<LightPlayer> players){
        super(oldPlayer, MessageType.ENDTURNUPDATE);
        this.newPlayerName = newPlayer;
        this.players = players;
        this.gameOver=false;
        this.isSinglePlayer = false;
        //multi player end turn
    }

    /**Single Player end turn response
     * @param username the Player's username
     * @param lorenzo the LightLorenzo instance with it's new actions
     * @param players the LightPlayer ArrayList (it's a 1-length ArrayList)
     * @param board the new LightDevelopmentBoard instance
     */
    public EndTurnResponse(String username, LightLorenzo lorenzo, ArrayList<LightPlayer> players, LightDevelopmentBoard board){
        //single player end turn
        super(username, MessageType.ENDTURNUPDATE);
        this.lorenzo = lorenzo;
        this.players = players;
        this.board = board;
        this.gameOver=false;
        this.isSinglePlayer = true;
    }

    /**Single Player end Game message
     * @param username the Player's username
     * @param message the final message containing if Player won or not
     */
    public EndTurnResponse(String username, String message){
        //single player end game
        super(username, MessageType.ENDTURNUPDATE);
        this.message = message;
        this.gameOver=true;
        this.isSinglePlayer = true;
    }

    /**Multi Player end game message
     * @param username the last Player's username who ended the turn
     * @param rank the String containing the final rank
     * @param winner the winner Player's username
     */
    public EndTurnResponse(String username, String rank, String winner){
        //multi player end game
        super(username, MessageType.ENDTURNUPDATE);
        this.newPlayerName = winner;
        this.message = rank;
        this.gameOver = true;
        this.isSinglePlayer = false;
    }
    @Override
    public void executeCommand(LightController controller){
        if(this.isSinglePlayer){
            if(gameOver) controller.endSinglePlayerGame(this.message);
            else controller.updateSinglePlayerEndTurn(getUsername(), lorenzo, players.get(0), this.board);
        }else{
            if(gameOver) controller.endMultiPlayerGame(newPlayerName, message);
            else controller.updateMultiPlayerEndTurn(getUsername(), players, this.newPlayerName);
        }
    }

    /**Returns the Player's username that starts the turn in Multi Player Game sessione
     * @return a String
     */
    public String getNewPlayerName() {
        return newPlayerName;
    }
}
