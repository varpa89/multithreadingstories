package jox;

import java.util.logging.Logger;

/**
 * @author p.varchenko
 * @since 04.10.16
 */
public class RouteData {
    private Logger logger = Logger.getLogger(RouteData.class.getName());
    private final int targetDistance;
    private int traveledDistance = 0;
    private String lastLeg;

    RouteData(int targetDistance) {
        this.targetDistance = targetDistance;
    }

    String getLastLeg() {
        return this.lastLeg;
    }

    void setLastLeg(String lastLeg) {
        this.lastLeg = lastLeg;
    }

    void addInterval(int interval) {

        int distanceToGoBefore = this.targetDistance - this.traveledDistance;
        if (this.traveledDistance + interval > this.targetDistance) {
            throw new IllegalArgumentException("Interval could not be grater than distance to go. Interval " + interval + ", distance to go is " + distanceToGoBefore);
        }
        this.traveledDistance = this.traveledDistance + interval;
        int distanceToGoAfter = this.targetDistance - this.traveledDistance;
        logger.info("Distance to go: " + distanceToGoAfter);
    }

    int getDistanceToGo() {
        return targetDistance - traveledDistance;
    }

    boolean finish() {
        return this.traveledDistance >= this.targetDistance;
    }
}
