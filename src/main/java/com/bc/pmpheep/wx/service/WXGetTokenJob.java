package com.bc.pmpheep.wx.service;

import org.apache.commons.lang.StringUtils;

/**
 * Created by raoqi on 2015-5-15.
 */
public class WXGetTokenJob {

    private WXQYUserService commonService;

    public WXGetTokenJob(WXQYUserService commonService) {
        this.commonService = commonService;
    }

    public void getTokenAndTicket(){
        //获取access_token
        String access_token = commonService.getAccessToken(true);
        System.out.println("WXQY_Access_Token : " + access_token);
        if(StringUtils.isNotEmpty(access_token)){
            //获取jsapi_ticket
            String jsapi_token = commonService.getJsapiTicket(true);
            System.out.println("WXQY_jsapi_ticket : " + jsapi_token);
        }
    }
}
