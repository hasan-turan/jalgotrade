package tr.com.jalgo.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
public class FileUploadConfiguration {
	@Bean
	CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setMaxUploadSize(100 *1000* 1000); // 1000 bytes 1000KB, 1000 KB 1 MB, 100 * 1000 * 1000 ~100 MB
		return resolver;
	}
}
