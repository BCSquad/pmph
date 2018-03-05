package com.bc.pmpheep.wechat.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bc.pmpheep.wechat.aes.AesException;
import com.bc.pmpheep.wechat.aes.WXBizMsgCrypt;
import com.bc.pmpheep.wechat.service.CoreService;
import com.bc.pmpheep.wechat.util.Constants;

/**
 * 
 * <pre>
 * 功能描述：注解方式打开链接
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
@Controller
public class CoreController {
    private static Logger logger         = LoggerFactory.getLogger(CoreController.class);
    private String        token          = Constants.TOKEN;
    private String        encodingAESKey = Constants.encodingAESKey;
    private String        corpId         = Constants.CORPID;

    /**
     * 
     * <pre>
     * 功能描述： 企业微信服务发送消息绑定API
     * 使用示范：
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws IOException
     * </pre>
     */
    @RequestMapping(value = { "/coreJoin" }, method = RequestMethod.GET)
    public void coreJoinGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException {
        // 微信加密签名
        String msg_signature = request.getParameter("msg_signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");
        // System.out.println("request=" + request.getRequestURL());
        PrintWriter out = response.getWriter();
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        String result = null;
        try {
            WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(token, encodingAESKey, corpId);
            result = wxcpt.VerifyURL(msg_signature, timestamp, nonce, echostr);
        } catch (AesException e) {
            logger.error("AES解密出现异常：{}", e.getMessage());
        }
        if (result == null) {
            result = token;
        }
        out.print(result);
        out.close();
        out = null;
    }

    /**
     * 
     * <pre>
     * 功能描述：企业微信服务响应消息
     * 使用示范：
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws IOException
     * </pre>
     */
    @RequestMapping(value = { "/coreJoin" }, method = RequestMethod.POST)
    public void coreJoinPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException {
        // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        // 微信加密签名
        String msg_signature = request.getParameter("msg_signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 从请求中读取整个post数据
        InputStream inputStream = request.getInputStream();
        String postData = IOUtils.toString(inputStream, "UTF-8");
        // System.out.println(postData);
        String msg = "";
        WXBizMsgCrypt wxcpt = null;
        try {
            wxcpt = new WXBizMsgCrypt(token, encodingAESKey, corpId);
            // 解密消息
            msg = wxcpt.DecryptMsg(msg_signature, timestamp, nonce, postData);
        } catch (AesException e) {
            logger.error("Aes解密出现异常：{}", e.getMessage());
        }
        // System.out.println("msg=" + msg);
        // 调用核心业务类接收消息、处理消息
        String respMessage = CoreService.processRequest(msg);
        // System.out.println("respMessage=" + respMessage);
        String encryptMsg = "";
        try {
            // 加密回复消息
            encryptMsg = wxcpt.EncryptMsg(respMessage, timestamp, nonce);
        } catch (AesException e) {
            logger.error("Aes加密出现异常：{}", e.getMessage());
        }
        // 响应消息
        PrintWriter out = response.getWriter();
        out.print(encryptMsg);
        out.close();
    }
}
