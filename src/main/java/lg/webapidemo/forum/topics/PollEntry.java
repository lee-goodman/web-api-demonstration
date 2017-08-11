package lg.webapidemo.forum.topics;

import java.util.concurrent.atomic.AtomicInteger;

public class PollEntry {

    private String option;
    private AtomicInteger votes = new AtomicInteger(0);

    public PollEntry(String option) {
        this.option = option;
    }

    public void vote() {
        votes.addAndGet(1);
    }

    public PollEntrySummary makeSummary() {
        return new PollEntrySummary(option, votes.get());
    }
}
