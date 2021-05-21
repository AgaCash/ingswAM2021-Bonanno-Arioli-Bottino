package clientModel;

import clientModel.cards.LightCardSlots;
import clientModel.player.LightPlayer;
import clientModel.resources.LightResource;
import clientModel.singleplayer.LightLorenzo;
import clientModel.strongbox.LightStrongbox;
import clientModel.table.LightDeck;
import clientModel.table.LightDevelopmentBoard;
import clientModel.table.LightMarketBoard;
import clientModel.table.LightTable;
import clientModel.warehouse.LightWarehouseDepot;
import exceptions.NoSuchUsernameException;

import java.util.ArrayList;

public class LightGame {
    private LightPlayer player = new LightPlayer();
    private ArrayList<LightPlayer> otherPlayers = new ArrayList<>();
    private LightTable table;
    private LightLorenzo cpu;
    private ArrayList<LightResource> threwResources = new ArrayList<>();

    public LightGame (ArrayList<LightPlayer> setPlayers, LightTable table){
        this.otherPlayers = setPlayers;
        this.table = table;
    }

    public LightGame (LightLorenzo cpu, LightTable table){
        this.cpu = cpu;
        this.table = table;
    }

    public LightDeck getDeck(int slot){
        return table.getDevBoard().getDeck(slot);
    }

    public String getUsername(){
        return player.getNickname();
    }

    public void updateCardSlots(String username, LightCardSlots cardSlots) throws NoSuchUsernameException {
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

}
