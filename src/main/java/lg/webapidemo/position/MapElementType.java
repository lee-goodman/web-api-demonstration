package lg.webapidemo.position;

enum MapElementType {
    EMPTY(".", "nothing", true),
    WALL("x", "a wall", false),
    GOAL("g", "the goal", true),
    PLAYER("p", "nothing", true),
    DOOR("d", "a door", false);

    private String mapNotation;
    private String friendlyName;
    private Boolean passable;

    MapElementType(String mapNotation, String friendlyName, Boolean passable) {
        this.mapNotation = mapNotation;
        this.friendlyName = friendlyName;
        this.passable = passable;
    }

    static MapElementType fromMapNotationElement(String element) {
        for (MapElementType mapElement : MapElementType.values()) {
            if (mapElement.mapNotation.equals(element)) {
                return mapElement;
            }
        }
        return null;
    }

    public String getFriendlyName() {
        return friendlyName;
    }

    public Boolean isPassable() {
        return passable;
    }
}
