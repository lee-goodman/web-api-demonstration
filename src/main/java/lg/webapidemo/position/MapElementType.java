package lg.webapidemo.position;

enum MapElementType {
    EMPTY(".", "nothing"), WALL("x", "a wall"), GOAL("g", "the goal"), PLAYER("p", "nothing");

    private String mapNotation;
    private String friendlyName;

    MapElementType(String mapNotation, String friendlyName) {
        this.mapNotation = mapNotation;
        this.friendlyName = friendlyName;
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
}
