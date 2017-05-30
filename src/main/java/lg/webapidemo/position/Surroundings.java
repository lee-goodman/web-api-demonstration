package lg.webapidemo.position;

public class Surroundings {

    private MapElement north;
    private MapElement east;
    private MapElement south;
    private MapElement west;

    public Surroundings(MapElement north, MapElement east, MapElement south, MapElement west) {
        this.north = north;
        this.east = east;
        this.south = south;
        this.west = west;
    }

    public MapElement getNorth() {
        return north;
    }

    public MapElement getEast() {
        return east;
    }

    public MapElement getSouth() {
        return south;
    }

    public MapElement getWest() {
        return west;
    }

    @Override
    public String toString() {
        return "North of you is " + north.getFriendlyName()
                + ", east is " + east.getFriendlyName()
                + ", down south is " + south.getFriendlyName()
                + ", and west is " + west.getFriendlyName();
    }
}
