package network.server;

import controller.Controller;
import exceptions.GameAlreadyStartedException;
import exceptions.LobbyFullException;
import exceptions.NotEnoughPlayersException;
import model.player.Player;
import network.messages.lobbyMessages.LoginMultiPlayerResponse;
import view.VirtualClient;

import java.util.ArrayList;

public class Lobby {
    private int id;
    private ArrayList<Player> players;
    private transient ArrayList<VirtualClient> views;
    private transient Controller sharedController;
    private final boolean singlePlayerMode;
    private boolean gameStarted;

    private void initLobby(){
        players = new ArrayList<>();
        views = new ArrayList<>();
    }

    public Lobby(int id, Player player, VirtualClient virtualClient, boolean singlePlayerMode){
        initLobby();
        this.singlePlayerMode = singlePlayerMode;
        this.id = id;
        player.setStartingTurn(players.size()+1);
        players.add(player);
        views.add(virtualClient);
        gameStarted = false;
    }


    public synchronized void joinLobby(Player player, VirtualClient virtualClient) throws LobbyFullException, GameAlreadyStartedException {
        if(players.size() > 3)
            throw new LobbyFullException();

        if(gameStarted)
            throw new GameAlreadyStartedException("Cannot join. The game is already started");

        //player.setStartingTurn(players.size()+1);
        players.add(player);
        views.add(virtualClient);
        LoginMultiPlayerResponse response = new LoginMultiPlayerResponse(player.getNickname());
        views.forEach((view)-> view.getVirtualView().sendLobbyResponse(response));
        /*
        if(!singlePlayerMode){
            LoginMultiPlayerResponse response = new LoginMultiPlayerResponse(virtualClient.getVirtualView().getUsername());
            views.forEach((view)->{
                view.getVirtualView().sendLobbyResponse(response);
            });
        }*/
    }

   public synchronized void leaveLobby(String username){
       System.out.println(username+" disconnected from lobby");
        players.removeIf(p->p.getNickname().equals(username));
        LobbyHandler.getInstance().destroyLobby(this);
        /*
        if(this.isEmpty()){
            LobbyHandler.getInstance().destroyLobby(this);
        }*/
    }

    //todo: se inserisci numero lobby single throw error

    public synchronized ArrayList<String> getUsernameList(){
        ArrayList<String> names = new ArrayList<>();
        for (Player p : players) {
            names.add(p.getNickname());
        }
        return names;
    }

    public synchronized boolean isEmpty(){
        return players.size()==0;
    }

    public synchronized boolean isUsernamePresent(String username){
        for (Player p: players) {
            if(p.getNickname().equals(username)){
                return true;
            }
        }
        return false;
    }

    public synchronized boolean isUsernameDisconnected(String username){
        return sharedController.isUsernameDisconnected(username);
    }

    public synchronized int getId(){
        return id;
    }

    public synchronized boolean isLobbyFull(){
        return players.size() >= 4;
    }

    public synchronized void resetController(VirtualClient virtualClient){
        virtualClient.setController(sharedController);
    }

    public synchronized void startGame() throws NotEnoughPlayersException {
        if(players.size() < 2 && !singlePlayerMode) {
            throw new NotEnoughPlayersException("Not enough players");
        }
        sharedController = new Controller(id, views);
        System.out.println(sharedController);
        System.out.println(views);
        if(singlePlayerMode){
            views.get(0).setController(sharedController);
            sharedController.addSinglePlayer(players.get(0));
        }else{
            for (VirtualClient v:views) {
                v.setController(sharedController);
                //System.out.println("controller settato a "+v.getVirtualView().getUsername());
                v.getVirtualView().sendStartMultiPlayerSignal();
                //System.out.println("START inviato a "+v.getVirtualView().getUsername());
            }
            sharedController.addMultiPlayers(players, views);
        }
        gameStarted = true;
    }

    public synchronized Controller getSharedController(){
        return sharedController;
    }

    public synchronized boolean isSinglePlayerMode() {
        return singlePlayerMode;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }
}
