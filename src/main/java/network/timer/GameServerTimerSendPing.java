package network.timer;

import network.messages.pingMessages.PingGameMessage;
import view.VirtualClient;

public class GameServerTimerSendPing extends ResettableTimer {
    private VirtualClient virtualClient;

    public GameServerTimerSendPing(long timeout_ms, VirtualClient virtualClient) {
        super(timeout_ms);
        this.virtualClient = virtualClient;
    }

    @Override
    protected void timeout() {
        PingGameMessage pingMessage = new PingGameMessage(virtualClient.getVirtualView().getUsername());
        virtualClient.getVirtualView().sendPing(pingMessage);
        //virtualClient.disconnectPlayerFromGame();
    }

    @Override
    public void reset() {
        super.reset();
    }


}
 //kill thread -> thread.shutdown();