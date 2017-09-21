package lg.webapidemo.forum;

import lg.webapidemo.forum.users.ForumUser;
import lg.webapidemo.forum.users.UserRequest;
import lg.webapidemo.forum.users.UserSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@RequestMapping("/forum/users")
@RestController
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private InMemoryUserDetailsManager userDetailsManager;
    private List<String> usernames = Collections.synchronizedList(new ArrayList<>());

    private Random random = new Random();

    @PostMapping
    public UserSummary addUser(@RequestBody UserRequest user) {
        if(random.nextBoolean()) {
            throw new IntermittentException("This only happens sometimes, try again and it should work...");
        }
        LOG.info("Creating user: {}", user);
        ForumUser newUser = new ForumUser(user);
        userDetailsManager.createUser(newUser);
        usernames.add(newUser.getUsername());
        Collections.sort(usernames);
        return newUser.makeSummary();
    }

    @GetMapping
    public List<UserSummary> listUsers() {
        List<UserSummary> users = new ArrayList<>();
        usernames.forEach(username -> {
            ForumUser user = (ForumUser) userDetailsManager.loadUserByUsername(username);
            users.add(user.makeSummary());
        });
        return users;
    }

    @PreAuthorize("principal.username == 'admin'")
    @PutMapping("/{username}")
    public UserSummary editUser(@PathVariable String username, @RequestBody UserRequest user) {
        if(!user.getUsername().equals(username)) {
            throw new IllegalArgumentException("Usernames cannot be modified");
        }
        LOG.info("Updating user: {}", user);
        ForumUser updatedUser = new ForumUser(user);
        userDetailsManager.updateUser(updatedUser);
        return updatedUser.makeSummary();
    }

    @PreAuthorize("principal.username == 'admin'")
    @DeleteMapping("/{username}")
    public void deleteUser(@PathVariable String username) {
        LOG.info("Deleting user: {}", username);
        userDetailsManager.deleteUser(username);
    }

    private static class IntermittentException extends RuntimeException {
        public IntermittentException(String message) {
            super(message);
        }
    }
}
