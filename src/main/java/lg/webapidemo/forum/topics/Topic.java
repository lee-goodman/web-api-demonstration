package lg.webapidemo.forum.topics;

import lg.webapidemo.forum.messages.Message;

import java.util.ArrayList;
import java.util.List;

public class Topic {

    private Integer id;
    private String title;
    private List<Message> messages = new ArrayList<>();

    public Topic(Integer id, TopicRequest request) {
        this.id = id;
        this.title = request.getTitle();
    }

    public TopicSummary makeSummary() {
        return new TopicSummary(id, title, messages.size(), messages.isEmpty() ? null : messages.get(messages.size() - 1).getDate());
    }
}
