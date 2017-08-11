package lg.webapidemo.forum;

import com.google.common.base.Predicates;
import lg.webapidemo.forum.messages.Message;
import lg.webapidemo.forum.messages.MessageRequest;
import lg.webapidemo.forum.messages.MessageSummary;
import lg.webapidemo.forum.topics.*;
import lg.webapidemo.forum.users.ForumUser;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.google.common.base.Predicates.isNull;
import static com.google.common.base.Predicates.not;

@Component
public class Forum {

    private Map<Integer, Topic> topics = new LinkedHashMap<>();

    public synchronized TopicSummary createTopic(TopicRequest request) {
        Topic newTopic = request.toTopic(topics.size());
        topics.put(topics.size(), newTopic);
        return newTopic.makeSummary();
    }

    public List<TopicSummary> getTopics() {
        return topics.values().stream().filter(not(isNull())).map(Topic::makeSummary).collect(Collectors.toList());
    }

    public List<MessageSummary> getMessages(Integer topicId) {
        return topics.get(topicId).getMessages();
    }

    public MessageSummary addMessage(Integer topicId, ForumUser user, MessageRequest request) {
        return topics.get(topicId).addMessage(user, request);
    }

    public MessageSummary editMessage(Integer topicId, Integer messageId, MessageRequest request) {
        return topics.get(topicId).editMessage(messageId, request);
    }

    public MessageSummary deleteMessage(Integer topicId, Integer messageId) {
        return topics.get(topicId).deleteMessage(messageId);
    }

    public PollSummary vote(Integer topicId, Integer pollId) throws PollExpiredException {
        return topics.get(topicId).vote(pollId);
    }

    public Boolean userOwnsMessage(ForumUser user, Integer topicId, Integer messageId) {
        return topics.get(topicId).userOwnsMessage(user, messageId);
    }

    public void clear() {
        topics.clear();
    }

}
