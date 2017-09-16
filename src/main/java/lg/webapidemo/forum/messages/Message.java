package lg.webapidemo.forum.messages;

import lg.webapidemo.forum.store.DataStoreEntry;
import lg.webapidemo.forum.users.ForumUser;

import java.time.Instant;

public class Message extends DataStoreEntry {

    private ForumUser user;
    private Instant date;
    private Instant lastEdit;
    private String message;

    public Message(ForumUser user, MessageRequest request) {
        this.user = user;
        this.date = Instant.now();
        this.lastEdit = Instant.now();
        this.message = request.getMessage();
    }

    public Instant getDate() {
        return date;
    }

    public MessageSummary makeSummary() {
        return new MessageSummary(getId(), user.getDisplayName(), date, lastEdit, message);
    }

    public Boolean isOwnedBy(ForumUser user) {
        return this.user.equals(user);
    }

    public void edit(MessageRequest request) {
        this.message = request.getMessage();
        this.lastEdit = Instant.now();
    }
}
