package network.timer;

public abstract class ResettableTimer implements Runnable {

    private Object lock = new Object();
    private long timeout_ms;
    private long last;
    private boolean stop;

    public ResettableTimer(long timeout_ms) {
        this.timeout_ms = timeout_ms;
        stop = false;
    }

    public void reset() {
        synchronized (lock) {
            last = System.currentTimeMillis();
            lock.notify();
        }
    }

    public void finish(){
        stop = true;
    }

    @Override
    public void run() {
        try {
            synchronized (lock) {
                while (!stop) {
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