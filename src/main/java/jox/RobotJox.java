package jox;

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

        RouteData routeData = new RouteData(100);
        final String RIGHT_LEG_NAME = "rightLeg";
        final String LEFT_LEG_NAME = "leftLeg";
        Leg rightLeg;
        Leg leftLeg;


        //Broken solution
        rightLeg = new BrokenLeg(RIGHT_LEG_NAME, routeData);
        leftLeg = new BrokenLeg(LEFT_LEG_NAME, routeData);

        //Synchronized solution
        //rightLeg = new SyncLeg(RIGHT_LEG_NAME, routeData);
        //leftLeg = new SyncLeg(LEFT_LEG_NAME, routeData);


        RobotJox robot = new RobotJox(rightLeg, leftLeg);
        robot.go();
    }
}
