package lg.webapidemo.movement;

public class Distance {

    private Integer xDistance;
    private Integer yDistance;

    private Distance(Integer xDistance, Integer yDistance) {
        this.xDistance = xDistance;
        this.yDistance = yDistance;
    }

    public static Distance between(Point pointA, Point pointB) {
        return new Distance(pointA.getX() - pointB.getX(), pointA.getY() - pointB.getY());
    }

    @Override
    public String toString() {
        return "Distance{" +
                "xDistance=" + xDistance +
                ", yDistance=" + yDistance +
                '}';
    }
}
