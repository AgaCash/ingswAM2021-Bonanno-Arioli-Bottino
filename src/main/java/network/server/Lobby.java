package network.server;

import controller.Controller;
import exceptions.LobbyFullException;
import model.Player;
import view.VirtualView;

import java.util.ArrayList;

public class Lobby { //LOBBY E' IL CONTROLLER
    private int id;
    private ArrayList<Player> players;
    private ArrayList<VirtualView> views;
    private Controller sharedController;
    private boolean singlePlayerMode;

    public Lobby(int id, Player player, boolean singlePlayerMode){
        this.singlePlayerMode = singlePlayerMode;
        players = new ArrayList<>();
        this.id = id;
        players.add(player);

    }

    public Lobby(int id, Player player){
        players = new ArrayList<>();
        this.id = id;
        players.add(player);
    }

    public void joinLobby(Player player) throws LobbyFullException{
        if(players.size() > 3)
            throw new LobbyFullException();

        player.setStartingTurn(players.size()+1);
        players.add(player);
    }

    public void leaveLobby(Player player){
        players.remove(player);
        for (Player p :players) {
            p.setStartingTurn(players.indexOf(p)+1);
        }
    }

    public ArrayList<String> getUsernameList(){
        ArrayList<String> names = new ArrayList<>();
        for (Player p : players) {
            names.add(p.getNickname());
        }
        return names;
    }

    public int getId(){
        return id;
    }

    public boolean isLobbyFull(){
        return players.size() >= 4;
    }

    public void startGame() throws Exception {
        if(players.size() < 2 && !singlePlayerMode)
            throw new Exception("Not enough players");
        //CREA VIEWS PASSANDOGLI in e out
        sharedController = new Controller(id, views);
    }

    public Controller getSharedController(){
        return sharedController;
    }

    @Override
    public String toString() {
        return "Lobby{" +
                " id: " + id +
                ", player: " + players +
                '}';
    }
}
