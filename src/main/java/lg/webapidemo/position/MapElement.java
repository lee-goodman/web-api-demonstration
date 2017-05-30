package lg.webapidemo.position;

enum MapElement {
    EMPTY(".", "nothing"), WALL("x", "a wall"), GOAL("g", "the goal"), PLAYER("p", "nothing");

    private String mapNotation;
    private String friendlyName;

    MapElement(String mapNotation, String friendlyName) {
        this.mapNotation = mapNotation;
        this.friendlyName = friendlyName;
    }

    static MapElement fromMapNotationElement(String element) {
        for (MapElement mapElement : MapElement.values()) {
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
