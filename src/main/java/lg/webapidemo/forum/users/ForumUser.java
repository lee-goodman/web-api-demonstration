package lg.webapidemo.forum.users;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Collections;

public class ForumUser extends User {

    public static final ForumUser ADMIN = new ForumUser("admin", "admin123", "administrator");

    private String displayName;

    private ForumUser(String username, String password, String displayName) {
        super(username, password, Collections.emptyList());
        this.displayName = displayName;
    }

    public ForumUser(UserRequest request) {
        super(request.getUsername(), request.getPassword(), Collections.emptyList());
        this.displayName = request.getDisplayName();
    }

    public String getDisplayName() {
        return displayName;
    }

    public ForumUser clone() {
        return new ForumUser(this.getUsername(), this.getPassword(), this.displayName);
    }
}
