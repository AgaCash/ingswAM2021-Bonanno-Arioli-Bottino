package network.timer;

import view.VirtualClient;

public class GameServerTimerCheckConnection extends ResettableTimer{

    private VirtualClient virtualClient;
    private GameServerTimerSendPing gameServerTimerSendPing;

    public GameServerTimerCheckConnection(long timeout_ms, VirtualClient virtualClient) {
        super(timeout_ms);
        this.virtualClient = virtualClient;
        //crea il sottotimer per il loop di invio di ping
        gameServerTimerSendPing = new GameServerTimerSendPing(timeout_ms/2, virtualClient);
        Thread t = new Thread(gameServerTimerSendPing);
        t.start();
    }

    @Override
    protected void timeout() {
        virtualClient.disconnectPlayerFromGame();
        this.finish();
        gameServerTimerSendPing.finish();
    }

    @Override
    public void reset() {
        super.reset();
        gameServerTimerSendPing.reset();
    }

}
