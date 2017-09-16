package lg.webapidemo.forum;

import lg.webapidemo.forum.store.DataStore;
import lg.webapidemo.forum.subforum.SubForum;
import lg.webapidemo.forum.subforum.SubForumSummary;
import lg.webapidemo.forum.users.ForumUser;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class Forum {

    private DataStore<SubForum> subForums = new DataStore<>();

    public SubForum get(Integer id) {
        return subForums.get(id);
    }

    public SubForumSummary create(ForumUser user) {
        SubForum subForum = new SubForum(user);
        subForums.add(subForum);
        return subForum.makeSummary();
    }

    public List<SubForumSummary> list() {
        return subForums.valueStream().map(SubForum::makeSummary).collect(toList());
    }

    public void clear() {
        subForums.clear();
    }

}
