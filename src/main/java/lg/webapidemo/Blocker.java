package lg.webapidemo;

import java.util.Optional;

public class Blocker {

    private String friendlyName;
    private Optional<String> objectId;

    public Blocker(String friendlyName) {
        this(friendlyName, Optional.empty());
    }

    public Blocker(String friendlyName, Optional<String> objectId) {
        this.friendlyName = friendlyName;
        this.objectId = objectId;
    }

    @Override
    public String toString() {
        String identifierPart = objectId.isPresent() ? (" called '" + objectId.get() + "'") : "";
        return "your path is blocked by " + friendlyName + identifierPart;
    }
}
