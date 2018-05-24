package com.bc.pmpheep.wx.util;

import com.alibaba.fastjson.JSON;
import com.bc.pmpheep.back.util.HttpUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import com.bc.pmpheep.wechat.util.WXURLUtil;
import org.apache.commons.collections.MapUtils;

import java.util.HashMap;
import java.util.Map;

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
        content = content + String.format(SendWXMessageUtil.getHrefType(MapUtils.getString(params,"hrefType","")),SendWXMessageUtil.getHrefContent(MapUtils.getString(params,"hrefContentType","")));
        map.put("content",content);
        params.put("text",map);
        params.put("agentid",Integer.parseInt(params.get("agentid").toString()));
        params.remove("content");
        String url = params.get("url").toString();
        params.remove("url");
        String responseContent = HttpUtil.doPost(url, JSON.toJSON(params));
        Map backResult = JSON.parseObject(responseContent, Map.class);
        return backResult;
    }

    /**
     *
     * @param hrefType 超链接类型 0 没有超链接 1 前台app 超链接 2 后台app超链接
     * @return
     */
    public static String getHrefType(String hrefType){
        String href = "%s";
        if(StringUtil.isEmpty(hrefType)){
            hrefType = "0";
        }
        switch (hrefType){
            case "0":href="%s";break;
            case "1":href="<a href=\"http://medu.ipmph.com/wx/#/loginm\">%s</a>";break;
            case "2":href="<a href=\"http://medu.ipmph.com/wx/#/loginm\">%s</a>";break;
            default: href="%s";break;
        }
        return href;
    }

    /**
     *
     * @param hrefContentType  1 查看 2 请审核
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
            default: hrefContent="";break;
        }
        return hrefContent;
    }
}
