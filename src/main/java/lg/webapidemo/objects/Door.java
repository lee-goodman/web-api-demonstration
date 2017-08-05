package lg.webapidemo.objects;

public class Door {

    private Boolean isClosed = true;

    public void open() {
        this.isClosed = false;
    }

    public Boolean isClosed() {
        return isClosed;
    }
}
