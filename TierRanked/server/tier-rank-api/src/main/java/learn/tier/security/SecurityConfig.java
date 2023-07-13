package learn.tier.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final JwtConverter converter;

    public SecurityConfig(JwtConverter converter) {
        this.converter = converter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationConfiguration authConfig) throws Exception {
        //we are using forms...delete line 24?
        http.csrf().disable();

        http.cors();

        http.authorizeRequests()
                .antMatchers("/authenticate").permitAll()
                .antMatchers("/refresh_token").authenticated()
                .antMatchers("/create_account").permitAll()
                //get permissions
                .antMatchers(HttpMethod.GET,
                        "/api/category", "/api/category/*").permitAll()
                .antMatchers(HttpMethod.GET,
                        "/api/comment", "/api/comment/tierlist").permitAll()
                .antMatchers(HttpMethod.GET,
                        "/api/display_profile", "/api/display_profile/*", "/api/display_profile/username/*").permitAll()
                .antMatchers(HttpMethod.GET,
                        "/api/tierlist", "/api/tierlist/user/*", "/api/tierlist/category/*", "/api/tierlist/*").permitAll()
                .antMatchers(HttpMethod.GET,
                        "/api/category", "/api/category/*").permitAll()
                .antMatchers(HttpMethod.GET,
                        "/api/user").permitAll()
                //post permissions
                .antMatchers(HttpMethod.POST,
                        "/api/comment").hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.POST,
                        "/api/tierlist").hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.POST,
                        "/api/display_profile").permitAll()
                //put permissions
                .antMatchers(HttpMethod.PUT,
                        "/api/display_profile/*").hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.PUT,
                        "/api/tierlist/*").hasAnyAuthority("USER", "ADMIN")
                //delete permissions
                .antMatchers(HttpMethod.DELETE,
                        "/api/comment/*").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE,
                        "/api/tierlist/*").hasAnyAuthority("ADMIN")
                .antMatchers("/**").denyAll()
                .and()
                .addFilter(new JwtRequestFilter(authenticationManager(authConfig), converter))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
