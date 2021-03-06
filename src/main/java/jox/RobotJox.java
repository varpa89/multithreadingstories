package jox;

import java.util.concurrent.locks.ReentrantLock;

public class RobotJox {
    private final Leg rightLeg;
    private final Leg leftLeg;

    private RobotJox(Leg rightLeg, Leg leftLeg) {
        this.rightLeg = rightLeg;
        this.leftLeg = leftLeg;
    }

    private void go() {
        new Thread(this.leftLeg, this.leftLeg.getName()).start();
        new Thread(this.rightLeg, this.rightLeg.getName()).start();
    }

    public static void main(String[] args) {

        RouteData routeData = new RouteData(1000);
        final String RIGHT_LEG_NAME = "rightLeg";
        final String LEFT_LEG_NAME = "leftLeg";
        Leg rightLeg;
        Leg leftLeg;

        //Broken solution
        //rightLeg = new BrokenLeg(RIGHT_LEG_NAME, routeData);
        //leftLeg = new BrokenLeg(LEFT_LEG_NAME, routeData);

        //Synchronized solution
        //rightLeg = new SyncLeg(RIGHT_LEG_NAME, routeData);
        //leftLeg = new SyncLeg(LEFT_LEG_NAME, routeData);

        //Reentrant lock solution
        ReentrantLock lock = new ReentrantLock();
        rightLeg = new ReentrantLockLeg(RIGHT_LEG_NAME, routeData, lock);
        leftLeg = new ReentrantLockLeg(LEFT_LEG_NAME, routeData, lock);


        RobotJox robot = new RobotJox(rightLeg, leftLeg);
        robot.go();
    }
}
