package model;

import model.table.PlayerBoard;
import model.table.Table;

import java.util.ArrayList;
import java.util.Collections;

public class Game {
    private static Game instance = null;
    ArrayList<PlayerBoard> players = new ArrayList<>();
    Table table;
    //Lorenzo cpu = new Lorenzo();  will be ok when DevBoard will adopt singleton pattern

    private Game(){
        initializeGame();
    }

    public static Game getGameInstance(){
        if (instance == null)
            //synchronized(model.Game.class){
            //    if(instance ==null)
            //        instance = new model.Game();
            //    }   double-checked locking
            instance = new Game();
        return instance;
    }

    public void initializeGame(){
        Collections.shuffle(this.players);
        table = Table.getTableInstance();
        //players.get(0).serInkwell
        //players.get(1).chooseResource()
        //players.get(2).


    }

    public void pope(String nickname){

    }

    public void addPlayer(Player newPlayer){
        if(players.size()<4){
            players.add(new PlayerBoard(newPlayer, table.getDevBoard(), table.getMarketBoard()));
        }

    }
    //inizializza i player (@Controller)

    //inizializza le playerboard


}
