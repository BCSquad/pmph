package com.bc.pmpheep.websocket;

import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.po.WriterUser;
import com.bc.pmpheep.back.util.SessionUtil;

public class HandshakeInterceptor extends HttpSessionHandshakeInterceptor {

    // 握手前
    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest,
    ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler,
    Map<String, Object> map) throws Exception {
        if (serverHttpRequest instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) serverHttpRequest;
            String userType = servletRequest.getServletRequest().getParameter("userType");
            String sessionId = servletRequest.getServletRequest().getParameter("sessionId");

            if (null == userType || "".equals(userType)) {
                return false;
            }
            // HttpSession session = servletRequest.getServletRequest().getSession(false);
            String userId = null;
            // if (null == session) {
            // return false;
            // }
            // userType 1=社内用户/2=作家/3=机构用户
            if ("1".equals(userType)) {
                // PmphUser pmphUser = SessionUtil.getPmphUser();
                PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
                if (null == pmphUser) {
                    return false;
                }
                Long pmphUserId = pmphUser.getId();
                if (null == pmphUserId) {
                    return false;
                }
                userId = userType + "_" + pmphUserId;
            } else if ("2".equals(userType)) {
                WriterUser writerUser = SessionUtil.getWriterUserBySessionId(sessionId);
                if (null == writerUser) {
                    return false;
                }
                Long writerUserId = writerUser.getId();
                if (null == writerUserId) {
                    return false;
                }
                userId = userType + "_" + writerUserId;
            } else if ("3".equals(userType)) {

            } else {
                return false;
            }
            if (null == userId) {
                return false;
            }
            // 为服务器创建WebSocketSession做准备
            map.put("userId", userId);
            return true;
        }
        return false;
    }

    // 握手后
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
    WebSocketHandler wsHandler, Exception ex) {

    }

}