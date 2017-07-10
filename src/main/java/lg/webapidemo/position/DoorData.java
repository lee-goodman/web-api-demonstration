package lg.webapidemo.position;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class DoorData {

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    Boolean matchCredentials(UsernamePasswordAuthenticationToken credentials) {
        return username.equals(credentials.getName()) && password.equals(credentials.getCredentials());
    }

    @Override
    public String toString() {
        return "{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
