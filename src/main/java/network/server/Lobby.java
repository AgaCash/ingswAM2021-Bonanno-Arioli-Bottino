package network.server;

import controller.Controller;
import exceptions.LobbyFullException;
import exceptions.NotEnoughPlayersException;
import model.player.Player;
import network.messages.lobbyMessages.LoginMultiPlayerResponse;
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
        player.setStartingTurn(players.size()+1);
        players.add(player);
        views.add(virtualClient);
    }


    public void joinLobby(Player player, VirtualClient virtualClient) throws LobbyFullException{
        if(players.size() > 3)
            throw new LobbyFullException();

        //player.setStartingTurn(players.size()+1);
        players.add(player);
        views.add(virtualClient);
        LoginMultiPlayerResponse response = new LoginMultiPlayerResponse(player.getNickname());
        views.forEach((view)->{
            view.getVirtualView().sendLobbyResponse(response);
        });
        /*
        if(!singlePlayerMode){
            LoginMultiPlayerResponse response = new LoginMultiPlayerResponse(virtualClient.getVirtualView().getUsername());
            views.forEach((view)->{
                view.getVirtualView().sendLobbyResponse(response);
            });
        }*/
    }

   public void leaveLobby(String username){
        players.removeIf(p->p.getNickname().equals(username));
        /*
        for (Player p :players) {
            p.setStartingTurn(players.indexOf(p)+1);
        }*/
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

    public void startGame() throws NotEnoughPlayersException {
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
                System.out.println("controller settato a "+v.getVirtualView().getUsername());
                v.getVirtualView().sendStartMultiPlayerSignal();
                System.out.println("START inviato a "+v.getVirtualView().getUsername());
            }
            sharedController.addMultiPlayers(players, views);
        }
    }

    public Controller getSharedController(){
        return sharedController;
    }

}
