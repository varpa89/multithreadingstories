package jox;

/**
 * @author p.varchenko
 * @since 05.10.16
 */
public class BrokenLeg extends AbstractLeg {

    BrokenLeg(String name, RouteData routeData) {
        super(name, routeData);
    }

    @Override
    public void run() {
        while (true) {
            if (routeData.finish()) {
                break;
            }
            checkAndMakeStep();
        }
    }
}
