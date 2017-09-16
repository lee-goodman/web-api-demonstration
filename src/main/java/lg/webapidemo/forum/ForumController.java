package lg.webapidemo.forum;

import lg.webapidemo.forum.messages.MessageRequest;
import lg.webapidemo.forum.messages.MessageSummary;
import lg.webapidemo.forum.subforum.SubForum;
import lg.webapidemo.forum.subforum.SubForumSummary;
import lg.webapidemo.forum.topics.PollSummary;
import lg.webapidemo.forum.topics.TopicRequest;
import lg.webapidemo.forum.topics.TopicSummary;
import lg.webapidemo.forum.users.ForumUser;
import lg.webapidemo.forum.users.UserRequest;
import lg.webapidemo.forum.users.UserSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping("/forum")
@RestController
public class ForumController {

    private static final Logger LOG = LoggerFactory.getLogger(ForumController.class);

    @Autowired
    private Forum forum;


    @GetMapping("/subForums")
    public List<SubForumSummary> getSubForum() {
        return forum.list();
    }

    @PostMapping("/subForums")
    public SubForumSummary createSubForum(@AuthenticationPrincipal ForumUser user) {
        return forum.create(user);
    }

    @GetMapping("/subForums/{forumId}/topics")
    public List<TopicSummary> getTopics(@PathVariable Integer forumId) {
        return forum.get(forumId).getTopics();
    }

    @PostMapping("/subForums/{forumId}/topics")
    public TopicSummary createTopic(@PathVariable Integer forumId, @RequestBody TopicRequest topic) {
        return forum.get(forumId).createTopic(topic);
    }

    @PutMapping("/subForums/{forumId}/topics/{topicId}")
    public TopicSummary editTopic(@PathVariable Integer forumId, @PathVariable Integer topicId, @RequestBody TopicRequest topic) {
        return forum.get(forumId).editTopic(topicId, topic);
    }

    @DeleteMapping("/subForums/{forumId}/topics/{topicId}")
    public void deleteTopic(@PathVariable Integer forumId, @PathVariable Integer topicId) {
        forum.get(forumId).removeTopic(topicId);
    }

    @GetMapping("/subForums/{forumId}/topics/{topicId}/messages")
    public List<MessageSummary> getMessages(@PathVariable Integer forumId, @PathVariable Integer topicId) {
        return forum.get(forumId).getMessages(topicId);
    }

    @PostMapping("/subForums/{forumId}/topics/{topicId}/messages")
    public MessageSummary addMessage(@PathVariable Integer forumId, @PathVariable Integer topicId, @RequestBody MessageRequest message) {
        return forum.get(forumId).addMessage(topicId, getCurrentUser(), message);
    }

    @PutMapping("/subForums/{forumId}/topics/{topicId}/votes/{pollId}")
    public PollSummary vote(@PathVariable Integer forumId, @PathVariable Integer topicId, @PathVariable Integer pollId) throws Exception {
        return forum.get(forumId).vote(topicId, pollId);
    }

    @PutMapping("/subForums/{forumId}/topics/{topicId}/messages/{messageId}")
    public MessageSummary editMessage(@PathVariable Integer forumId, @PathVariable Integer topicId, @PathVariable Integer messageId, @RequestBody MessageRequest message) {
        SubForum subForum = this.forum.get(forumId);
        if(!subForum.userOwnsMessage(getCurrentUser(), topicId, messageId)) {
            throw new AccessDeniedException("User does not own this message!");
        }
        return subForum.editMessage(topicId, messageId, message);
    }

    @DeleteMapping("/subForums/{forumId}/topics/{topicId}/messages/{messageId}")
    public MessageSummary getMessages(@PathVariable Integer forumId, @PathVariable Integer topicId, @PathVariable Integer messageId) {
        SubForum subForum = this.forum.get(forumId);
        if(!subForum.userOwnsMessage(getCurrentUser(), topicId, messageId)) {
            throw new AccessDeniedException("User does not own this message!");
        }
        return subForum.deleteMessage(topicId, messageId);
    }

    @PreAuthorize("principal.username == 'admin'")
    @DeleteMapping
    public void wipeForum() {
        forum.clear();
    }

    private ForumUser getCurrentUser() {
        LOG.info("Current user: {}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return (ForumUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
