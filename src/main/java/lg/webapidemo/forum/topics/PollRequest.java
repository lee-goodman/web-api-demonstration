package lg.webapidemo.forum.topics;

import java.time.Instant;
import java.util.List;

public class PollRequest extends TopicRequest {

    private List<String> pollOptions;
    private Instant pollExpiresAt;

    public List<String> getPollOptions() {
        return pollOptions;
    }

    public Instant getPollExpiresAt() {
        return pollExpiresAt;
    }

    @Override
    public Topic toTopic() {
        return new Poll(this);
    }
}
