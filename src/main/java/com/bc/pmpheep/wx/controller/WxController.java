package com.bc.pmpheep.wx.controller;

import com.bc.pmpheep.controller.bean.ResponseBean;
import com.bc.pmpheep.wx.service.WXQYUserService;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by lihuan on 2018/5/8.
 */
@RestController
@RequestMapping("wx")
@Profile("production")
public class WxController {

    @Autowired
    WXQYUserService service;

    @GetMapping("jssignature")
    public ResponseBean<Map> getJSsignature(HttpServletRequest request) {

        String timestamp = String.valueOf(System.currentTimeMillis());
        String nonceStr = randString(16);
        String strBackUrl = request.getHeader("Referer");

        String signature = service.getSHA1_JS(nonceStr, timestamp, strBackUrl);
        Map<String, String> result = new HashMap<String, String>();
        result.put("appId", service.getWxId());
        result.put("timestamp", timestamp);
        result.put("nonceStr", nonceStr);
        result.put("strBackUrl", strBackUrl);
        result.put("signature", signature);
        return new ResponseBean<Map>(result);
    }

    @GetMapping("findUserByUserId")
    public ResponseBean<Map> findUserByUserId (@RequestParam(value = "userId",required = true) String userId,HttpServletRequest request ){
        return new ResponseBean<Map>(service.findUser(userId));
    }

    @GetMapping("userid")
    public ResponseBean<Map> getUserIdFromCode(@RequestParam("code") String code, HttpServletRequest request) {
        return new ResponseBean<Map>(service.getUserByWXCode(code));
    }


    private static String randString(int len) {
        String baseNumber = "02468135791357902468";
        Assert.state(len > 1, "随机数长度不能小于1");
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < len; i++) {
//            int number = random.nextInt(baseNumber.length());
            sb.append(baseNumber.charAt(random.nextInt(baseNumber.length())));
        }
        return sb.toString();
    }

}
