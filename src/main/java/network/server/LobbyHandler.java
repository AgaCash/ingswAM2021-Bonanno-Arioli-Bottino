package network.server;

import exceptions.GameAlreadyStartedException;
import exceptions.LobbyFullException;
import exceptions.NoSuchUsernameException;
import exceptions.NotEnoughPlayersException;
import model.player.Player;
import view.VirtualClient;

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

    public synchronized void createLobby(Player player, VirtualClient virtualClient) throws SizeLimitExceededException {
        Lobby l = new Lobby(currId, player, virtualClient, false);
        currId++;
        lobbies.add(l);
    }

    public synchronized void createSinglePlayer(Player player, VirtualClient virtualClient){
        Lobby l = new Lobby(currId, player, virtualClient, true);
        currId++;
        lobbies.add(l);
        try {
            l.startGame();
            //virtualClient.setController(l.getSharedController());
        } catch (NotEnoughPlayersException e) {
            e.printStackTrace(); //NON SUCCEDERA' MAI
        }
    }

    public synchronized void joinLobby(Player player, int id, VirtualClient virtualClient) throws LobbyFullException, IndexOutOfBoundsException, GameAlreadyStartedException {
        for (Lobby l: lobbies) {
            if(l.getId()==id){
                l.joinLobby(player, virtualClient);
                return;
            }
        }
        throw new IndexOutOfBoundsException();
    }

    public synchronized Lobby getLobbyFromUsername(String username) throws NoSuchUsernameException {
        for (Lobby l:lobbies) {
            if(l.isUsernamePresent(username)){
                return l;
            }
        }
        throw new NoSuchUsernameException("Username not present in any lobby");
    }

    public synchronized boolean isUsernameAvailable(String username){
        try{
            getLobbyFromUsername(username);
            return false;
        } catch (NoSuchUsernameException e) {
            //username doesn't exist, so username is available
            return true;
        }
    }

    public synchronized boolean isUsernameDisconnected(String username){
        try{
            return getLobbyFromUsername(username).isUsernameDisconnected(username);
        } catch (NoSuchUsernameException e) {
            //return false bcs username is not used ==> is not disconnecteds
            return false;
        }
    }

    public synchronized void leaveLobby(Lobby lobby, String username){
        lobby.leaveLobby(username);
        if(lobby.isEmpty()){
            lobbies.remove(lobby);
        }
    }

    //serve????
    public synchronized void destroyLobby(Lobby lobby){
        //disconnetti tutti
        if(!lobbies.contains(lobby)){
            return;
        }
        //elimina la lobby dalla lista
        lobby.getUsernameList().forEach(lobby::leaveLobby);
    }
    //me sa de no

    public synchronized ArrayList<Lobby> getMultiLobbies() {
        ArrayList<Lobby> multiLobbies = new ArrayList<>();
        lobbies.forEach((l)->{
            if(!l.isSinglePlayerMode() && !l.isGameStarted()){
                multiLobbies.add(l);
            }
        });
        return multiLobbies;
    }

    public synchronized Lobby getLobby(int id){
        return lobbies.get(id);
    }



}
