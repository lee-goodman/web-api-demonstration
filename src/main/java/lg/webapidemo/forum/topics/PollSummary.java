package lg.webapidemo.forum.topics;

import java.time.Instant;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

public class PollSummary extends TopicSummary {

    private Map<Integer, PollEntrySummary> poll;
    private Instant pollExpiresAt;

    public PollSummary(TopicSummary summary, Map<Integer, PollEntry> poll, Instant pollExpiresAt) {
        super(summary);
        this.poll = poll.entrySet().stream().collect(toMap(entry -> entry.getKey(), entry -> entry.getValue().makeSummary()));
        this.pollExpiresAt = pollExpiresAt;
    }

    public Map<Integer, PollEntrySummary> getPoll() {
        return poll;
    }

    public Instant getPollExpiresAt() {
        return pollExpiresAt;
    }
}
