package lg.webapidemo.forum.topics;

import com.google.common.base.Predicates;
import lg.webapidemo.forum.messages.Message;
import lg.webapidemo.forum.messages.MessageRequest;
import lg.webapidemo.forum.messages.MessageSummary;
import lg.webapidemo.forum.users.ForumUser;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.google.common.base.Predicates.isNull;
import static com.google.common.base.Predicates.not;
import static java.util.stream.Collectors.toList;

public class Topic {

    private Integer id;
    private String title;
    private Map<Integer, Message> messages = new LinkedHashMap<>();

    public Topic(Integer id, TopicRequest request) {
        this.id = id;
        this.title = request.getTitle();
    }

    public List<MessageSummary> getMessages() {
        return messages.values().stream().filter(not(isNull())).map(Message::makeSummary).collect(toList());
    }

    public synchronized MessageSummary addMessage(ForumUser user, MessageRequest request) {
        Message message = new Message(messages.size(), user, request);
        messages.put(messages.size(), message);
        return message.makeSummary();
    }

    public synchronized MessageSummary editMessage(Integer messageId, MessageRequest request) {
        Message message = messages.get(messageId);
        message.edit(request);
        return message.makeSummary();
    }

    public synchronized MessageSummary deleteMessage(Integer messageId) {
        Message message = messages.get(messageId);
        messages.remove(messageId);
        return message.makeSummary();
    }

    public Boolean userOwnsMessage(ForumUser user, Integer messageId) {
        return messages.get(messageId).isOwnedBy(user);
    }

    public PollSummary vote(Integer pollId) throws PollExpiredException {
        throw new UnsupportedOperationException("This topic is not a poll");
    }

    public TopicSummary makeSummary() {
        return new TopicSummary(id, title, messages.size(), messages.isEmpty() ? null : messages.get(messages.size() - 1).getDate());
    }
}
