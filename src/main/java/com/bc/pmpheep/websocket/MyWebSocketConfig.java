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
//
//@Configuration  //指明该类为Spring 配置类
//@EnableWebMvc    // 声明该类支持WebMvc
//@EnableWebSocket  // 声明该类支持WebSocket
public class MyWebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {
		
		@Autowired
		private MyWebSocketHandler myWebSocketHandler;
		
		@Override
        public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        	//前台 可以使用websocket环境
            registry.addHandler(myWebSocketHandler,"/websocket").addInterceptors(new HandshakeInterceptor()).setAllowedOrigins("*");
            //前台 不可以使用websocket环境，则使用sockjs进行模拟连接
            registry.addHandler(myWebSocketHandler, "/sockjs/websocket").addInterceptors(new HandshakeInterceptor()).setAllowedOrigins("*").withSockJS();
        }
}











