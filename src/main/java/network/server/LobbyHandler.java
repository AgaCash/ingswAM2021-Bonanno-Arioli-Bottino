package network.server;

import controller.Controller;
import exceptions.LobbyFullException;
import model.Player;

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

    public void createLobby(Player player) throws SizeLimitExceededException {
        Lobby l = new Lobby(currId, player);
        currId++;
        lobbies.add(l);
    }

    public Controller createSinglePlayer(Player player){
        Lobby l = new Lobby(currId, player, true);
        currId++;
        lobbies.add(l);
        try {
            l.startGame();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return l.getSharedController();
    }

    public void joinLobby(Player player, int id) throws LobbyFullException, IndexOutOfBoundsException {
        for (Lobby l: lobbies) {
            if(l.getId()==id){
                l.joinLobby(player);
                break;
            }
        }
        throw new IndexOutOfBoundsException();
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
