package com.bc.pmpheep.back.service.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.bc.pmpheep.back.plugin.Page;
import com.bc.pmpheep.back.po.PmphPermission;
import com.bc.pmpheep.back.po.PmphRole;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.service.PmphUserService;
import com.bc.pmpheep.back.shiro.kit.ShiroKit;
import com.bc.pmpheep.back.vo.PmphUserManagerVO;
import com.bc.pmpheep.test.BaseTest;
import com.google.gson.Gson;

/**
 * PmphUser 单元测试
 * 
 * @author Administrator
 * 
 */
public class PmphUserServiceTest extends BaseTest {

    Logger                   logger = LoggerFactory.getLogger(PmphUserServiceTest.class);
    Gson                     gson   = new Gson();

    @Autowired
    PmphUserService          userService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

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
        PmphUser user = new PmphUser();
        user.setUsername("admin");
        user.setPassword("1");
        user.setRealname("admin");
        user.setIsDisabled(0);
        PmphUser u = userService.add(user);// 添加用户
        // 查看两个对象的引用是否相等。类似于使用“==”比较两个对象
        Assert.assertNotSame("是否有返回值", null, u.getId());
        PmphUser ps = userService.add(new PmphUser("test1", "123"), roleIdList);// 给单用户添加多个角色
        // 查看对象是否不为空。
        Assert.assertNotNull("是否保存成功", ps);
    }

    /**
     * PmphUser 添加删除
     */
    // @Test
    // @Rollback(Const.ISROLLBACK)
    // public void deletePmphUserTest() {
    // Integer aInteger = 0;
    // try {
    // List<Long> userIdList = new ArrayList<Long>();
    // userIdList.add(19L);
    // userService.deleteUserAndRole(userIdList);// 删除用户对应的角色
    // thrown.expect(CheckedServiceException.class);// 预期异常的属性信息
    // aInteger = 1;
    // } catch (CheckedServiceException e) {
    // e.printStackTrace();
    // }
    // Assert.fail("CheckedServiceException");
    // // 查看两个对象的引用是否相等。类似于使用“==”比较两个对象
    // Assert.assertSame("是否等于1", 1, aInteger);
    // }

    /**
     * PmphUser 更新方法
     */
    // @Test
    // @Rollback(Const.ISROLLBACK)
    public void updatePmphUserTest() {
        PmphUser pmphUser = new PmphUser();
        pmphUser.setId(18L);
        pmphUser.setUsername("test1");
        List<Long> userIdList = new ArrayList<Long>();
        userIdList.add(1L);
        userIdList.add(2L);
        PmphUser pu = userService.update(pmphUser);
        // 查看对象是否不为空。
        Assert.assertNotNull(pu);
        logger.debug(pu.toString());
        PmphUser pu1 = userService.update(pmphUser, userIdList);
        Assert.assertNotNull(pu1);
        logger.debug(pu1.toString());
    }

    /**
     * 查询
     */
    // @Test
    // @Rollback(Const.ISROLLBACK)
    public void getListsTest() {
        PmphUser puPmphUser;
        List<PmphUser> pmUsers;
        List<PmphPermission> listPermissions;
        // try {
        pmUsers = userService.getList();// 查询所有
        Assert.assertNotNull(pmUsers);
        puPmphUser = userService.login("test1", ShiroKit.md5("123", "test1"));// 按UserName
        Assert.assertNotNull(puPmphUser);
        puPmphUser = userService.get(31L);// 按ID查询对象
        Assert.assertNotNull(puPmphUser);
        puPmphUser = userService.login("test1", "123");
        Assert.assertNotNull(puPmphUser);
        pmUsers = userService.getListByRole(1L);
        Assert.assertNotNull(pmUsers);
        listPermissions = userService.getListAllResource(31L);
        Assert.assertNotNull(listPermissions);
        List<String> listRoleNameList = userService.getListRoleSnByUser(31L);
        Assert.assertNotNull(listRoleNameList);
        List<PmphRole> pr = userService.getListUserRole(31L);
        Assert.assertNotNull(pr);
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        // 使用try/catch 块时，catch块后一定要写fail()方法，如果预期异常没有抛出就会导致信息的误报
        // Assert.fail("expected XXXException");
    }

    // @Test
    public void getListByUsernameAndRealname() {
        Page<PmphUserManagerVO, String> page = userService.getListByUsernameAndRealname("1", 1, 10);
        Assert.assertNotNull(page);
        logger.info(gson.toJson(page));
    }

    @Test
    public void getListPmphUserVO() {
        Page<PmphUserManagerVO, PmphUserManagerVO> page = new Page<>();
        PmphUserManagerVO managerVO = new PmphUserManagerVO();
        managerVO.setUsername(null);
        managerVO.setRealname(null);
        managerVO.setPath(null);
        page.setParameter(managerVO);
        page.setPageSize(15);
        page = userService.getListPmphUser(page);
        Assert.assertNotNull(page);
        if (page.getRows().isEmpty()) {
            logger.info("失败了");
        } else {
            logger.info("查找成功{}", page);
        }
    }
}
