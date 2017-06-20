package lg.webapidemo;

import java.util.Set;

public class DoorsResponse {

    public Set<String> doors;

    public DoorsResponse(Set<String> doors) {
        this.doors = doors;
    }

    public Set<String> getDoors() {
        return doors;
    }
}
