package com.bc.pmpheep.back.service.test;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.bc.pmpheep.back.dao.WriterUserDao;
import com.bc.pmpheep.back.po.WriterPermission;
import com.bc.pmpheep.back.po.WriterRole;
import com.bc.pmpheep.back.po.WriterUser;
import com.bc.pmpheep.back.service.WriterUserService;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import com.bc.pmpheep.test.BaseTest;

/**
 * PmphUser 单元测试
 * 
 * @author Administrator
 * 
 */
public class WriterUserTest extends BaseTest {
    Logger            logger = LoggerFactory.getLogger(WriterUserTest.class);

    @Autowired
    WriterUserService userService;
    @Resource
    WriterUserDao     writerUserDao;

    @Test
    public void Count() {
        Long num = writerUserDao.getWriterUserCount();
        logger.info("一共有{}条数据", num);
    }

    /**
     * PmphUser 添加Test
     */
    // @Test
    // @Rollback(Const.ISROLLBACK)
    public void addPmphUserTest() {
        List<Long> roleIdList = new ArrayList<Long>();
        roleIdList.add(1L);
        roleIdList.add(2L);
        roleIdList.add(3L);
        WriterUser user = new WriterUser();
        user.setUsername("admin");
        user.setPassword("1");
        user.setRealname("admin");
        user.setIsDisabled(0);
        WriterUser u = userService.add(user);// 添加用户
        // 查看两个对象的引用是否相等。类似于使用“==”比较两个对象
        Assert.assertNotSame("是否有返回值", null, u.getId());
        WriterUser ps = userService.add(new WriterUser("test1", "123"), roleIdList);// 给单用户添加多个角色
        Assert.assertNotNull(ps);
    }

    /**
     * PmphUser 添加删除
     */
    // @Test
    // @Rollback(Const.ISROLLBACK)
    public void deletePmphUserTest() {
        Integer aInteger = 0;
        try {
            List<Long> userIdList = new ArrayList<Long>();
            userIdList.add(19L);
            // userService.delete(18L);// 按ID删除
            userService.deleteUserAndRole(userIdList);// 删除用户对应的角色
            aInteger = 1;
        } catch (CheckedServiceException e) {
            e.printStackTrace();
        }
        Assert.fail("CheckedServiceException");
        // 查看两个对象的引用是否相等。类似于使用“==”比较两个对象
        Assert.assertSame("是否等于1", 1, aInteger);
    }

    /**
     * 查询
     */
    // @Test
    public void getListsTest() {
        WriterUser wtUser;
        List<WriterUser> pmUsers;
        List<WriterPermission> listPermissions;
        pmUsers = userService.getList();// 查询所有
        Assert.assertNotNull(pmUsers);
        logger.debug(pmUsers.toString());
        wtUser = userService.getByUsername("test1");// 按UserName 查询对象
        Assert.assertNotNull(wtUser);
        logger.debug(wtUser.toString());
        wtUser = userService.get(1L);// 按ID查询对象
        Assert.assertNotNull(wtUser);
        logger.debug(wtUser.toString());
        wtUser = userService.login("test1", "123");
        Assert.assertNotNull(wtUser);
        logger.debug(wtUser.toString());
        pmUsers = userService.getListByRole(1L);
        Assert.assertNotNull(pmUsers);
        logger.debug(pmUsers.toString());
        listPermissions = userService.getListAllResource(1L);
        Assert.assertNotNull(listPermissions);
        logger.debug(listPermissions.size() == 0 ? "null" : listPermissions.get(0).toString());
        List<String> listRoleNameList = userService.getListRoleSnByUser(1L);
        Assert.assertNotNull(listRoleNameList);
        logger.debug(listRoleNameList.size() == 0 ? "null" : listRoleNameList.get(0));
        List<WriterRole> pr = userService.getListUserRole(1L);
        Assert.assertNotNull(pr);
        logger.debug(pr.size() == 0 ? "null" : pr.get(0).toString());
    }

    /**
     * PmphUser 更新方法
     */
    // @Test
    // @Rollback(Const.ISROLLBACK)
    public void updatePmphUserTest() {
        WriterUser pmphUser = new WriterUser();
        pmphUser.setId(18L);
        pmphUser.setUsername("admin1");
        List<Long> userIdList = new ArrayList<Long>();
        userIdList.add(1L);
        userIdList.add(2L);
        WriterUser pu = userService.update(pmphUser);
        Assert.assertNotNull(pu);
        logger.debug(pu.toString());
        WriterUser pu1 = userService.update(pmphUser, userIdList);
        Assert.assertNotNull(pu1);
        logger.debug(pu1.toString());
    }
}
