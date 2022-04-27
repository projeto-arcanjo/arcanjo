package br.com.cmabreu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        registration.setMessageSizeLimit( 3 * 64 * 1024 ); // default : 64 * 1024
        registration.setSendTimeLimit( 30 * 10000 ); // default : 10 * 10000
        registration.setSendBufferSizeLimit( 5 * 512 * 1024 ); // default : 512 * 1024
        registration.setSendTimeLimit( 50 * 10 * 1000 ); // default : 10 * 1000
    }	 
	
	
	 @Override
	 public void registerStompEndpoints(StompEndpointRegistry registry) {
		 registry.addEndpoint("/ws").withSockJS();
	 }	

	 
}
