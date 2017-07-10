package lg.webapidemo.position;

import java.util.*;
import java.util.Map;

public class MapMetadata {

    private java.util.Map<String, DoorData> doors = Collections.emptyMap();

    static MapMetadata empty() {
        return new MapMetadata();
    }

    public Map<String, DoorData> getDoors() {
        return doors;
    }

    @Override
    public String toString() {
        return "{" +
                "doors=" + doors +
                '}';
    }
}
