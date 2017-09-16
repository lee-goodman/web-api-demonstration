package lg.webapidemo.forum.users;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserSummary {

    private String username;
    private String displayName;

    public UserSummary(String username, String displayName) {
        this.username = username;
        this.displayName = displayName;
    }

    public String getUsername() {
        return username;
    }

    public String getDisplayName() {
        return displayName;
    }
}
