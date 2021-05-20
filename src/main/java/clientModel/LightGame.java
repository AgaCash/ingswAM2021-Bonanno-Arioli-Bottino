package clientModel;

import clientModel.player.LightPlayer;
import clientModel.resources.LightResource;
import clientModel.singleplayer.LightLorenzo;
import clientModel.table.LightTable;

import java.util.ArrayList;

public class LightGame {
    private ArrayList<LightPlayer> players = new ArrayList<>();
    private LightTable table;
    private LightLorenzo cpu;
    private ArrayList<LightResource> threwResources = new ArrayList<>();

    public LightGame (ArrayList<LightPlayer> setPlayers, LightTable table){
        this.players = setPlayers;
        this.table = table;
    }

    public LightGame (LightLorenzo cpu, LightTable table){
        this.cpu = cpu;
        this.table = table;
    }
}
