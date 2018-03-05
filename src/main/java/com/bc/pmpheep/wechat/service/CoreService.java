package com.bc.pmpheep.wechat.service;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bc.pmpheep.wechat.po.resp.TextMessage;
import com.bc.pmpheep.wechat.util.MessageUtil;

/**
 * 
 * <pre>
 * 功能描述：处理微信发来的信息
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2018-2-27
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
public class CoreService {
    private static Logger logger = LoggerFactory.getLogger(CoreService.class);

    /**
     * 
     * <pre>
     * 功能描述：解析消息
     * 使用示范：
     *
     * @param msg 消息串
     * @return
     * </pre>
     */
    public static String processRequest(String msg) {
        String respMessage = null;
        try {
            // 默认返回的文本消息内容
            // String respContent =
            String respContent = "请求处理异常，请稍候尝试！";
            // System.out.println("msg_XML==" + msg);
            // xml请求解析
            Map<String, String> requestMap = MessageUtil.parseXml(msg);
            // System.out.println("Event==" + requestMap.get("Event"));
            // 发送方帐号（open_id）
            String fromUserName = requestMap.get("FromUserName");
            // 公众帐号
            String toUserName = requestMap.get("ToUserName");
            // 消息类型
            String msgType = requestMap.get("MsgType");
            // 回复文本消息
            TextMessage textMessage = new TextMessage();
            textMessage.setToUserName(fromUserName);
            textMessage.setFromUserName(toUserName);
            textMessage.setCreateTime(new Date().getTime());
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
            textMessage.setFuncFlag(0);
            // 文本消息
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
                String content = requestMap.get("Content");
                respContent = "您发送的是文本消息！内容是：" + content;
            }
            // 图片消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
                respContent = "您发送的是图片消息！";
            }
            // 地理位置消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
                respContent = "您发送的是地理位置消息！";
            }
            // 链接消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
                respContent = "您发送的是链接消息！";
            }
            // 音频消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
                respContent = "您发送的是音频消息！";
            }
            // 事件推送
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
                // 事件类型
                String eventType = requestMap.get("Event");
                // 自定义菜单点击事件
                if (eventType.equalsIgnoreCase(MessageUtil.EVENT_TYPE_CLICK)) {
                    // 事件KEY值，与创建自定义菜单时指定的KEY值对应
                    String eventKey = requestMap.get("EventKey");
                    // System.out.println("EventKey=" + eventKey);
                    respContent = "您点击的菜单KEY是" + eventKey;
                }
            }
            textMessage.setContent(respContent);
            respMessage = MessageUtil.textMessageToXml(textMessage);
        } catch (Exception e) {
            logger.error("处理消息出异常：{}", e.getMessage());
            // System.out.println(e);
            respMessage = "处理消息出异常。。。";
        }
        return respMessage;
    }

}
