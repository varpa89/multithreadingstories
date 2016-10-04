package jox;

public class RobotJox {
    private final Leg rightLeg;
    private final Leg leftLeg;

    private RobotJox(Distance distance) {
        this.rightLeg = new Leg("rightLeg", distance);
        this.leftLeg = new Leg("leftLeg", distance);
    }

    private void go() {
        new Thread(this.leftLeg, this.leftLeg.getName()).start();
        new Thread(this.rightLeg, this.rightLeg.getName()).start();
    }


    public static void main(String[] args) {
        Distance distance = new Distance(100);
        RobotJox robot = new RobotJox(distance);
        robot.go();
    }
}
