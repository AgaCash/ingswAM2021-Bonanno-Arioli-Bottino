package network.timer;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class ResettableTimer implements Runnable {

    private final Object lock = new Object();
    private long timeout_ms;
    private long last;
    private volatile AtomicBoolean stop;

    public ResettableTimer(long timeout_ms) {
        this.timeout_ms = timeout_ms;
        stop = new AtomicBoolean(false);
    }

    public void reset() {
        synchronized (lock) {
            last = System.currentTimeMillis();
            lock.notify();
        }
    }

    public void finish(){
        stop.set(true);
    }

    @Override
    public void run() {
        try {
            while (!stop.get()) {
                synchronized (lock) {
                    lock.wait(timeout_ms);
                    long delta = System.currentTimeMillis() - last;
                    if (delta >= timeout_ms) {
                        timeout();
                    }
                }
            }
        } catch (InterruptedException e) {
        }
    }

    protected abstract void timeout();
}