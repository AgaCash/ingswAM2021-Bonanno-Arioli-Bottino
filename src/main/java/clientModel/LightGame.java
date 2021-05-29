package clientModel;

import clientModel.cards.LightDevelopmentCard;
import clientModel.player.LightPlayer;
import clientModel.resources.LightResource;
import clientModel.singleplayer.LightLorenzo;
import clientModel.strongbox.LightStrongbox;
import clientModel.table.*;
import clientModel.warehouse.LightWarehouseDepot;
import exceptions.NoSuchUsernameException;

import java.util.ArrayList;

public class LightGame {
    private LightPlayer player = new LightPlayer();
    private ArrayList<LightPlayer> otherPlayers = new ArrayList<>();
    private LightTable table;
    private LightLorenzo cpu;
    private ArrayList<LightResource> threwResources = new ArrayList<>();

    public LightGame (ArrayList<LightPlayer> setPlayers){
        this.otherPlayers = setPlayers;
        this.table = new LightTable();
    }

   /* public LightGame (LightLorenzo cpu, LightTable table){
        this.cpu = cpu;
        this.table = table;
    }*/

    public LightGame(){
        this.table = new LightTable();
    }

    public LightDevelopmentCard getDeck(int slot){
        return table.getDevBoard().getDeck(slot);
    }

    public String getUsername(){
        return player.getNickname();
    }

    public void updateCardSlots(String username, ArrayList<LightDevelopmentCard> cardSlots) throws NoSuchUsernameException {
        LightPlayer player = getPlayer(username);
        player.getPlayerBoard().setCardSlots(cardSlots);
    }
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
    private LightPlayer getPlayer(String username) throws NoSuchUsernameException {
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

    public void setDevBoard(LightDevelopmentBoard board){
        this.table.setDevelopmentBoard(board);
    }

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
