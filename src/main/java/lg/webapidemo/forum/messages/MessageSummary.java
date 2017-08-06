package lg.webapidemo.forum.messages;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.Instant;

public class MessageSummary {

    private Integer id;
    private String user;
    private Instant postDate;
    private Instant editDate;
    private String message;

    public MessageSummary(Integer id, String user, Instant postDate, Instant editDate, String message) {
        this.id = id;
        this.user = user;
        this.postDate = postDate;
        this.editDate = editDate;
        this.message = message;
    }

    public Integer getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public Instant getPostDate() {
        return postDate;
    }

    public Instant getEditDate() {
        return editDate;
    }

    public String getMessage() {
        return message;
    }
}
