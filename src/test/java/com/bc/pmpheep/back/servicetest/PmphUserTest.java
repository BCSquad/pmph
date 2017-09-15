package com.bc.pmpheep.back.servicetest;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.service.PmphUserService;
import com.bc.pmpheep.test.BaseTest;

public class PmphUserTest extends BaseTest {
    private static final Logger log = LoggerFactory.getLogger(PmphUserTest.class);

    @Autowired
    PmphUserService             userService;

    @Test
    public void getListsTest() {
        try {
            List<PmphUser> pmUsers = userService.getList();
            log.debug(pmUsers.toString());
            // Assert.assertNotNull(userService.getList());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    @Rollback(false)
    public void addPmphUserTest() {
        try {
            PmphUser user = new PmphUser();
            user.setUsername("admin1");
            user.setPassword("1");
            user.setRealname("admin1");
            user.setIsDisabled(0);
            PmphUser u = userService.add(user);
            Long a = u.getId();
            System.out.println(a);
            // log.debug(userService.add(user).toString());
            // Assert.assertNotNull(userService.getList());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
