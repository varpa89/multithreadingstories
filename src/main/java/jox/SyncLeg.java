package jox;

/**
 * @author p.varchenko
 * @since 04.10.16
 */
class SyncLeg extends AbstractLeg {

    SyncLeg(String name, RouteData routeData) {
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
                    makeStep(stepDistance);
                }
            }
        }
    }
}
