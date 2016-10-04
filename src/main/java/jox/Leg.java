package jox;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author p.varchenko
 * @since 04.10.16
 */
public class Leg implements Runnable{
    private Logger logger = Logger.getLogger(Leg.class.getName());
    private final String name;
    private final Distance distance;

    Leg(String name, Distance distance) {
        this.name = name;
        this.distance = distance;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (this.distance) {
                if (distance.finish()) {
                    break;
                }
                int stepDistance = (int) (1 + Math.random() * this.distance.getDistanceToGo());
                if (!this.getName().equals(this.distance.getLastLeg())) {
                    this.distance.setLastLeg(this.getName());
                    logger.info(Thread.currentThread().getName() + " start to going " + stepDistance + " meters");
                    try {
                        Thread.sleep(stepDistance);
                    } catch (InterruptedException e) {
                        logger.log(Level.SEVERE, e.getMessage());
                    }
                    this.distance.addInterval(stepDistance);
                    //logger.info(Thread.currentThread().getName() + " stop to going");
                }
            }
        }
    }
}
