package lg.webapidemo.forum.subforum;

import lg.webapidemo.forum.messages.MessageRequest;
import lg.webapidemo.forum.messages.MessageSummary;
import lg.webapidemo.forum.store.DataStore;
import lg.webapidemo.forum.store.DataStoreEntry;
import lg.webapidemo.forum.topics.*;
import lg.webapidemo.forum.users.ForumUser;

import java.util.List;
import java.util.stream.Collectors;

public class SubForum extends DataStoreEntry {

    private ForumUser owner;
    private DataStore<Topic> topics = new DataStore<>();

    public SubForum(ForumUser owner) {
        this.owner = owner;
    }

    public TopicSummary createTopic(TopicRequest request) {
        Topic newTopic = request.toTopic();
        topics.add(newTopic);
        return newTopic.makeSummary();
    }

    public List<TopicSummary> getTopics() {
        return topics.valueStream().map(Topic::makeSummary).collect(Collectors.toList());
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

    public SubForumSummary makeSummary() {
        return new SubForumSummary(getId(), owner.getDisplayName(), topics.size());
    }

}
