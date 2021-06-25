package network.server;

import controller.Controller;
import exceptions.GameAlreadyStartedException;
import exceptions.LobbyFullException;
import exceptions.NotEnoughPlayersException;
import model.player.Player;
import network.messages.lobbyMessages.LobbyPlayerDisconnectedMessage;
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
        //player.setStartingTurn(players.size()+1);
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

    public Player getCreator(){
        return players.get(0);
    }

   public synchronized void leaveLobby(String username){
       System.out.println(username+" disconnected from lobby");
       LobbyPlayerDisconnectedMessage l;
       if (players.get(0).getNickname().equals(username))
           l = new LobbyPlayerDisconnectedMessage(username, true);
       else
           l = new LobbyPlayerDisconnectedMessage(username, false);
       views.removeIf(v->v.getVirtualView().getUsername().equals(username));
       views.forEach((v)->v.getVirtualView().sendPlayerResilienceMessage(l));
       players.removeIf(p->p.getNickname().equals(username));
    }

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
        try{
            return sharedController.isUsernameDisconnected(username);
        }catch (NullPointerException n){
            return false;
        }
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
        sharedController = new Controller(id, views, id);
        System.out.println(sharedController);
        System.out.println(views);
        System.out.println(players);
        System.out.println(views);
        if(singlePlayerMode){
            sharedController.addSinglePlayer(players.get(0));
            views.get(0).setController(sharedController);
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

    @Override
    public String toString() {
        String s ="";
        for (Player p: players) {
            s+= p.getNickname()+", ";
        }
        return "ID: "+id+
                "  - Players: "+s;
    }
}
