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
    }
}
