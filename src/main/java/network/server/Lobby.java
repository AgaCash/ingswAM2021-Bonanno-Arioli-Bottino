package network.server;

import controller.Controller;
import exceptions.LobbyFullException;
import model.player.Player;
import view.VirtualClient;

import java.util.ArrayList;

public class Lobby { //LOBBY E' IL CONTROLLER
    private int id;
    private ArrayList<Player> players;
    private ArrayList<VirtualClient> views;
    private Controller sharedController;
    private boolean singlePlayerMode;

    private void initLobby(){
        players = new ArrayList<>();
        views = new ArrayList<>();
    }

    public Lobby(int id, Player player, VirtualClient virtualClient, boolean singlePlayerMode){
        initLobby();
        this.singlePlayerMode = singlePlayerMode;
        this.id = id;
        try {
            joinLobby(player, virtualClient);
        } catch (LobbyFullException e) {
            e.printStackTrace(); //NON SUCCEDERA' MAI!!
        }
    }

    public Lobby(int id, Player player, VirtualClient virtualClient){
        initLobby();
        this.id = id;
        try {
            joinLobby(player, virtualClient);
        } catch (LobbyFullException e) {
            e.printStackTrace(); //NON SUCCEDERA' MAI!! //todo: e la legge di murphy?
        }
    }

    public void joinLobby(Player player, VirtualClient virtualClient) throws LobbyFullException{
        if(players.size() > 3)
            throw new LobbyFullException();

        player.setStartingTurn(players.size()+1);
        players.add(player);
        views.add(virtualClient);
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
        return "\nLobby " + id +
                ": players " + players.size() +"/4"+
                "\n";
    }
}
