package lg.webapidemo;

public class BlockerResponse {

    private String what;
    private String name;

    public BlockerResponse(String what, String name) {
        this.what = what;
        this.name = name;
    }

    public String getWhat() {
        return what;
    }

    public String getName() {
        return name;
    }
}
