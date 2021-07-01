package network.messages.gameMessages;

import clientController.LightController;
import clientModel.player.LightPlayer;
import clientModel.singleplayer.LightLorenzo;
import clientModel.table.LightDevelopmentBoard;
import network.messages.MessageType;

import java.util.ArrayList;

public class EndTurnResponse extends GameMessage{
    private String newPlayerName ;
   /* private LightDevelopmentBoard board;
    private LightFaithTrack track;
    private LightCardSlots slots;
    private LightStrongbox strongbox;
    private String message;
    private boolean isSinglePlayer;
    private boolean gameOver = false;
    private boolean victory;
    private LightLorenzo lorenzo;
    */
    private LightDevelopmentBoard board;
    private ArrayList<LightPlayer> players;
    private LightLorenzo lorenzo;
    private boolean isSinglePlayer;
    private boolean gameOver;
    private String message;

    public EndTurnResponse(String oldPlayer, String newPlayer, ArrayList<LightPlayer> players){
        super(oldPlayer, MessageType.ENDTURNUPDATE);
        this.newPlayerName = newPlayer;
        this.players = players;
        this.gameOver=false;
        this.isSinglePlayer = false;
        //multi player end turn
       /* super(oldPlayer, MessageType.ENDTURNUPDATE);
        this.newPlayerName = newPlayer;
        this.isSinglePlayer = false;
        this.strongbox = strongbox;*/
    }

    public EndTurnResponse(String username, LightLorenzo lorenzo, ArrayList<LightPlayer> players, LightDevelopmentBoard board){
        //single player end turn
      /*  super(username, MessageType.ENDTURNUPDATE);
        this.board = board;
        this.track = track;
        this.slots = newSlots;
        this.strongbox = newStrongbox;
        this.isSinglePlayer = true;
        this.gameOver = false;
        this.lorenzo = lorenzo;*/
        super(username, MessageType.ENDTURNUPDATE);
        this.lorenzo = lorenzo;
        this.players = players;
        this.board = board;
        this.gameOver=false;
        this.isSinglePlayer = true;
    }

    public EndTurnResponse(String username, String message){
        //single player end game
        super(username, MessageType.ENDTURNUPDATE);
        this.message = message;
        this.gameOver=true;
        this.isSinglePlayer = true;
    }

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
        //controller.updateStrongbox(getUsername(), this.strongbox);
        if(this.isSinglePlayer){
            if(gameOver){
                controller.endSinglePlayerGame(this.message);
            }
            else {
                controller.updateSinglePlayerEndTurn(getUsername(), lorenzo, players.get(0), this.board);
               /* controller.updateDevBoard(board);
                //controller.updateFaithTrack(getUsername(), this.track);
                controller.updateCardSlots(getUsername(), this.slots);
                controller.updateLorenzo(lorenzo);
                controller.startTurn();*/

            }

        }else{
            if(gameOver){
                controller.endMultiPlayerGame(newPlayerName, message);
            }
            else {
                controller.updateMultiPlayerEndTurn(getUsername(), players, this.newPlayerName);
               /* controller.showOthersActions(getUsername() , " has ended the turn");

                if (controller.getUsername().equals(this.newPlayerName)) {
                    controller.showSuccess(this.newPlayerName, "IT'S YOUR TURN!");
                    controller.startTurn();
                }
                else
                    controller.showOthersActions(this.newPlayerName , " has started the turn");

                */
            }
        }

    }

    public String getNewPlayerName() {
        return newPlayerName;
    }
}
