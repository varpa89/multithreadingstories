package jox;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author p.varchenko
 * @since 05.10.16
 */
public class ReentrantLockLeg extends AbstractLeg {
    private final ReentrantLock lock;

    ReentrantLockLeg(String name, RouteData routeData, ReentrantLock lock) {
        super(name, routeData);
        this.lock = lock;
    }

    @Override
    public void run() {
        while (true) {
            lock.lock();
            try {
                if (routeData.finish()) {
                    break;
                }
                checkAndMakeStep();
            } finally {
                lock.unlock();
            }
        }
    }
}
