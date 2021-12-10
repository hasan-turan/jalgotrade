package tr.com.jalgo.api.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

 
@Configuration
 
public class ApplicationConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http
			.csrf().disable()
			//.addFilterBefore(new CorsFilter(), SessionManagementFilter.class)
			//.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
			 
			.authorizeRequests()
//			  .antMatchers(HttpMethod.POST,"/login").permitAll()
//			  .antMatchers("admin/**").authenticated()
//			  .antMatchers("/ws/**").permitAll()
//			  .antMatchers("/gs-guide-websocket/**").permitAll()
			  .anyRequest().permitAll();
//			.and()
//			.formLogin()
//				.usernameParameter("username")
//				.passwordParameter("password")
//				.loginPage("/login")
//				.permitAll()
//				.and()
//			.logout()
//				.permitAll();
		// @formatter:on
	}

}
