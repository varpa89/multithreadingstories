package threadsorder;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Pavel S Varchenko
 * @since 17.05.2018
 */
public class ControlOrder {
    private static final Semaphore semaphore1 = new Semaphore(1);
    private static final Semaphore semaphore2 = new Semaphore(0);
    private static final Semaphore semaphore3 = new Semaphore(0);

    public static void main(String[] args) {

        Thread worker1 = new Thread(new Worker(semaphore1, semaphore2), "worker1");
        Thread worker2 = new Thread(new Worker(semaphore2, semaphore3), "worker2");
        Thread worker3 = new Thread(new Worker(semaphore3, semaphore1), "worker3");

        worker3.start();
        worker1.start();
        worker2.start();
    }

    public static class Worker implements Runnable {
        private final Semaphore s1;
        private final Semaphore s2;

        Worker(Semaphore s1, Semaphore s2) {
            this.s1 = s1;
            this.s2 = s2;
        }

        @Override
        public void run() {
            try {
                s1.acquire();
                System.out.println(Thread.currentThread()
                                         .getName() + " does work");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                s2.release();
            }
        }
    }
}
