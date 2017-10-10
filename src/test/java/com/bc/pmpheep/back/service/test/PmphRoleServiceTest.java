package com.bc.pmpheep.back.service.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.po.PmphRole;
import com.bc.pmpheep.back.service.PmphRoleService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.test.BaseTest;

/**
 * PmphRole 单元测试
 *
 * @author Administrator
 *
 */
public class PmphRoleServiceTest extends BaseTest {

    Logger logger = LoggerFactory.getLogger(PmphRoleServiceTest.class);

    @Autowired
    PmphRoleService roleService;

    /**
     * PmphRole 添加Test
     */
    @Test
    @Rollback(Const.ISROLLBACK)
    public void addPmphRoleTest() {
        PmphRole role = new PmphRole();
        role.setRoleName("角色11");
        role.setNote("角色11");
        Assert.assertNotNull(roleService.add(role));// 添加角色
        roleService.addUserRole(1L, 4L);// 添加用户角色
        List<Long> list = new ArrayList<>();
        list.add(4L);
        list.add(5L);
        roleService.addRoleResource(1L, list);// 添加角色资源
    }

    /**
     * PmphRole 修改Test
     */
    @Test
    @Rollback(Const.ISROLLBACK)
    public void updatePmphRoleTest() {
        PmphRole pr = new PmphRole();
        pr.setId(4L);
        pr.setRoleName("角色");
        Assert.assertNull(roleService.update(pr));
    }

    /**
     * PmphRole 查询Test
     */
    @Test
    @Rollback(Const.ISROLLBACK)
    public void getPmphRoleTest() {
        Assert.assertNotNull(roleService.get(1L));// 按ID查询
        Assert.assertNotNull(roleService.getList());// 查询所有
        Assert.assertNotNull(roleService.listRole());//
        Assert.assertNotNull(roleService.getUserRole(1L, 2L));// 根据用户ID，角色ID查询
        Assert.assertNotNull(roleService.getListRoleResource(1L));// 根据角色ID查询资源
        Assert.assertNotNull(roleService.getResourceRole(1L, 1L));// 根据角色ID查询资源
    }

    /**
     * PmphRole 删除Test
     */
    @Test
    @Rollback(Const.ISROLLBACK)
    public void deletePmphRoleTest() {
        List<Long> list = new ArrayList<Long>();
        list.add(1L);
        Integer count = roleService.delete(4L);// 按ID删除
        Assert.assertTrue("idDelete", count > 0);
        roleService.deleteRoleAndResource(list);// 根据角色 id 删除对应资源
        roleService.deleteUserRole(1L, 1L);// 删除用户对应角色
        roleService.deleteUserRoles(1L);// 删除用户对应所有角色
        roleService.deleteRoleResource(1L, 1L);// 删除角色对应的资源
        Integer ins = roleService.deleteRoleAndUser(list);// 删除角色对应所有的用户
        Assert.assertTrue("idDelete", ins > 0);
    }
}
