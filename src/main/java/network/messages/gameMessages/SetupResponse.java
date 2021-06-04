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
        /*for(LightPlayer p: players){
            LightPlayer newPlayer = new LightPlayer();
            LightPlayerBoard lBoard = new LightPlayerBoard();

            ArrayList<LightLeaderCard> leaderCards =(ArrayList<LightLeaderCard>) p.getPlayerBoard().getLeaderSlot().clone();
            ArrayList<LightDevelopmentCard> slots = (ArrayList<LightDevelopmentCard>) p.getPlayerBoard().getCardSlots().clone();

            lBoard.setLeaderSlot(leaderCards);
            lBoard.setCardSlots(slots);
            lBoard.setInkwell(p.getPlayerBoard().getInkwell());
            lBoard.setFaithTrack(p.getPlayerBoard().getFaithTrack());
            lBoard.setWarehouse(p.getPlayerBoard().getWarehouseDepot());
            lBoard.setStrongbox(p.getPlayerBoard().getStrongbox());
            lBoard.setPoints(p.getPlayerBoard().getPoints());

            newPlayer.setPlayerBoard(lBoard);
            newPlayer.setPoints(p.getPoints());
            newPlayer.setNickname(p.getNickname());
            newPlayer.setStartingTurn(p.getStartingTurn());
        }*/
    }
    @Override
    public void executeCommand(LightController controller){
        controller.setPlayer(getUsername(), this.players);
        controller.setPlayers(this.players);
        controller.setDevBoard(this.board);
        controller.setMarketBoard(this.market);
        if(firstPlayer.equals(controller.getUsername()))
            controller.start();
        else
            controller.waitForMyTurn();
    }
}
