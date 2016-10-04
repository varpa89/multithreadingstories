package jox;

public class RobotJox {
    private final Leg rightLeg;
    private final Leg leftLeg;

    private RobotJox(RouteData routeData) {
        this.rightLeg = new Leg("rightLeg", routeData);
        this.leftLeg = new Leg("leftLeg", routeData);
    }

    private void go() {
        new Thread(this.leftLeg, this.leftLeg.getName()).start();
        new Thread(this.rightLeg, this.rightLeg.getName()).start();
    }


    public static void main(String[] args) {
        RouteData routeData = new RouteData(100);
        RobotJox robot = new RobotJox(routeData);
        robot.go();
    }
}
