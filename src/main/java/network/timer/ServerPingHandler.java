package network.timer;

import network.messages.pingMessages.PingGameMessage;
import network.messages.pingMessages.PingLobbyMessage;
import view.VirtualClient;

import java.util.concurrent.atomic.AtomicBoolean;

public class ServerPingHandler extends Thread {


    private final Object lock = new Object();
    private long timeout_ms;
    private long last;
    private volatile AtomicBoolean stop;

    private VirtualClient virtualClient;
    private boolean gameState;
    private boolean pingSent;
    //private boolean pongRecived;

    public ServerPingHandler(long timeout_ms, VirtualClient virtualClient){
        this.timeout_ms = timeout_ms;
        this.virtualClient = virtualClient;
        gameState = false;
        pingSent = false;
        stop = new AtomicBoolean(false);
        //pongRecived = false;
        System.out.println("SPH created");
    }

    public void setGameState(boolean gameState) {
        this.gameState = gameState;
        this.pingSent = false;
        System.out.println("new gamestate: "+gameState);
    }

    public void finish(){
        stop.set(true);
    }

    public void reset() {
        synchronized (lock) {
            last = System.currentTimeMillis();
            pingSent = false;
            lock.notify();
        }
    }

    @Override
    public void run() {
        try {
            while (!stop.get()){
                synchronized (lock){
                    lock.wait(timeout_ms);
                    long delta = System.currentTimeMillis() - last;
                    if (delta >= timeout_ms) {
                        if(gameState) {
                            timeoutGame();
                        }else{
                            timeoutLobby();
                        }
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void timeoutGame(){
        if(!pingSent){
            //send ping
            pingSent=true;
            PingGameMessage p = new PingGameMessage(virtualClient.getVirtualView().getUsername());
            virtualClient.getVirtualView().sendPing(p);
            //System.out.println("send ping GAME");
        } else {
            //non ha risposto
            // non è più online
            //disconnettilo dal gioco
            stop.set(true);
            System.out.println("DISCONNESSO DA GAME");
        }
    }

    public void timeoutLobby(){
        if(!pingSent){
            //send ping
            pingSent=true;
            PingLobbyMessage p = new PingLobbyMessage(virtualClient.getVirtualView().getUsername());
            virtualClient.getVirtualView().sendPing(p);
            //System.out.println("send ping LOBBY");
        } else {
            //non ha risposto
            // non è più online
            //disconnettilo dalla lobby
            stop.set(true);
            System.out.println("DISCONNESSO DA LOBBY");
        }
    }

}
