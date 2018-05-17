package threadsorder;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Pavel S Varchenko
 * @since 17.05.2018
 */
public class Order {

    private static final Lock lock = new ReentrantLock(true);
    private static final Semaphore semaphore = new Semaphore(1, true);

    public static void main(String[] args) {

        Thread rightLeg = new Thread(new Leg(), "rightLeg");
        Thread leftLeg = new Thread(new Leg(), "leftLeg");
        Thread cane = new Thread(new Leg(), "cane");

        cane.start();
        rightLeg.start();
        leftLeg.start();
    }

    public static class Leg implements Runnable {
        ThreadLocal<Integer> stepCount = ThreadLocal.withInitial(() -> 0);

        @Override
        public void run() {
            while (stepCount.get() < 10) {
                try {
                    //lock.lock();
                    semaphore.acquire();
                    stepCount.set(stepCount.get() + 1);
                    System.out.println(Thread.currentThread()
                                             .getName() + " makes step " + stepCount.get());

                    //Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                    //lock.unlock();
                }
            }
        }
    }
}
