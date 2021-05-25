package network.timer;

import network.messages.pingMessages.PingGameMessage;
import network.messages.pingMessages.PingLobbyMessage;
import network.server.Lobby;
import view.VirtualClient;

import java.util.Scanner;

public class LobbyServerTimerCheckConnection extends ResettableTimer{

    private VirtualClient virtualClient;
    private LobbyServerTimerSendPing lobbyServerTimerSendPing;

    public LobbyServerTimerCheckConnection(long timeout_ms, VirtualClient virtualClient) {
        super(timeout_ms);
        this.virtualClient = virtualClient;
        //crea il sottotimer per il loop di invio di ping
        lobbyServerTimerSendPing = new LobbyServerTimerSendPing(timeout_ms/2, virtualClient);
        Thread t = new Thread(lobbyServerTimerSendPing);
        t.start();
    }

    @Override
    protected void timeout() {
        virtualClient.disconnectPlayerFromLobby();
        this.finish();
        lobbyServerTimerSendPing.finish();
    }

    @Override
    public void reset() {
        super.reset();
    }

}
