//package tr.com.jalgo.api.configuration;
//
//import java.io.IOException;
//import java.util.Arrays;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.context.annotation.PropertySources;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//
//@Component
//@Order(Ordered.HIGHEST_PRECEDENCE)
//@PropertySources({ @PropertySource("classpath:application.properties") })
//public class CorsFilter implements Filter {
//
//	@Value("${allowed-origins}")
//	private String allowedOrigins;
//
//	@Override
//	public void init(FilterConfig filterConfig) throws ServletException {
//
//	}
//
//	@Override
//	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
//			throws IOException, ServletException {
//		HttpServletResponse response = (HttpServletResponse) servletResponse;
//		HttpServletRequest request = (HttpServletRequest) servletRequest;
//
////		 String requestOrigin = request.getHeader("origin");
////		 String[] allowedOriginsArray= allowedOrigins.split(",");
////		 
////		 if(Arrays.asList(allowedOriginsArray).contains(requestOrigin)) {
////				response.setHeader("Access-Control-Allow-Origin", requestOrigin);
////		 }
////        response.setHeader("Access-Control-Allow-Origin", "*");
////        response.setHeader("Access-Control-Allow-Methods", "GET,POST,DELETE");
////        response.setHeader("Access-Control-Allow-Headers", "*");
////        response.setHeader("Access-Control-Allow-Credentials", "true");
////        response.setHeader("Access-Control-Max-Age", "180");
//
//		response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
//		response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
//		response.setHeader("Access-Control-Allow-Credentials", "true");
//		response.setHeader("Access-Control-Max-Age", "3600");
//		response.setHeader("Accept-Language", "*");
//		/*
//		 * This two lines of code enables only specific headers. Such as headers being
//		 * sent from react ui headers: { 'X-XSRF-TOKEN': csrfToken, 'Accept':
//		 * 'application/json', 'Content-Type': 'application/json', 'Authorization':
//		 * LocalStorageUtils.getAuthorization(), },
//		 * 
//		 */
//		response.setHeader("Access-Control-Allow-Headers", "authorization, content-type, x-xsrf-token,x-auth-token");
//		response.addHeader("Access-Control-Expose-Headers", "x-xsrf-token,x-auth-token");
//
//		// For HTTP OPTIONS verb/method reply with ACCEPTED status code -- per CORS
//		// handshake
//		if (request.getMethod().equals("OPTIONS")) {
//			response.setStatus(HttpServletResponse.SC_ACCEPTED);
//		} else {
//			filterChain.doFilter(request, response);
//		}
//	}
//
//	@Override
//	public void destroy() {
//
//	}
//}
