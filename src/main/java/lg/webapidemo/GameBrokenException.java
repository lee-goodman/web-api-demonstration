package lg.webapidemo;

public class GameBrokenException extends RuntimeException {

    public GameBrokenException(String message) {
        super(message);
    }
}
