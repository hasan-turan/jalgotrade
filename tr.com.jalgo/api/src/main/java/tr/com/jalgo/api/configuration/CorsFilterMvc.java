//package tr.com.jalgo.api.configuration;
//
//
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//
//
//@Configuration
//public class CorsFilterMvc implements WebMvcConfigurer {
//	 /**
//     * Open cross-domain
//     */
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        // Set the routes that are allowed across the domain
//        registry.addMapping("/**")
//                // Set the domain name that allows cross-domain requests
//                .allowedOriginPatterns("http://localhost")
//                // whether to allow certificates (cookies)
//                .allowCredentials(true)
//                // Set the allowed methods
//                .allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH", "OPTIONS")
//                // Allowed time across domains
//                .maxAge(3600);
//    }
//}
