package lg.webapidemo.position;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

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

    Boolean canOpenDoor(String doorId, UsernamePasswordAuthenticationToken credentials) {
        DoorData doorData = doors.get(doorId);
        if(doorData != null) {
            return doorData.matchCredentials(credentials);
        }
        return false;
    }

    public Set<String> availableDoors() {
        return doors.keySet();
    }

    @Override
    public String toString() {
        return "{" +
                "doors=" + doors +
                '}';
    }
}
