package com.bc.pmpheep.back.util.mail;

import java.util.Properties;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * 
 * <pre>
 * 功能描述：发送邮件
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017-12-22
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
public class JavaMailSenderUtil {
    Logger                       logger    = LoggerFactory.getLogger(JavaMailSenderUtil.class);
    // 发送邮件服务器
    private static final String  HOST      = "smtp.sina.com";
    // 端口号
    private static final Integer PROT      = 25;
    // 用户名
    private static final String  USER_NAME = "sccdbc@sina.com";
    // 密码
    private static final String  PASS_WORD = "sccdbc";
    // 是否需要验证密码
    private static final String  AUTH      = "true";
    // 超时时间
    private static final String  TIME_OUT  = "2000";

    /**
     * 
     * <pre>
     * 功能描述：邮件配置
     * 使用示范：
     *
     * @return
     * </pre>
     */
    private MimeMessage JavaMailSenderConfig(JavaMailSenderImpl senderImpl) {
        // senderImpl.setHost("smtp.qq.com");// 发送邮件服务器
        senderImpl.setHost(HOST);// 发送邮件服务器
        // 端口号，腾讯邮箱使用SSL协议端口号：465/587，腾讯邮箱使用非SSL协议,163邮箱,新浪邮箱端口号都是：25
        senderImpl.setPort(PROT);
        senderImpl.setUsername(USER_NAME); // 用户名
        senderImpl.setPassword(PASS_WORD); // 密码

        Properties prop = new Properties();// 参数配置
        // prop.put("mail.smtp.ssl.enable", "true");// 使用SSL协议
        // prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        prop.put("mail.smtp.auth", AUTH); // 是否需要验证密码
        prop.put("mail.smtp.timeout", TIME_OUT);// 超时时间
        senderImpl.setJavaMailProperties(prop);

        // 创建一个邮件消息
        MimeMessage mailMessage = senderImpl.createMimeMessage();
        return mailMessage;
    }

    /**
     * 
     * <pre>
     * 功能描述：发送邮件
     * 使用示范：
     *
     * @param title 邮件标题
     * @param content 邮件标题内容
     * @param toMail 收件人
     * @return true/false
     * @throws Exception
     * </pre>
     */
    public boolean sendMail(String title, String content, String[] toMail) throws Exception {
        Boolean isOk = false;
        // 创建邮件发送类
        JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
        // 设置邮件
        MimeMessage mailMessage = null;
        try {
            mailMessage = JavaMailSenderConfig(senderImpl);
            // 设置邮件
            MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true, "UTF-8");
            // 收件人
            messageHelper.setTo(toMail);
            // 发件人
            messageHelper.setFrom("sccdbc@sina.com");
            // 标题
            messageHelper.setSubject(title);
            // 内容，true 表示启动HTML格式的邮件
            messageHelper.setText(content, true);
            // 发送邮件
            senderImpl.send(mailMessage);
            isOk = true;
            logger.info("邮件发送成功...");
        } catch (Exception e) {
            logger.error("邮件发送时发生异常:{}", e.getMessage());
            logger.info("邮件进行重发");
            try {
                Thread.sleep(2000);
                senderImpl.send(mailMessage);
                isOk = true;
            } catch (InterruptedException ie) {
                logger.error("重发邮件时发生异常:{}", ie.getMessage());
            }
        }
        return isOk;
    }

    public static void main(String[] args) {
        JavaMailSenderUtil jss = new JavaMailSenderUtil();
        try {
            jss.sendMail("邮件发送测试", "<html><head></head><body><h1>hello!!邮件发送测试</h1>"
                                   + "</body></html>", new String[] { "nyz526@163.com" });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
