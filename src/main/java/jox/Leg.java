package jox;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author p.varchenko
 * @since 04.10.16
 */
public class Leg extends AbstractLeg implements Runnable{
    private Logger logger = Logger.getLogger(Leg.class.getName());

    Leg(String name, RouteData routeData) {
        super(name, routeData);
    }

    @Override
    public void run() {
        while (true) {
            synchronized (this.routeData) {
                if (routeData.finish()) {
                    break;
                }
                int stepDistance = (int) (1 + Math.random() * this.routeData.getDistanceToGo());
                if (!this.getName().equals(this.routeData.getLastLeg())) {
                    this.routeData.setLastLeg(this.getName());
                    logger.info(Thread.currentThread().getName() + " start to going " + stepDistance + " meters");
                    try {
                        Thread.sleep(stepDistance);
                    } catch (InterruptedException e) {
                        logger.log(Level.SEVERE, e.getMessage());
                    }
                    this.routeData.addInterval(stepDistance);
                    //logger.info(Thread.currentThread().getName() + " stop to going");
                }
            }
        }
    }
}
