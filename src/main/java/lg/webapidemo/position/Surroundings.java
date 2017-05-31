package lg.webapidemo.position;

public class Surroundings {

    private MapElement up;
    private MapElement right;
    private MapElement down;
    private MapElement left;

    public Surroundings(MapElement up, MapElement right, MapElement down, MapElement left) {
        this.up = up;
        this.right = right;
        this.down = down;
        this.left = left;
    }

    public MapElement getUp() {
        return up;
    }

    public MapElement getRight() {
        return right;
    }

    public MapElement getDown() {
        return down;
    }

    public MapElement getLeft() {
        return left;
    }

    @Override
    public String toString() {
        return "Up is " + up.getFriendlyName()
                + ", right is " + right.getFriendlyName()
                + ", down is " + down.getFriendlyName()
                + ", and left is " + left.getFriendlyName();
    }
}
