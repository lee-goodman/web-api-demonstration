package lg.webapidemo.forum.messages;

import lg.webapidemo.forum.users.ForumUser;

import java.time.Instant;

public class Message {

    private Integer id;
    private ForumUser user;
    private Instant date;
    private String message;

    public Instant getDate() {
        return date;
    }
}
