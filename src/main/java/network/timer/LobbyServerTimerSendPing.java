package network.timer;

import network.messages.pingMessages.PingLobbyMessage;
import view.VirtualClient;

public class LobbyServerTimerSendPing extends ResettableTimer {
    private VirtualClient virtualClient;

    public LobbyServerTimerSendPing(long timeout_ms, VirtualClient virtualClient) {
        super(timeout_ms);
        this.virtualClient = virtualClient;
    }

    @Override
    protected void timeout(){
        PingLobbyMessage pingMessage = new PingLobbyMessage(virtualClient.getVirtualView().getUsername());
        virtualClient.getVirtualView().sendPing(pingMessage);
    }

    @Override
    public void reset() {
        super.reset();
    }
}
//kill thread -> thread.shutdown();