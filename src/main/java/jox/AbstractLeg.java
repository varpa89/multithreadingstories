package jox;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author p.varchenko
 * @since 04.10.16
 */
abstract class AbstractLeg implements Leg {
    private final String name;
    final RouteData routeData;
    private Logger logger = Logger.getLogger(AbstractLeg.class.getName());

    AbstractLeg(String name, RouteData routeData) {
        this.name = name;
        this.routeData = routeData;
    }

    @Override
    public String getName() {
        return this.name;
    }

    private void makeStep(int stepDistance) {
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

    void checkAndMakeStep(){
        if (!this.getName().equals(this.routeData.getLastLeg())) {
            int stepDistance = (int) (1 + Math.random() * this.routeData.getDistanceToGo());
            makeStep(stepDistance);
        }
    }
}
