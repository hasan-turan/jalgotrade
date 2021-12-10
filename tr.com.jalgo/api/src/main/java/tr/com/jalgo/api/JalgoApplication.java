package tr.com.jalgo.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
 
@SpringBootApplication
@ComponentScan(basePackages = {"tr.com.jalgo.*.**"})
public class JalgoApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(JalgoApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		  return builder.sources(JalgoApplication.class);
	}

}
