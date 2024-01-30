package net.therap.schoalrsphere.security;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import net.therap.schoalrsphere.exception.InsufficientPrivilegeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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
				.authorizeHttpRequests(configurer ->
								configurer
										.requestMatchers("/").permitAll()
										.requestMatchers("/css/**", "/js/**", "/images/**", "/terms", "/privacy", "/ethics").permitAll()
//								.requestMatchers("/").hasAnyRole("ADMIN", "REGULAR")

										.anyRequest()
										.authenticated()
				)
				.formLogin(form ->
						form
								.loginPage("/login")
								.usernameParameter("email")
								.passwordParameter("password")
								.loginProcessingUrl("/authenticate")
								.permitAll()
								.successHandler(
										(request, response, authentication) -> {
											User user = ((User) authentication.getPrincipal());

											session.setAttribute("user", user);

											response.sendRedirect("/");
										})
								.failureForwardUrl("/login")
				)
				.logout(
						logout -> logout.permitAll()
				)
				.exceptionHandling(configurer ->
						configurer.accessDeniedHandler((request, response, accessDeniedException) -> {
							throw new InsufficientPrivilegeException("Access Denied");
						})
				);

		return http.build();
	}
}
