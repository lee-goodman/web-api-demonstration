package lg.webapidemo.forum.topics;

import java.time.Instant;

public class TopicSummary {

    private Integer id;
    private String title;
    private Integer messageCount;
    private Instant lastMessageDate;

    public TopicSummary(Integer id, String title, Integer messageCount, Instant lastMessageDate) {
        this.id = id;
        this.title = title;
        this.messageCount = messageCount;
        this.lastMessageDate = lastMessageDate;
    }

    public TopicSummary(TopicSummary summary) {
        this.id = summary.id;
        this.title = summary.title;
        this.messageCount = summary.messageCount;
        this.lastMessageDate = summary.lastMessageDate;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Integer getMessageCount() {
        return messageCount;
    }

    public Instant getLastMessageDate() {
        return lastMessageDate;
    }

}
