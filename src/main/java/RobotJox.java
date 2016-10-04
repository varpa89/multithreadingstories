import java.util.logging.Level;
import java.util.logging.Logger;

public class RobotJox {

    private final Distance distance;
    private final Leg rightLeg;
    private final Leg leftHtLeg;

    private RobotJox(Distance distance) {
        this.distance = distance;
        this.rightLeg = new Leg("rightLeg", distance);
        this.leftHtLeg = new Leg("leftLeg", distance);
    }

    private static class Distance {

        private Logger logger = Logger.getLogger(Distance.class.getName());
        private final int targetDistance;
        private int traveledDistance = 0;
        private String lastLeg;

        Distance(int targetDistance, String lastLeg) {
            this.targetDistance = targetDistance;
            this.lastLeg = lastLeg;
        }

        String getLastLeg() {
            return this.lastLeg;
        }

        void setLastLeg(String lastLeg) {
            this.lastLeg = lastLeg;
        }

        void addInterval(int interval) {

            int distanceToGo = this.targetDistance - this.traveledDistance;
            if (this.traveledDistance + interval > this.targetDistance) {
                throw new IllegalArgumentException("Interval could not be grater than distance to go. Interval " + interval + ", distance to go is " + distanceToGo);
            }
            this.traveledDistance = this.traveledDistance + interval;
            logger.info("Distance to go: " + distanceToGo);
        }

        int getDistanceToGo() {
            return targetDistance - traveledDistance;
        }

        boolean finish() {
            return this.traveledDistance >= this.targetDistance;
        }
    }

    private static class Leg implements Runnable {

        private Logger logger = Logger.getLogger(Leg.class.getName());
        private final String name;
        private final Distance distance;

        Leg(String name, Distance distance) {
            this.name = name;
            this.distance = distance;
        }

        private String getName() {
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
                    if (!this.distance.getLastLeg().equals(this.getName())) {
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

    private void go() {
        new Thread(this.leftHtLeg, this.leftHtLeg.getName()).start();
        new Thread(this.rightLeg, this.rightLeg.getName()).start();
    }


    public static void main(String[] args) {
        Distance distance = new Distance(100, "rightLeg");
        RobotJox robot = new RobotJox(distance);
        robot.go();
    }
}
