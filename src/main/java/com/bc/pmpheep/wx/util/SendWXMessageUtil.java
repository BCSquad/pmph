package com.bc.pmpheep.wx.util;

import com.alibaba.fastjson.JSON;
import com.bc.pmpheep.back.service.WxSendMessageService;
import com.bc.pmpheep.back.util.HttpUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import com.bc.pmpheep.wechat.util.WXURLUtil;
import com.bc.pmpheep.back.dao.SendWxMessageDao;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * * touser	否	成员ID列表（消息接收者，多个接收者用‘|’分隔，最多支持1000个）。特殊情况：指定为@all，则向该企业应用的全部成员发送
 toparty	否	部门ID列表，多个接收者用‘|’分隔，最多支持100个。当touser为@all时忽略本参数
 totag	否	标签ID列表，多个接收者用‘|’分隔，最多支持100个。当touser为@all时忽略本参数
 msgtype	是	消息类型，此时固定为：text
 agentid	是	企业应用的id，整型。可在应用的设置页面查看
 content	是	消息内容，最长不超过2048个字节
 safe	否	表示是否是保密消息，0表示否，1表示是，默认0
 */

/**
 * Created by cyx  on 2018/5/24
 */
public class SendWXMessageUtil {



    /**
     * 发送文本消息
     * @param params
     * @return
     * @throws CheckedServiceException
     */
    public static Map sendWxTextMessage(Map<String,Object> params)throws CheckedServiceException {
        if(ObjectUtil.isNull(params)){
            throw new CheckedServiceException("微信推送消息", CheckedExceptionResult.NULL_PARAM,"参数为空");
        }
        if(StringUtil.isEmpty(MapUtils.getString(params,"touser",""))&&StringUtil.isEmpty(MapUtils.getString(params,"toparty",""))&&StringUtil.isEmpty(MapUtils.getString(params,"totag",""))){
            throw new CheckedServiceException("微信推送消息", CheckedExceptionResult.NULL_PARAM,"请求对象参数为空");
        }
        if(StringUtil.isEmpty((MapUtils.getString(params,"msgtype","")))){
            throw new CheckedServiceException("微信推送消息", CheckedExceptionResult.NULL_PARAM,"请求消息类型为空");
        }
        if(!(MapUtils.getString(params,"msgtype","").equals("text"))){
            throw new CheckedServiceException("微信推送消息", CheckedExceptionResult.NULL_PARAM,"请求消息类型非text");
        }
        if(StringUtil.isEmpty((MapUtils.getString(params,"agentid","")))){
            throw new CheckedServiceException("微信推送消息", CheckedExceptionResult.NULL_PARAM,"企业应用的id为空");

        }else if(!com.bc.pmpheep.back.util.StringUtil.isInt(MapUtils.getString(params,"agentid",""))){
            throw new CheckedServiceException("微信推送消息", CheckedExceptionResult.NULL_PARAM,"企业应用的ID非整形");
        }
        if(StringUtil.isEmpty(MapUtils.getString(params,"content",""))){
            throw new CheckedServiceException("微信推送消息", CheckedExceptionResult.NULL_PARAM,"推送内容为空");
        }

        if(MapUtils.getString(params,"content","").length()>2048){
            throw new CheckedServiceException("微信推送消息", CheckedExceptionResult.NULL_PARAM,"推送内容超过2048个字节");
        }
        Map<String,Object> map = new HashMap<String,Object>();
        String content = MapUtils.getString(params,"content","");
        content = content + String.format(SendWXMessageUtil.getHrefType(MapUtils.getString(params,"hrefType",""))
                ,MapUtils.getString(params,"paramUrl","")
                ,SendWXMessageUtil.getHrefContent(MapUtils.getString(params,"hrefContentType","")));

        map.put("content",content);
        params.put("text",map);
        params.put("agentid",Integer.parseInt(params.get("agentid").toString()));
        params.remove("content");
        String url = params.get("url").toString();
        params.remove("url");
        String responseContent = "";
        Map backResult = new HashMap();
        Properties pp = new Properties();
        String isNeedSendMessage = "";
        InputStream fis  = SendWXMessageUtil.class.getClassLoader().getResourceAsStream("pmphapi-config.properties");
        try {
            pp.load(fis);
            isNeedSendMessage =pp.getProperty("isNeedSendMessage").toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if("true".equals(isNeedSendMessage)){
            responseContent= HttpUtil.doPost(url, JSON.toJSON(params));
            backResult = JSON.parseObject(responseContent, Map.class);
        }else{ //消息已经记录，但是不给客户的企业微信发送消息(客户现在不想企业微信收到消息)
            backResult.put("errcode","0");
            backResult.put("errmsg","ok");
            backResult.put("invaliduser","");
        }

        return backResult;
    }

    /**
     *
     * @param hrefType 超链接类型 0 没有超链接 1 前台app 超链接 2 后台app超链接
     * @return
     */
    public static String getHrefType(String hrefType){
        String href = "%s";
        Properties pp = new Properties();
        String rootAdrr = "";
        String appAdrr = "";
        InputStream fis  = SendWXMessageUtil.class.getClassLoader().getResourceAsStream("pmphapi-config.properties");
        try {
            pp.load(fis);
            rootAdrr =pp.getProperty("rootAdrr").toString();
            appAdrr =pp.getProperty("appAdrr").toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        rootAdrr = StringUtil.isEmpty("rootAdrr")?"medu.ipmph.com/pmphwx/":rootAdrr;//  20097r18u8.iask.in 
        appAdrr = StringUtil.isEmpty("appAdrr")?"medu.ipmph.com":appAdrr;//  20097r18u8.iask.in
        if(StringUtil.isEmpty(hrefType)){
            hrefType = "0";
        }
        switch (hrefType){
            case "0":href="%s";break;
            case "1":href="<a class=\"wxmsg_a\" href=\"http://"+appAdrr+"/meduwx%s\">%s</a>";break;
            case "2":href="<a class=\"wxmsg_a\" href=\"http://"+appAdrr+"/pmphvuewx/#/login%s\">%s</a>";break; // 业务测试
           // case "2":href="<a class=\"wxmsg_a\" href=\"http://"+appAdrr+"/wx/#/login%s\">%s</a>";break; //测试 和正式
            case "3":href="<a class=\"wxmsg_a\" href=\"http://"+rootAdrr+"/sso/login?appType=1%s\">%s</a>";break; //教材审核  &UserId&materialId=&declarationId=
            case "4":href="<a class=\"wxmsg_a\" href=\"http://"+rootAdrr+"/sso/login?appType=2%s\">%s</a>";break; //选题申报  &UserId
            case "5":href="<a class=\"wxmsg_a\" href=\"http://"+rootAdrr+"/sso/login?appType=3%s\">%s</a>";break; //图书纠错 &UserId&bookName=&type=&id=
            case "6":href="<a class=\"wxmsg_a\" vueHerf=\"http://"+appAdrr+"/pmphvuewx%s\">%s</a>";break; // 业务测试
           // case "6":href="<a class=\"wxmsg_a\" vueHerf=\"http://"+appAdrr+"/wx%s\">%s</a>";break;  //测试 和正式
            default: href="%s";break;
        }
        return href;
    }

    /**
     *
     * @param hrefContentType  1 查看 2 请审核 3请处理
     * @return
     */
    public static String getHrefContent(String hrefContentType){
        String hrefContent = "";
        if(StringUtil.isEmpty(hrefContentType)){
            hrefContentType = "0";
        }
        switch (hrefContentType){
            case "0":hrefContent="";break;
            case "1":hrefContent="查看";break;
            case "2":hrefContent="请审核";break;
            case "3":hrefContent="请处理";break;
            default: hrefContent="";break;
        }
        return hrefContent;
    }
}
