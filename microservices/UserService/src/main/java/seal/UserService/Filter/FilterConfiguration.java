package seal.UserService.Filter;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class FilterConfiguration extends WebSecurityConfigurerAdapter {
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
            .antMatchers(HttpMethod.GET,"/users","/user/{userId}","/favorites/user/{user_id}").permitAll()
            .antMatchers(HttpMethod.POST, "/user/login","/favorite/user/{user_id}/subject").permitAll()
            .antMatchers(HttpMethod.DELETE, "/favorite/{user_id}/{favorite_id}").permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilterBefore(
                    new JWTGenericFilterBean(),
                    UsernamePasswordAuthenticationFilter.class
            );
    }
    
}
