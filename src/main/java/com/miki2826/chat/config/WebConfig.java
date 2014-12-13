package com.miki2826.chat.config;

import com.miki2826.chat.handler.MessageWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.PerConnectionWebSocketHandler;

/**
 * @author michaeld
 */
@Configuration
@EnableWebMvc
@EnableWebSocket
@ComponentScan(basePackages = {"com.miki2826.chat"})
public class WebConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {

    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //Register the websocket handler with SockJS
        registry.addHandler(messagesWebSocketHandler(), "/messages").withSockJS();
    }

    @Bean
    public WebSocketHandler messagesWebSocketHandler() {
        return new PerConnectionWebSocketHandler(MessageWebSocketHandler.class);
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
}
