package lg.webapidemo.forum;

import com.google.common.base.Predicates;
import lg.webapidemo.forum.topics.Topic;
import lg.webapidemo.forum.topics.TopicRequest;
import lg.webapidemo.forum.topics.TopicSummary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.google.common.base.Predicates.isNull;
import static com.google.common.base.Predicates.not;

@Component
public class Forum {

    private List<Topic> topics = new ArrayList<>();

    public synchronized TopicSummary createTopic(TopicRequest request) {
        Topic newTopic = new Topic(topics.size(), request);
        topics.add(newTopic);
        return newTopic.makeSummary();
    }

    public List<TopicSummary> getTopics() {
        return topics.stream().filter(not(isNull())).map(Topic::makeSummary).collect(Collectors.toList());
    }

    public void clear() {
        topics.clear();
    }
}
