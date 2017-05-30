package lg.webapidemo.position;

public class Point {

    private Integer x;
    private Integer y;

    public Point(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    Integer getX() {
        return x;
    }

    Integer getY() {
        return y;
    }

    Boolean outOfBounds(Integer mapWidth, Integer mapHeight) {
        return (x < 0 || x >= mapWidth) || (y < 0 || y >= mapHeight);
    }

    public Point translate(Direction direction) {
        switch (direction) {
            case UP: return new Point(x, y-1);
            case DOWN: return new Point(x, y+1);
            case RIGHT: return new Point(x+1, y);
            case LEFT: return new Point(x-1, y);
            default: return this;
        }
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
