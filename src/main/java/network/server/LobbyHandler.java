package network.server;

import controller.Controller;
import exceptions.LobbyFullException;
import model.player.Player;
import view.VirtualClient;
import view.VirtualView;

import javax.naming.SizeLimitExceededException;
import java.util.ArrayList;


/*

        DA SINCRONIZZARE

 */

public class LobbyHandler {
    private static LobbyHandler instance;
    private ArrayList<Lobby> lobbies;
    private int currId;

    private LobbyHandler(){
        lobbies = new ArrayList<>();
        currId = 0;
    }

    public static LobbyHandler getInstance() {
        if(instance==null){
            instance = new LobbyHandler();
        }
        return instance;
    }

    public void createLobby(Player player, VirtualClient virtualClient) throws SizeLimitExceededException {
        Lobby l = new Lobby(currId, player, virtualClient);
        currId++;
        lobbies.add(l);
    }

    public Controller createSinglePlayer(Player player, VirtualClient virtualClient){
        Lobby l = new Lobby(currId, player, virtualClient, true);
        currId++;
        lobbies.add(l);
        try {
            l.startGame();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return l.getSharedController();
    }

    public void joinLobby(Player player, int id, VirtualClient virtualClient) throws LobbyFullException, IndexOutOfBoundsException {
        if(id < 0 || id >= lobbies.size())
            throw new IndexOutOfBoundsException();
        for (Lobby l: lobbies) {
            if(l.getId()==id){
                l.joinLobby(player, virtualClient);
                break;
            }
        }
    }

    public boolean checkUsername(String username){
        for (Lobby l: lobbies) {
            if(l.getUsernameList().contains(username))
                return false;
        }
        return true;
    }

    public ArrayList<Lobby> getLobbies() {
        return lobbies;
    }

    public Lobby getLobby(int id){
        return lobbies.get(id);
    }



}
