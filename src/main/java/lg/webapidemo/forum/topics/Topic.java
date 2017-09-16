package lg.webapidemo.forum.topics;

import lg.webapidemo.forum.store.DataStore;
import lg.webapidemo.forum.store.DataStoreEntry;
import lg.webapidemo.forum.messages.Message;
import lg.webapidemo.forum.messages.MessageRequest;
import lg.webapidemo.forum.messages.MessageSummary;
import lg.webapidemo.forum.users.ForumUser;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class Topic extends DataStoreEntry {

    private String title;
    private DataStore<Message> messages = new DataStore<>();

    public Topic(TopicRequest request) {
        this.title = request.getTitle();
    }

    public List<MessageSummary> getMessages() {
        return messages.valueStream().map(Message::makeSummary).collect(toList());
    }

    public MessageSummary addMessage(ForumUser user, MessageRequest request) {
        Message message = new Message(user, request);
        messages.add(message);
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
        Message lastMessage = messages.getLast();
        return new TopicSummary(getId(), title, messages.size(), lastMessage==null ? null : lastMessage.getDate());
    }
}
