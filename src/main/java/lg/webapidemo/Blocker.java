package lg.webapidemo;

public class Blocker {

    private String friendlyName;

    public Blocker(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    @Override
    public String toString() {
        return "Your path is blocked by " + friendlyName;
    }
}
