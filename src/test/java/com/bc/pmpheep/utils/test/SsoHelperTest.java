package com.bc.pmpheep.utils.test;

import com.bc.pmpheep.back.po.OrgUser;
import com.bc.pmpheep.test.BaseTest;
import com.bc.pmpheep.utils.SsoHelper;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    @Test
    public void createSSOAccount() {
        OrgUser orgUser = new OrgUser("gugia", "123");//SSO已存在该账号，理应报错
        SsoHelper ssoHelper = new SsoHelper();
        String msg = ssoHelper.createSSOAccount(orgUser);
        logger.info(msg);
        Assert.assertFalse(msg.equals("success"));
    }

    @Test
    public void resetPassword() {
        SsoHelper ssoHelper = new SsoHelper();
        Assert.assertTrue("修改SSO用户密码失败", ssoHelper.resetPassword("gugia", "123123"));
    }
}
