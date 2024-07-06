package cn.enaium.utility;

/**
 * @author Enaium
 */
public class Timer {
    private long lastTime;

    public Timer() {
        lastTime = System.currentTimeMillis();
    }

    public boolean hasReached(long delay) {
        return System.currentTimeMillis() - lastTime >= delay;
    }

    public void reset() {
        lastTime = System.currentTimeMillis();
    }

    public void use(Runnable runnable, long delay) {
        if (hasReached(delay)) {
            runnable.run();
            reset();
        }
    }
}
