package com.bc.pmpheep.back.service.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

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
public class WriterUserServiceTest2 extends BaseTest {
    private static final Logger log = LoggerFactory.getLogger(WriterUserServiceTest2.class);

    @Autowired
    WriterUserService           userService;

    /**
     * PmphUser 添加Test
     */
    // @Test
    // @Rollback(false)
    public void addPmphUserTest() {
        try {
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
            Long a = u.getId();// 返回自增主键
            System.out.println(a);
            WriterUser ps = userService.add(new WriterUser("test1", "123"), roleIdList);// 给单用户添加多个角色
            Assert.assertNotNull(ps);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * PmphUser 添加删除
     */
    // @Test
    // @Rollback(false)
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
        System.out.println(aInteger);
    }

    /**
     * 查询
     */
    // @Test
    public void getListsTest() {
        WriterUser wtUser;
        List<WriterUser> pmUsers;
        List<WriterPermission> listPermissions;
        try {
            pmUsers = userService.getList();// 查询所有
            log.debug(pmUsers.toString());
            wtUser = userService.getByUsername("test1");// 按UserName 查询对象
            log.debug(wtUser.toString());
            wtUser = userService.get(1L);// 按ID查询对象
            log.debug(wtUser.toString());
            wtUser = userService.login("test1", "123");
            log.debug(wtUser.toString());
            pmUsers = userService.getListByRole(1L);
            log.debug(pmUsers.toString());
            listPermissions = userService.getListAllResource(1L);
            log.debug(listPermissions.size() == 0 ? "null" : listPermissions.get(0).toString());
            List<String> listRoleNameList = userService.getListRoleSnByUser(1L);
            log.debug(listRoleNameList.size() == 0 ? "null" : listRoleNameList.get(0));
            List<WriterRole> pr = userService.getListUserRole(1L);
            log.debug(pr.size() == 0 ? "null" : pr.get(0).toString());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * PmphUser 更新方法
     */
    // @Test
    // @Rollback(false)
    public void updatePmphUserTest() {
        WriterUser pmphUser = new WriterUser();
        pmphUser.setId(18L);
        pmphUser.setUsername("admin1");
        List<Long> userIdList = new ArrayList<Long>();
        userIdList.add(1L);
        userIdList.add(2L);
        WriterUser pu = userService.update(pmphUser);
        log.debug(pu.toString());
        WriterUser pu1 = userService.update(pmphUser, userIdList);
        log.debug(pu1.toString());
    }
}
