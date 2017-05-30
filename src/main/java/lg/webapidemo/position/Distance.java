package lg.webapidemo.position;

public class Distance {

    private Direction horizontalDirection = null;
    private Integer horizontalDistance = 0;
    private Direction verticalDirection = null;
    private Integer verticalDistance = 0;

    private Distance(Integer xDistance, Integer yDistance) {
        if(xDistance != 0) {
            horizontalDirection = xDistance < 0 ? Direction.LEFT : Direction.RIGHT;
            horizontalDistance = Math.abs(xDistance);
        }
        if(yDistance != 0) {
            verticalDirection = yDistance < 0 ? Direction.UP : Direction.DOWN;
            verticalDistance = Math.abs(yDistance);
        }
    }

    public static Distance between(Point pointA, Point pointB) {
        return new Distance(pointB.getX() - pointA.getX(), pointB.getY() - pointA.getY());
    }

    @Override
    public String toString() {
        String horizontalPart = distanceString(horizontalDirection, horizontalDistance);
        String verticalPart = distanceString(verticalDirection, verticalDistance);
        return "The goal is " + horizontalPart + " " + verticalPart ;
    }

    private String distanceString(Direction direction, Integer distance) {
        String distanceString = "";
        if(direction != null) {
            distanceString = direction.toString().toLowerCase() + " by " + distance;
        }
        return distanceString;
    }
}
