package clientModel;

import clientModel.cards.LightCardSlots;
import clientModel.cards.LightLeaderCard;
import clientModel.player.LightPlayer;
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
     * @throws NoSuchUsernameException if there's not any LightPlayer with such username
     */
     public void updateCardSlots(String username, LightCardSlots cardSlots) throws NoSuchUsernameException {
        LightPlayer player = getPlayer(username);
        player.getPlayerBoard().setCardSlots(cardSlots);
     }

    /**Updates a LightPlayer's LightLeaderCardSlot with its corresponding Model's LeaderCardSlot
     * @param username the LightPlayer's username who needs to update its LightLeaderCardSlot
     * @param leaderSlot a LightLeaderCard instance
     * @throws NoSuchUsernameException if there's not any LightPlayer with such username
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

    /**Updates a LightPlayer's LightWarehouseDepot with its corresponding Model's WarehouseDepot
     * @param username the LightPlayer's username who needs to update its LightWarehouseDepot
     * @param warehouseDepot a LightWarehouseDepot instance
     * @throws NoSuchUsernameException if there's not any LightPlayer with such username
     */
    public void updateWarehouse(String username, LightWarehouseDepot warehouseDepot) throws NoSuchUsernameException {
        LightPlayer player = getPlayer(username);
        player.getPlayerBoard().setWarehouse(warehouseDepot);
    }

    /**Updates the LightModel's LightMarketBoard with the current Model's MarketBoard
     * @param market a LightMarketBoard instance
     */
    public void updateMarketBoard(LightMarketBoard market){
        table.setMarketBoard(market);
    }

    /**Updates a LightPlayer's LightStrongbox with its corresponding Model's Strongbox
     * @param username the LightPlayer's username who needs to update its LightStrongbox
     * @param strongbox a LightStrongbox instance
     * @throws NoSuchUsernameException if there's not any LightPlayer with such username
     */
    public void updateStrongbox(String username, LightStrongbox strongbox) throws NoSuchUsernameException {
        LightPlayer player = getPlayer(username);
        player.getPlayerBoard().setStrongbox(strongbox);
    }

    /**Updates a LightPlayer's LightFaithTrack with its corresponding Model's FaithTrack
     * @param username the LightPlayer's username who needs to update its LightFaithTrack
     * @param faithTrack a LightFaithTrack instance
     * @throws NoSuchUsernameException if there's not any LightPlayer with such username
     */
    public void updateFaithTrack(String username, LightFaithTrack faithTrack) throws NoSuchUsernameException {
        LightPlayer player = getPlayer(username);
        player.getPlayerBoard().setFaithTrack(faithTrack);

    }


    /**Returns a LightPlayer instance by username
     * @param username the searched LightPlayer's username
     * @return a LightPlayer instance
     * @throws NoSuchUsernameException if there's not any LightPlayer with such username
     */
    public LightPlayer getPlayer(String username) throws NoSuchUsernameException {
        if(player.getNickname().equals(username))
            return player;
        for(LightPlayer player:otherPlayers)
            if(player.getNickname().equals(username))
                return player;
        throw new NoSuchUsernameException();

    }

    /**Sets the User's LightPlayer instance
     * @param player a LightPlayer instance
     */
    public void setPlayer(LightPlayer player){
        this.player = player;
    }

    /**Returns the User's LightPlayer instance
     * @return a LightPlayer instance
     */
    public LightPlayer getPlayer(){
        return this.player;
    }

    /**Returns the User's LightPlayerBoard
     * @return a LightPlayerBoard instance
     */
    public LightPlayerBoard getPlayerBoard(){
        return player.getPlayerBoard();
    }

    /**Sets the LightModel's LightMarketBoard
     * @param market a LightMarketBoard instance copy of Server Model's MarketBoard
     */
    public void setMarketBoard(LightMarketBoard market){
        this.table.setMarketBoard(market);
    }

    /**Returns the LightModel's LightMarketBoard instance
     * @return a LightMarketBoard instance
     */
    public LightMarketBoard getMarketBoard(){ return this.table.getMarketBoard(); }

    /**Sets the LightModel's LightDevelopmentBoard
     * @param board a LightDevelopmentBoard instance copy of Server Model's DevelopmentBoard
     */
    public void setDevBoard(LightDevelopmentBoard board){
        this.table.setDevelopmentBoard(board);
    }

    /**Returns the LightModel's LightDevelopmentBoard instance
     * @return a LightDevelopmentBoard instance
     */
    public LightDevelopmentBoard getDevBoard(){ return this.table.getDevBoard(); }

    /**Sets a LightPlayer copy of a Model's Player
     * @param username the LightPlayer's username
     * @param players the entire LightPlayer ArrayList
     */
    public void setPlayers(String username, ArrayList<LightPlayer> players){
        this.otherPlayers = players;
        for(LightPlayer player : players) {
            if (player.getNickname().equals(username)) {
                this.player = player;
            }
        }
        this.otherPlayers.remove(this.player);

    }




}
