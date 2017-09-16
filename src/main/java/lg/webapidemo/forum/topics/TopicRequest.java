package lg.webapidemo.forum.topics;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@JsonTypeInfo(use = Id.NAME, include = As.PROPERTY, property = "type", defaultImpl = TopicRequest.class)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TopicRequest.class, name = "topic"),
        @JsonSubTypes.Type(value = PollRequest.class, name = "poll")
})
public class TopicRequest {

    private String title;

    public String getTitle() {
        return title;
    }

    public Topic toTopic() {
        return new Topic(this);
    }
}
