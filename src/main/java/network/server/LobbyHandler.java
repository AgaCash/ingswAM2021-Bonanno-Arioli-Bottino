package network.server;

import controller.Controller;

import javax.naming.SizeLimitExceededException;
import java.util.ArrayList;
import java.util.Map;


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
        Lobby l = new Lobby(currId);
        currId++;
        l.joinLobby(player);
    }

    public void joinLobby(Player player, int id) throws SizeLimitExceededException {
        for (Lobby l: lobbies) {
            if(l.getId()==id){
                l.joinLobby(player);
                break;
            }
        }
    }

    public ArrayList<Lobby> getLobbies() {
        return lobbies;
    }

}
