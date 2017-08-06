package lg.webapidemo.forum.messages;

import lg.webapidemo.forum.users.ForumUser;

import java.time.Instant;

public class Message {

    private Integer id;
    private ForumUser user;
    private Instant date;
    private Instant lastEdit;
    private String message;

    public Message(Integer id, ForumUser user, MessageRequest request) {
        this.id = id;
        this.user = user;
        this.date = Instant.now();
        this.lastEdit = Instant.now();
        this.message = request.getMessage();
    }

    public Instant getDate() {
        return date;
    }

    public MessageSummary makeSummary() {
        return new MessageSummary(id, user.getDisplayName(), date, lastEdit, message);
    }

    public Boolean isOwnedBy(ForumUser user) {
        return this.user.equals(user);
    }

    public void edit(MessageRequest request) {
        this.message = request.getMessage();
        this.lastEdit = Instant.now();
    }
}
