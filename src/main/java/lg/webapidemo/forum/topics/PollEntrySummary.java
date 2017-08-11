package lg.webapidemo.forum.topics;

public class PollEntrySummary {

    private String option;
    private Integer votes;

    public PollEntrySummary(String option, Integer votes) {
        this.option = option;
        this.votes = votes;
    }

    public String getOption() {
        return option;
    }

    public Integer getVotes() {
        return votes;
    }
}
