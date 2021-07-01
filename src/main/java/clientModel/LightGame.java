package clientModel;

import clientModel.cards.LightCardSlots;
import clientModel.cards.LightLeaderCard;
import clientModel.player.LightPlayer;
import clientModel.resources.LightResource;
import clientModel.strongbox.LightStrongbox;
import clientModel.table.*;
import clientModel.warehouse.LightWarehouseDepot;
import exceptions.NoSuchUsernameException;

import java.util.ArrayList;

/**
 * Class that handles the LightModel status during the game
 * It contains the main Client LightPlayer and a copy of all others LightPlayers (in multiplayer game mode) to follow the game processing
 */
public class LightGame {
    private LightPlayer player = new LightPlayer();
    private ArrayList<LightPlayer> otherPlayers = new ArrayList<>();
    private LightTable table;
    private ArrayList<LightResource> threwResources = new ArrayList<>();

    public LightGame(){
        this.table = new LightTable();
    }

    /**Returns Client Player's username
     * @return a String
     */
    public String getUsername(){
        return player.getNickname();
    }

    /**Updates a LightPlayer's LightCardSlots with its corresponding Model's CardSlots
     * @param username the LightPlayer's username who needs to update its LightCardSlots
     * @param cardSlots a LightCardSlots instance
     * //@throws NoSuchUsernameException (never thrown)
     */
    public void updateCardSlots(String username, LightCardSlots cardSlots) throws NoSuchUsernameException {
        LightPlayer player = getPlayer(username);
        player.getPlayerBoard().setCardSlots(cardSlots);
    }

    /**Updates a LightPlayer's LightLeaderCardSlot with its corresponding Model's LeaderCardSlot
     * @param username the LightPlayer's username who needs to update its LightLeaderCardSlot
     * @param leaderSlot a LightLeaderCard instance
     * //@throws NoSuchUsernameException
     */
   public void updateLeaderSlot(String username, ArrayList<LightLeaderCard> leaderSlot) throws NoSuchUsernameException {
        LightPlayer player = getPlayer(username);
        player.getPlayerBoard().setLeaderSlot(leaderSlot);
    }



    /**Updates the LightModel's LightDevelopmentBoard with the current Model's DevelopmentBoard
     * @param developmentBoard a LightDevelopmentBoard instance
     */
    public void updateDevBoard(LightDevelopmentBoard developmentBoard){
        table.setDevelopmentBoard(developmentBoard);
    }
    public void updateWarehouse(String username, LightWarehouseDepot warehouseDepot) throws NoSuchUsernameException {
        LightPlayer player = getPlayer(username);
        player.getPlayerBoard().setWarehouse(warehouseDepot);
    }
    public void updateMarketBoard(LightMarketBoard market){
        table.setMarketBoard(market);
    }
    public void updateStrongbox(String username, LightStrongbox strongbox) throws NoSuchUsernameException {
        LightPlayer player = getPlayer(username);
        player.getPlayerBoard().setStrongbox(strongbox);
    }
    public void updateFaithTrack(String username, LightFaithTrack faithTrack) throws NoSuchUsernameException {
        LightPlayer player = getPlayer(username);
        player.getPlayerBoard().setFaithTrack(faithTrack);

    }
    public LightPlayer getPlayer(String username) throws NoSuchUsernameException {
        if(player.getNickname().equals(username))
            return player;
        for(LightPlayer player:otherPlayers)
            if(player.getNickname().equals(username))
                return player;
        throw new NoSuchUsernameException();

    }
    public void setPlayer(LightPlayer player){
        this.player = player;
    }
    public LightPlayerBoard getPlayerBoard(){
        return player.getPlayerBoard();
    }

    public void setMarketBoard(LightMarketBoard market){
        this.table.setMarketBoard(market);
    }
    public LightMarketBoard getMarketBoard(){ return this.table.getMarketBoard(); }

    public void setDevBoard(LightDevelopmentBoard board){
        this.table.setDevelopmentBoard(board);
    }
    public LightDevelopmentBoard getDevBoard(){ return this.table.getDevBoard(); }

    public void setPlayers(ArrayList<LightPlayer> players){
        this.otherPlayers = players;
        for(LightPlayer player : players)
            if(player.getNickname().equals(this.player.getNickname())) {
                this.player = player;
            }
        this.otherPlayers.remove(this.player);

    }

    public LightPlayer getPlayer(){
        return this.player;
    }



}
