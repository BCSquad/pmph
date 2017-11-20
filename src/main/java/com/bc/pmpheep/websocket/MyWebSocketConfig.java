package com.bc.pmpheep.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * 
 * websocket 配置类
 *
 * @author Mryang
 *
 * @createDate 2017年9月27日 上午10:13:25
 *
 */
//@Configuration
//@EnableWebMvc
//@EnableWebSocket
public class MyWebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {
		
		@Autowired
		private MyWebSocketHandler handler;
		
		@Override
        public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        	//前台 可以使用websocket环境
            registry.addHandler(handler,"/websocket").addInterceptors(new HandshakeInterceptor()).setAllowedOrigins("*");
            //前台 不可以使用websocket环境，则使用sockjs进行模拟连接
            registry.addHandler(handler, "/sockjs/websocket").addInterceptors(new HandshakeInterceptor()).setAllowedOrigins("*").withSockJS();
        }
}











