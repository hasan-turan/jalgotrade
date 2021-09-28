package tr.com.jalgo.api.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http
			.csrf().disable()
			.authorizeRequests()
			  .antMatchers(HttpMethod.POST,"/login").permitAll()
			  .antMatchers("admin/**").authenticated()
			  .anyRequest().permitAll()
			.and()
			.formLogin()
				.usernameParameter("username")
				.passwordParameter("password")
				.loginPage("/login")
				.permitAll()
				.and()
			.logout()
				.permitAll();
		// @formatter:on
	}

}
