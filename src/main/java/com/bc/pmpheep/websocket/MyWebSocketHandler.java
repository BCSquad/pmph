package com.bc.pmpheep.websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.bc.pmpheep.back.util.JsonUtil;

/**
 * WebSocketSession 一个页面new了几个webSocket 后台就应该有几个WebSocketSession
 * 
 * @author mryang
 * 
 */
@Component
// 自动注入需要加的注解
public class MyWebSocketHandler implements WebSocketHandler {
	
	//用来服务前台，登录标识
	public static Map<String, Boolean> loginFlag = new HashMap<String, Boolean>(16); 

    // 当MyWebSocketHandler类被加载时就会创建该Map，随类而生
    private static Map<String, WebSocketSession> userSocketSessionMap;

    static {
        userSocketSessionMap = new HashMap<String, WebSocketSession>(16);
    }

    // 握手实现连接后
    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        String userId = (String) webSocketSession.getAttributes().get("userId");
        // 往变量里面put user
        if (null == userSocketSessionMap.get(userId) || !userSocketSessionMap.get(userId).isOpen()) {
            userSocketSessionMap.put(userId, webSocketSession);
        }
    }

    /**
     * 再次刷新页面就相当于断开WebSocket连接,原本在静态变量userSocketSessionMap中的
     * WebSocketSession会变成关闭状态(close)，但是刷新后的第二次连接服务器创建的
     * 新WebSocketSession(open状态)又不会加入到userSocketSessionMap中,所以这样就无法发送消息
     * 因此应当在关闭连接这个切面增加去除userSocketSessionMap中当前处于close状态的WebSocketSession，
     * 让新创建的WebSocketSession(open状态)可以加入到userSocketSessionMap中
     * 
     * @param webSocketSession
     * @param closeStatus
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus)
    throws Exception {
        String userId = (String) webSocketSession.getAttributes().get("userId");
        System.out.println("WebSocket:" + userId + "close connection");
        Iterator<Map.Entry<String, WebSocketSession>> iterator =
        userSocketSessionMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, WebSocketSession> entry = iterator.next();
            String thisUid = (String) entry.getValue().getAttributes().get("userId");
            if (userId.equals(thisUid)) {
                userSocketSessionMap.remove(userId);
                System.out.println("WebSocket in staticMap:" + userId + "removed");
            }
        }
    }

    /**
     * socket 发送信息的实现
     * 
     * @author Mryang
     * @createDate 2017年9月27日 上午10:58:24
     * @param userIds 用户id的集合 （社内用户1_id,前台作家用户2_id，前台管理员3_id，)
     * @param webScocketMessage
     * @throws IOException
     */
    public void sendWebSocketMessageToUser(List<String> userIds, WebScocketMessage webScocketMessage)
    throws IOException {
        TextMessage textMessage = new TextMessage(JsonUtil.toJSon(webScocketMessage), true);
        for (String userId : userIds) {
            if (null != userId && !"".equals(userId)) {
                WebSocketSession webSocketSession = userSocketSessionMap.get(userId);
                if (webSocketSession != null && webSocketSession.isOpen()) {
                    webSocketSession.sendMessage(textMessage);
                }
            }
        }
    }

    // 接收到了前台新的信息
    @Override
    public void handleMessage(WebSocketSession webSocketSession,
    WebSocketMessage<?> webSocketMessage) throws Exception {
        // 信息内容String msgText=webSocketMessage.getPayload().toString();
    	System.out.println(webSocketMessage.getPayload().toString());
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable)
    throws Exception {
        // System.out.println("传输失败");
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
