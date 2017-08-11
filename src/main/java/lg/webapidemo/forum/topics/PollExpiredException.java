package lg.webapidemo.forum.topics;

public class PollExpiredException extends Exception {

    public PollExpiredException(String message) {
        super(message);
    }
}
