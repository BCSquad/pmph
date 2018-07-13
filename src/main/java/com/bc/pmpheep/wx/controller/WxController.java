package com.bc.pmpheep.wx.controller;

import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.service.PmphUserService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.controller.bean.ResponseBean;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
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
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import com.bc.pmpheep.back.util.SessionUtil;
/**
 * Created by lihuan on 2018/5/8.
 */
@RestController
@RequestMapping("wx")
@Profile("production")
public class WxController {

    @Autowired
    WXQYUserService service;
    @Autowired
    PmphUserService pmphUserService;

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
    public ResponseBean<Map> findUserByUserId (@RequestParam(value = "userId",required = true) String userId,HttpServletRequest request ,HttpSession session){
        if (ObjectUtil.isNull(session)||ObjectUtil.isNull((PmphUser) session.getAttribute(Const.SESSION_PMPH_USER))) {
            throw new CheckedServiceException(CheckedExceptionBusiness.SESSION,
                    CheckedExceptionResult.USER_SESSION,
                    "当前Session会话已过期，请重新登录!");
        }
        Map resultMap = service.findUser(userId);
        if("0".equals(resultMap.get("errcode").toString())){
            PmphUser pmphUser = (PmphUser) session.getAttribute(Const.SESSION_PMPH_USER);
            int count = pmphUserService.updateUserOpenid(userId,pmphUser.getUsername(),pmphUser.getId());
        }else if("60111".equals(resultMap.get("errcode").toString())){
            String errmsgChi = resultMap.get("errmsg").toString().replace("userid not found","未找到微信企业号用户名！");
            resultMap.put("errmsg",errmsgChi);
        }

        return new ResponseBean<Map>(resultMap);
    }

    /**
     * 查看是否绑定
     * @param request
     * @return
     */
    @GetMapping("IsUserId")
    public ResponseBean<Map> IsUserId(HttpServletRequest request,HttpSession session){
        if (ObjectUtil.isNull(session)||ObjectUtil.isNull((PmphUser) session.getAttribute(Const.SESSION_PMPH_USER))) {
            throw new CheckedServiceException(CheckedExceptionBusiness.SESSION,
                    CheckedExceptionResult.USER_SESSION,
                    "当前Session会话已过期，请重新登录!");
        }
        PmphUser pmphUser = (PmphUser) session.getAttribute(Const.SESSION_PMPH_USER);
        Boolean IsUserId = pmphUserService.IsUserId(pmphUser.getId());
        Map map = new HashMap();
        map.put("isUserId",IsUserId);
        if(IsUserId){
            String userId = pmphUserService.getUserId(pmphUser.getId());
            map.put("userId",userId);
        }
        return new ResponseBean<Map>(map);
    }
    /**
     * 解除绑定
     * @param userId
     * @param request
     * @param session
     * @return
     */
    @GetMapping("unblind")
    public ResponseBean<Integer> unblind (@RequestParam(value = "userId",required = true) String userId,HttpServletRequest request ,HttpSession session) {
        if (ObjectUtil.isNull(session) || ObjectUtil.isNull((PmphUser) session.getAttribute(Const.SESSION_PMPH_USER))) {
            throw new CheckedServiceException(CheckedExceptionBusiness.SESSION,
                    CheckedExceptionResult.USER_SESSION,
                    "当前Session会话已过期，请重新登录!");
        }
        int count = pmphUserService.deletePmphUserIdAndWechatId(userId);
        return new ResponseBean<Integer>(count);
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
