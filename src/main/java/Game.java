import singleplayer.Lorenzo;
import table.MarketBoard;
import table.Table;

import java.util.ArrayList;

public class Game {

    private static Game instance = null;
    ArrayList<Player> players = new ArrayList<Player>(4);
    Table table = new Table();
    //Lorenzo cpu = new Lorenzo();  will be ok when DevBoard will adopt singleton pattern

    private Game(){
    }

    public static Game getGameInstance(){
        if (instance == null)
            //synchronized(Game.class){
            //    if(instance ==null)
            //        instance = new Game();
            //    }   double-checked locking
            instance = new Game();
        return instance;
    }

    public void pope(String nickname){

    }

    public void addPlayer(Player newPlayer){
        if(players.size()<4)
            players.add(newPlayer);
    }
}
