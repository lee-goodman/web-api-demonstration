package lg.webapidemo.forum.topics;

import java.time.Instant;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toMap;

public class Poll extends Topic {

    private Map<Integer, PollEntry> poll;
    private Instant expires;

    public Poll(Integer id, PollRequest request) {
        super(id, request);
        this.poll = IntStream.range(0, request.getPollOptions().size())
                .boxed()
                .collect(toMap(Function.identity(), i -> new PollEntry(request.getPollOptions().get(i))));
        this.expires = request.getPollExpiresAt();
    }

    @Override
    public PollSummary vote(Integer pollId) throws PollExpiredException {
        if(expires.isBefore(Instant.now())) {
            throw new PollExpiredException("This poll has expired, you can no longer vote in this poll");
        }
        poll.get(pollId).vote();
        return (PollSummary) makeSummary();
    }

    @Override
    public TopicSummary makeSummary() {
        return new PollSummary(super.makeSummary(), poll, expires);
    }
}
