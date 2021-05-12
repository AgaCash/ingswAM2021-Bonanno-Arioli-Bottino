package network.server;

import controller.Controller;
import view.VirtualView;

import javax.naming.SizeLimitExceededException;
import java.util.ArrayList;

public class Lobby {
    private int id;
    private ArrayList<Player> players;
    private Controller sharedController;

    public Lobby(int id){
        players = new ArrayList<>();
        this.id = id;
    }

    public void joinLobby(Player player) throws SizeLimitExceededException {
        if(players.size() > 3)
            throw new SizeLimitExceededException();

        player.setStartingTurn(players.size()+1);
        players.add(player);
    }

    public void leaveLobby(Player player){
        players.remove(player);
        for (Player p :players) {
            p.setStartingTurn(players.indexOf(p)+1);
        }
    }

    public int getId(){
        return id;
    }

    public boolean isLobbyFull(){
        return players.size() >= 4;
    }

    public void startGame() throws Exception {
        if(players.size() < 2)
            throw new Exception("Not enaugh players");
        sharedController = new Controller(0, new ArrayList<VirtualView>());
    }

    public Controller getSharedController(){
        return sharedController;
    }

    @Override
    public String toString() {
        return "Lobby{" +
                "id=" + id +
                ", players=" + players +
                '}';
    }
}
