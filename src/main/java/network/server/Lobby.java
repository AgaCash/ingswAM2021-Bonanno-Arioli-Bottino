package network.server;

import controller.Controller;
import exceptions.LobbyFullException;
import model.player.Player;
import view.VirtualClient;

import java.util.ArrayList;

public class Lobby { //LOBBY E' IL CONTROLLER
    private int id;
    private ArrayList<Player> players;
    private transient ArrayList<VirtualClient> views;
    private transient Controller sharedController;
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
            e.printStackTrace(); //NON SUCCEDERA' MAI!! //e la legge di murphy?
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

    public boolean isUsernamePresent(String username){
        for (Player p: players) {
            if(p.getNickname().equals(username)){
                return true;
            }
        }
        return false;
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
        sharedController = new Controller(id, views);
        for (VirtualClient v:views) {
            v.setController(sharedController);
        }
    }

    public Controller getSharedController(){
        return sharedController;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":\"" + id +"\""+
                ", \"players\": " + players +
                '}';
    }
}
