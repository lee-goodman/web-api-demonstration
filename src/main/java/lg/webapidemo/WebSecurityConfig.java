package lg.webapidemo;

import lg.webapidemo.forum.users.ForumUser;
import lg.webapidemo.forum.users.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .antMatcher("/forum/**")
            .authorizeRequests()
                .mvcMatchers("/forum/users").permitAll()
                .antMatchers("/forum/**").authenticated()
                .anyRequest().permitAll()
                .and()
            .httpBasic()
                .and()
            .csrf()
                .disable();

    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager() {
            private Map<String, ForumUser> secondaryStore = new HashMap<>();

            @Override
            public void createUser(UserDetails user) {
                super.createUser(user);
                this.secondaryStore.put(user.getUsername().toLowerCase(), (ForumUser) user);
            }

            @Override
            public void updateUser(UserDetails user) {
                super.updateUser(user);
                this.secondaryStore.put(user.getUsername().toLowerCase(), (ForumUser) user);
            }

            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return secondaryStore.get(username.toLowerCase()).clone();
            }
        };
        manager.createUser(ForumUser.ADMIN);
        return manager;
    }
}
