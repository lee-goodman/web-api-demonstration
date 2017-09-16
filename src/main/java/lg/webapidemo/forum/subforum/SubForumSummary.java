package lg.webapidemo.forum.subforum;

public class SubForumSummary {

    private Integer id;
    private String owner;
    private Integer topicCount;

    public SubForumSummary(Integer id, String owner, Integer topicCount) {
        this.id = id;
        this.owner = owner;
        this.topicCount = topicCount;
    }

    public Integer getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public Integer getTopicCount() {
        return topicCount;
    }
}
