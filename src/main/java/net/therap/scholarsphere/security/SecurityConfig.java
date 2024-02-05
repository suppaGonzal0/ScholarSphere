package net.therap.scholarsphere.security;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

/**
 * @author mehzabinaothoi
 * @since 1/28/24
 */
@Configuration
@Slf4j
public class SecurityConfig {

    private static final String[] PERMIT_ALL_PATHS = {"/css/**", "/js/**", "/images/**", "/", "/terms", "/privacy",
            "/ethics", "/register/**", "/login"};
    private static final String[] ADMIN_PATHS = {"/tag", "/conference", "/pending-approvals/**"};
    private static final String[] REGULAR_USER_PATHS = {"/paper/**", "/profile/**", "/sort", "/search", "/notification",
            "/comment/**", "/conference-search"};
    private static final String[] ALL_USER_PATHS = {"/tag/{id}", "/conference/{id}", "/paper-download"};

    @Autowired
    HttpSession session;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {

        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        jdbcUserDetailsManager.setUsersByUsernameQuery("select email, password, enabled from users where email=?");

        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("select email, authority from authorities where email=?");

        return jdbcUserDetailsManager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(configurer ->
                        configurer.requestMatchers(PERMIT_ALL_PATHS).permitAll()
                                .requestMatchers(ADMIN_PATHS).hasRole("ADMIN")
                                .requestMatchers(REGULAR_USER_PATHS).hasRole("REGULAR")
                                .requestMatchers(ALL_USER_PATHS).hasAnyRole("ADMIN", "REGULAR")
                                .anyRequest()
                                .authenticated()
                )
                .formLogin(form ->
                        form.loginPage("/login")
                                .usernameParameter("email")
                                .passwordParameter("password")
                                .loginProcessingUrl("/authenticate")
                                .permitAll()
                                .successHandler((request, response, authentication) -> {
                                    User user = ((User) authentication.getPrincipal());
                                    session.setAttribute("user", user);
                                    response.sendRedirect("/");
                                })
                                .failureForwardUrl("/login"))
                .logout(LogoutConfigurer::permitAll)
                .exceptionHandling(configurer ->
                        configurer.accessDeniedPage("/access-denied")
                );

        return http.build();
    }
}
