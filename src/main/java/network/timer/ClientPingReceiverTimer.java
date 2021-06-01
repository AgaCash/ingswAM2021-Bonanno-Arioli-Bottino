package network.timer;

import clientController.LightController;

public class ClientPingReceiverTimer extends ResettableTimer{

    private LightController lightController;

    public ClientPingReceiverTimer(long timeout_ms, LightController lightController) {
        super(timeout_ms);
        this.lightController = lightController;
    }

    @Override
    protected void timeout() {
        lightController.serverDisconnected();
        super.finish();
        //todo: notificare che il server non è più online
        //      terminare il client
    }
}
