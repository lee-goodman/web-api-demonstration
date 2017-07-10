package lg.webapidemo.position;

import java.util.Optional;

public class MapElement {

    private Optional<String> id = Optional.empty();
    private MapElementType type;

    MapElement(String mapNotationElement) {
        String[] element = mapNotationElement.split("-");
        type = MapElementType.fromMapNotationElement(element[0]);
        if(element.length > 1) {
            id = Optional.of(element[1]);
        }
    }

    Optional<String> getId() {
        return id;
    }

    String getFriendlyName() {
        String name = type.getFriendlyName();
        if(id.isPresent()) {
            name += " called '" + id.get() + "'";
        }
        return name;
    }

    Boolean isA(MapElementType type) {
        return this.type == type;
    }

    Optional<MapElementType> getBlockingObject() {
        if(!type.isPassable()) {
            return Optional.of(type);
        }
        return Optional.empty();
    }

    @Override
    public String toString() {
        return (id.isPresent() ? id.get() + "-" : "") + type;
    }
}
