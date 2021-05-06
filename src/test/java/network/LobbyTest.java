package network;

import network.server.Lobby;
import network.server.LobbyHandler;
import network.server.Player;
import org.junit.jupiter.api.Test;

import javax.naming.SizeLimitExceededException;

import static org.junit.jupiter.api.Assertions.*;

public class LobbyTest {
    @Test
    public void creationTest() throws Exception {
        Lobby lobby = new Lobby(7);
        assertNotNull(lobby);
        System.out.println((lobby.toString()));

        Player player = new Player("user1");
        lobby.joinLobby(player);
        lobby.joinLobby(player);
        lobby.joinLobby(player);
        lobby.joinLobby(player);
        assertThrows(SizeLimitExceededException.class, () -> lobby.joinLobby(player));
        System.out.println(lobby);
        assertTrue(lobby.isLobbyFull());
        lobby.leaveLobby(player);
        lobby.leaveLobby(player);
        lobby.leaveLobby(player);
        lobby.leaveLobby(player);
        assertEquals(lobby.getId(), 7);
        assertThrows(Exception.class, ()->lobby.startGame());
        lobby.joinLobby(player);
        lobby.joinLobby(player);
        lobby.startGame();
        assertNotNull(lobby.getSharedController());
    }

    @Test
    public void lobbyHandlerTest(){
        LobbyHandler lobbyHandler = LobbyHandler.getInstance();
        Player player = new Player("asd?");
        Player player2 = new Player("asd!");
        assertDoesNotThrow(()->lobbyHandler.createLobby(player));
        assertDoesNotThrow(()->lobbyHandler.joinLobby(player2, 0));
        System.out.println( lobbyHandler.getLobbies() );
        System.out.println( lobbyHandler.getLobby(0) );

    }
}
