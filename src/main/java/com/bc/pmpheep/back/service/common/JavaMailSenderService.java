package com.bc.pmpheep.back.service.common;

import java.util.Properties;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

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
@Service
public class JavaMailSenderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(JavaMailSenderService.class);
    @Autowired
    JavaMailSenderImpl          senderImpl;

    /**
     * 
     * <pre>
     * 功能描述：邮件配置
     * 使用示范：
     *
     * @return
     * </pre>
     */
    private MimeMessage JavaMailSenderConfig() {
        senderImpl.setHost("smtp.qq.com");// 发送邮件服务器
        // 端口号，腾讯邮箱使用SSL协议端口号：465/587，腾讯邮箱使用非SSL协议,163邮箱,新浪邮箱端口号都是：25
        senderImpl.setPort(465);
        senderImpl.setUsername("281235538@qq.com"); // 用户名
        senderImpl.setPassword("kcgewfmviiiqbjcd"); // 密码(腾讯/163邮箱都要使用授权码，新浪邮箱使用密码)

        Properties prop = new Properties();// 参数配置
        prop.put("mail.smtp.ssl.enable", "true");// 使用SSL协议
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        prop.put("mail.smtp.auth", "true"); // 是否需要验证密码
        prop.put("mail.smtp.timeout", "20000");// 超时时间
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
        try {
            // 设置邮件
            MimeMessage mailMessage = JavaMailSenderConfig();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true, "UTF-8");
            // 收件人
            messageHelper.setTo(toMail);
            // 发件人
            messageHelper.setFrom("281235538@qq.com");
            // 标题
            messageHelper.setSubject(title);
            // 内容，true 表示启动HTML格式的邮件
            messageHelper.setText(content, true);
            // 发送邮件
            senderImpl.send(mailMessage);
            isOk = true;
            LOGGER.info("邮件发送成功..");
        } catch (Exception e) {
            LOGGER.error("邮件发送发生异常");
            LOGGER.error(e.getMessage());
            LOGGER.info("进行重发");
            try {
                Thread.sleep(2000);
                senderImpl.send(JavaMailSenderConfig());
            } catch (InterruptedException ie) {
                LOGGER.info("重发邮件发生异常");
                LOGGER.error(ie.getMessage());
            }
        }
        return isOk;
    }
}
