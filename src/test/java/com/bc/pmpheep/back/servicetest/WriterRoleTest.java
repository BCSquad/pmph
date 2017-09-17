package com.bc.pmpheep.back.servicetest;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.bc.pmpheep.back.po.WriterRole;
import com.bc.pmpheep.back.service.WriterRoleService;
import com.bc.pmpheep.test.BaseTest;

/**
 * WriterRole 单元测试
 * 
 * @author Administrator
 * 
 */
public class WriterRoleTest extends BaseTest {
    private static final Logger log = LoggerFactory.getLogger(WriterRoleTest.class);

    @Autowired
    WriterRoleService           roleService;

    /**
     * PmphRole 添加Test
     */
    // @Test
    // @Rollback(false)
    public void addPmphRoleTest() {
        WriterRole role = new WriterRole();
        role.setRoleName("角色11");
        role.setNote("角色11");
        roleService.add(role);// 添加角色
        roleService.addUserRole(1L, 4L);// 添加用户角色
        roleService.addRoleResource(1L, 4L);// 添加角色资源
    }

    /**
     * PmphRole 修改Test
     */
    // @Test
    // @Rollback(false)
    public void updatePmphRoleTest() {
        WriterRole pr = new WriterRole();
        pr.setId(1L);
        pr.setRoleName("角色");
        roleService.update(pr);
    }

    /**
     * PmphRole 查询Test
     */
    // @Test
    // @Rollback(false)
    public void getPmphRoleTest() {
        roleService.get(1L);// 按ID查询
        roleService.getList();// 查询所有
        roleService.getListRole();//
        roleService.getUserRole(1L, 2L);// 根据用户ID，角色ID查询
        roleService.getListRoleResource(1L);// 根据角色ID查询资源
        roleService.getResourceRole(1L, 1L);// 根据角色ID查询资源
    }

    /**
     * PmphRole 删除Test
     */
    // @Test
    // @Rollback(false)
    public void deletePmphRoleTest() {
        List<Long> list = new ArrayList<Long>();
        list.add(1L);
        Integer count = roleService.delete(4L);// 按ID删除
        log.debug(count.toString());
        roleService.deleteRoleAndResource(list);// 根据角色 id 删除对应资源
        roleService.deleteUserRole(1L, 1L);// 删除用户对应角色
        roleService.deleteUserRoles(1L);// 删除用户对应所有角色
        roleService.deleteRoleResource(1L, 1L);// 删除角色对应的资源
        roleService.deleteRoleAndUser(list);// 删除角色对应所有的用户
    }
}
