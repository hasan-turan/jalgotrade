package tr.com.jalgo.api.ws.configuration;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
//
//@Configuration
//@EnableWebSocketMessageBroker
//public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
//
//	@Override
//	public void registerStompEndpoints(StompEndpointRegistry registry) {
//		registry.addEndpoint("/binance").setAllowedOrigins("*").withSockJS();
//	}
//
//	@Override
//	public void configureMessageBroker(MessageBrokerRegistry config) {
//		config.setApplicationDestinationPrefixes("/app");
//		config.enableSimpleBroker("/streaming");
//	}
//
//}

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/topic");// config.enableSimpleBroker("/topic/", "/queue/");
		config.setApplicationDestinationPrefixes("/app");
		
		
//		config.setApplicationDestinationPrefixes("/app")
//        .enableStompBrokerRelay("/topic")
//        .setRelayHost("localhost")
//        .setRelayPort(15674)
//        .setClientLogin("guest")
//        .setClientPasscode("guest");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		// registry.addEndpoint("/mywebsockets").setAllowedOrigins("mydomain.com").withSockJS();
		// registry.addEndpoint("/ws").setAllowedOriginPatterns("http://localhost**").withSockJS();
		//registry.addEndpoint("/ws").setAllowedOriginPatterns("http://localhost**").withSockJS();
		
		registry.addEndpoint("/ws").setAllowedOriginPatterns("**localhost**").withSockJS() ;
		
	}

	@Override
	public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
		// TODO Auto-generated method stub
		return false;
	}

}
