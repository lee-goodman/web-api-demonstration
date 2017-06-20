package lg.webapidemo;

public class NoSuchObjectException extends RuntimeException {

    private String objectId;

    public NoSuchObjectException(String objectId) {
        this.objectId = objectId;
    }

    public String getObjectId() {
        return objectId;
    }
}
