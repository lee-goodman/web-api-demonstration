package lg.webapidemo.forum;

import lg.webapidemo.forum.messages.MessageRequest;
import lg.webapidemo.forum.messages.MessageSummary;
import lg.webapidemo.forum.topics.TopicRequest;
import lg.webapidemo.forum.topics.TopicSummary;
import lg.webapidemo.forum.users.ForumUser;
import lg.webapidemo.forum.users.UserRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RequestMapping("/forum")
@RestController
public class ForumController {

    private static final Logger LOG = LoggerFactory.getLogger(ForumController.class);

    @Autowired
    private InMemoryUserDetailsManager userDetailsManager;

    @Autowired
    private Forum forum;

    @PostMapping("/users")
    public UserRequest addUser(@RequestBody UserRequest user) {
        LOG.info("Creating user: {}", user);
        userDetailsManager.createUser(new ForumUser(user));
        return user;
    }

    @GetMapping("/topics")
    public List<TopicSummary> getTopics() {
        return forum.getTopics();
    }

    @PostMapping("/topics")
    public TopicSummary createTopic(@RequestBody TopicRequest topic) {
        return forum.createTopic(topic);
    }

    @GetMapping("/topics/{topicId}/messages")
    public List<MessageSummary> getMessages(@PathVariable Integer topicId) {
        return null;
    }

    @PostMapping("/topics/{topicId}/messages")
    public MessageSummary addMessage(@PathVariable Integer topicId, @RequestBody MessageRequest message) {
        return null;
    }

    @DeleteMapping("/topics/{topicId}/messages/{messageId}")
    public MessageSummary getMessages(@PathVariable Integer topicId, @PathVariable Integer messageId) {
        return null;
    }

    @PreAuthorize("principal.username == 'admin'")
    @DeleteMapping
    public void wipeForum() {
        forum.clear();
    }

}
