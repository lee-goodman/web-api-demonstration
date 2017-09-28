package lg.webapidemo.forum.messages;

import lg.webapidemo.forum.Forum;
import lg.webapidemo.forum.store.DataStoreEntry;
import lg.webapidemo.forum.users.ForumUser;

import java.time.Instant;

public class Message extends DataStoreEntry {

    private String user;
    private ForumUser owner;
    private Instant date;
    private Instant lastEdit;
    private String message;

    public Message(ForumUser user, MessageRequest request) {
        this.user = user.getDisplayName();
        this.owner = user;
        this.date = Instant.now();
        this.lastEdit = Instant.now();
        this.message = request.getMessage();
    }

    public Instant getDate() {
        return date;
    }

    public MessageSummary makeSummary() {
        return new MessageSummary(getId(), user, date, lastEdit, message);
    }

    public Boolean isOwnedBy(ForumUser user) {
        return this.owner.equals(user);
    }

    public void edit(MessageRequest request) {
        this.message = request.getMessage();
        this.lastEdit = Instant.now();
    }
}
