package com.bc.pmpheep.utils.test;

import com.alibaba.fastjson.JSON;
import com.bc.pmpheep.back.po.OrgUser;
import com.bc.pmpheep.back.util.HttpUtil;
import com.bc.pmpheep.general.dto.SsoUser;
import com.bc.pmpheep.test.BaseTest;
import com.bc.pmpheep.utils.SsoHelper;
import javax.annotation.Resource;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
/**
 * SsoHelper单元测试
 *
 * @author L.X <gugia@qq.com>
 */
public class SsoHelperTest extends BaseTest {

    Logger logger = LoggerFactory.getLogger(SsoHelperTest.class);
    
    @Resource
    SsoHelper ssoHelper;

    @Test
    public void createSSOAccount() {
        OrgUser orgUser = new OrgUser("gugia", "123");//SSO已存在该账号，理应报错
        String msg = ssoHelper.createSSOAccount(orgUser);
        logger.info(msg);
        Assert.assertFalse(msg.equals("success"));
    }

    @Test
    public void resetPassword() {
        Assert.assertTrue("修改SSO用户密码失败", ssoHelper.resetPassword("gugia", "123123"));
    }

    @Test
    public  void getUserInfo(){
        System.out.println(JSON.toJSON(ssoHelper.getUserInfo("18301477677","123456")));

    }

    //模拟 SSO 登录 请求
    @Test
    public void ssoRequestTest(){
        final  String url= "http://192.168.100.135/pmpheep/sso/login";
        Map map = new HashMap();
        map.put("TARGET","");
        map.put("JSONResponse","yJzaWduIjoiYXhUYUExOFp0c0FKNjBJWG1KN3VLcnIzRGc2QWI0OFhrdWhzd005UktOWmpsTHlrQlhUeFRiWGVndmhybi81WStNUmxYNTM2MFlUS2VSYmZKbkVpYzBhTDlhR3B3cVp1WEhub213Y2lnNm44M3lLOFdjc1VhQ2dQcVB4TkhCSElKL1g2bjJwM1ZKL3pDTW1PNmFtdjVsOUVGdlJVc3dxMmFiY3VGUmVIdWVEcDVEaWtuUVZucDZRRTNSelBTWTVCck01eDFKY1JuUWdpblhrUDRoazBETDdoNnVsaTlyNmFodTB5YWRiNEpkellCT1RrdGh1WTRRWTc3REhTNUtrbUttRitYS1VTcTN6UWJnRWZOMlg0REhpTllsL0lsU3ZRS2xKNHY5MHJrUEx1a2xuNW1hK1oyVytPMmFQbDFKMjVNL0RUdjBwSjFValRrdDRUWVRQUHRBPT0iLCJhc3NlcnRpb24iOnsibm90QmVmb3JlIjoxNTI5Mzc1MjA4OTM3LCJhcHBOYW1lIjoi5Lq65Y2rZeaVmSIsIl9sb2NhbFVzZXJuYW1lIjoiZ3kiLCJhcHBJZCI6IjI0IiwiX2xvY2FsUGFzc3dvcmQiOiJHdXlhbjc1MjEiLCJhcHBMb2dpblBhc3MiOiJHdXlhbjc1MjEiLCJ1c2VySWQiOiJneSIsIm5vdEFmdGVyIjoxNTI5Mzc1ODA4OTM3LCJzc29UR1QiOiJleUpUU1VkT0lqb2lhbmRIVDJKV2VUSmpabXBWVVhBMFFWQXlWR1pIYTI5TlMxRk5QU0lzSWxSSFZDSTZJbVY1U21waU1qVnJZVmhTY0dJeU5HbFBibk5wV1cxV2JtRlhOVlZoVnpGc1NXcHZlRTVVU1RWTmVtTXdUbFJOTUU1VVp6Rk1RMHB0WVZjMWFHSkdVbkJpVjFWcFQycEZNVTFxYTNwT2VtZDRUWHBSTVU5RVZYTkpia3BzWWxjNU1GcFZhSFpqTTFGcFQybEplRTFxVVhWTlZFa3pUR3BGZVU1NU5IaFBWR2RwWmxOM2FWcEhSakJaVTBrMlpYbEthR1JZVW05VVIxWXlXbGQzYVU5cFNYbEphWGRwV1ZoV01HRkZOV2hpVjFWcFQybE1iR28yVUd0MU5sUnZjbkZVYjNJMFJXbE1RMHBvWkZoU2IxWkliSGRhVTBrMlNXNUNhR016VGpOaU0wcHJTV2wzYVdKSE9XNWhWelZQV1ZjeGJFbHFiMmxhTTJ0cFpsZ3dQU0o5IiwiYXBwTG9naW5JRCI6Imd5In19");
        map.put("j_username","");
        map.put("j_password","");
        map.put("TokenType","com.jiuqi.netrep.integrate.impl.TokenValidatorTWCX4A");
        String responseContent = HttpUtil.doPost(url, JSON.toJSON(map));
        System.out.println(responseContent);
    }

    @Test
    public void jxBase64(){
        String JSONResponse = "eyJzaWduIjoiYXhUYUExOFp0c0FKNjBJWG1KN3VLcnIzRGc2QWI0OFhrdWhzd005UktOWmpsTHlrQlhUeFRiWGVndmhybi81WStNUmxYNTM2MFlUS2VSYmZKbkVpYzBhTDlhR3B3cVp1WEhub213Y2lnNm44M3lLOFdjc1VhQ2dQcVB4TkhCSElKL1g2bjJwM1ZKL3pDTW1PNmFtdjVsOUVGdlJVc3dxMmFiY3VGUmVIdWVEcDVEaWtuUVZucDZRRTNSelBTWTVCck01eDFKY1JuUWdpblhrUDRoazBETDdoNnVsaTlyNmFodTB5YWRiNEpkellCT1RrdGh1WTRRWTc3REhTNUtrbUttRitYS1VTcTN6UWJnRWZOMlg0REhpTllsL0lsU3ZRS2xKNHY5MHJrUEx1a2xuNW1hK1oyVytPMmFQbDFKMjVNL0RUdjBwSjFValRrdDRUWVRQUHRBPT0iLCJhc3NlcnRpb24iOnsibm90QmVmb3JlIjoxNTI5Mzc1MjA4OTM3LCJhcHBOYW1lIjoi5Lq65Y2rZeaVmSIsIl9sb2NhbFVzZXJuYW1lIjoiZ3kiLCJhcHBJZCI6IjI0IiwiX2xvY2FsUGFzc3dvcmQiOiJHdXlhbjc1MjEiLCJhcHBMb2dpblBhc3MiOiJHdXlhbjc1MjEiLCJ1c2VySWQiOiJneSIsIm5vdEFmdGVyIjoxNTI5Mzc1ODA4OTM3LCJzc29UR1QiOiJleUpUU1VkT0lqb2lhbmRIVDJKV2VUSmpabXBWVVhBMFFWQXlWR1pIYTI5TlMxRk5QU0lzSWxSSFZDSTZJbVY1U21waU1qVnJZVmhTY0dJeU5HbFBibk5wV1cxV2JtRlhOVlZoVnpGc1NXcHZlRTVVU1RWTmVtTXdUbFJOTUU1VVp6Rk1RMHB0WVZjMWFHSkdVbkJpVjFWcFQycEZNVTFxYTNwT2VtZDRUWHBSTVU5RVZYTkpia3BzWWxjNU1GcFZhSFpqTTFGcFQybEplRTFxVVhWTlZFa3pUR3BGZVU1NU5IaFBWR2RwWmxOM2FWcEhSakJaVTBrMlpYbEthR1JZVW05VVIxWXlXbGQzYVU5cFNYbEphWGRwV1ZoV01HRkZOV2hpVjFWcFQybE1iR28yVUd0MU5sUnZjbkZVYjNJMFJXbE1RMHBvWkZoU2IxWkliSGRhVTBrMlNXNUNhR016VGpOaU0wcHJTV2wzYVdKSE9XNWhWelZQV1ZjeGJFbHFiMmxhTTJ0cFpsZ3dQU0o5IiwiYXBwTG9naW5JRCI6Imd5In19";
        BASE64Decoder decode = new BASE64Decoder();
        String json = null;
        try {
            json = new String(decode.decodeBuffer(JSONResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(json);

    }
}
