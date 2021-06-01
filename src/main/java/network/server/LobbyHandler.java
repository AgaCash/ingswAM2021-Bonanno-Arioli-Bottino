package network.server;

import exceptions.LobbyFullException;
import exceptions.NoSuchUsernameException;
import exceptions.NotEnoughPlayersException;
import model.player.Player;
import view.VirtualClient;

import javax.naming.SizeLimitExceededException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;


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
        System.out.println("ciaooo single player parte (o  ci prova almeno)");
        try {
            l.startGame();
            System.out.println("partito seeeeee");
            //virtualClient.setController(l.getSharedController());
        } catch (NotEnoughPlayersException e) {
            e.printStackTrace(); //NON SUCCEDERA' MAI
        }
    }

    public synchronized void joinLobby(Player player, int id, VirtualClient virtualClient) throws LobbyFullException, IndexOutOfBoundsException {
        if(id < 0 || id >= lobbies.size())
            throw new IndexOutOfBoundsException();
        for (Lobby l: lobbies) {
            if(l.getId()==id){
                l.joinLobby(player, virtualClient);
                break;
            }
        }
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
    //TODO: NOTIFY PLAYER LEFT LOBBY
    //TODO: array 0 -> destry
    public synchronized void destroyLobby(Lobby lobby){
        AtomicReference<Lobby> tmpLobby = new AtomicReference<>();
        lobbies.forEach((l)->{
            if(l.equals(lobby)){
                tmpLobby.set(lobby);
            }
        });
        lobbies.remove(tmpLobby);
    }

    public synchronized ArrayList<Lobby> getMultiLobbies() {
        ArrayList<Lobby> multiLobbies = new ArrayList<>();
        lobbies.forEach((l)->{
            if(!l.isSinglePlayerMode()){
                multiLobbies.add(l);
            }
        });
        return multiLobbies;
    }

    public synchronized Lobby getLobby(int id){
        return lobbies.get(id);
    }



}
