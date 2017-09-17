package com.bc.pmpheep.back.servicetest;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.bc.pmpheep.back.po.PmphPermission;
import com.bc.pmpheep.back.service.PmphPermissionService;
import com.bc.pmpheep.test.BaseTest;

/**
 * Pmph 资源Test
 * 
 * @author Administrator
 * 
 */
public class PmphPermissionTest extends BaseTest {
    private static final Logger log = LoggerFactory.getLogger(PmphPermissionTest.class);

    @Autowired
    PmphPermissionService       pmphPermissionService;

    /**
     * PmphPermission 添加Test
     */
    @Test
    // @Rollback(false)
    public void addPmphPermissionTest() {
        PmphPermission pp = new PmphPermission();
        pp.setMenuName("用户管理");
        pp.setUrl("admin/user/add");
        pp.setPeermissionName("用户管理添加");
        pp.setPath("admin:add");
        pp.setParentId(1L);
        pmphPermissionService.addPmphPermission(pp);// 添加资源目录

    }

    /**
     * PmphPermission 更新Test
     */
    // @Test
    // @Rollback(false)
    public void updatePmphPermissionTest() {
        PmphPermission pp = new PmphPermission();
        pp.setId(1L);
        pp.setMenuName("用户管理1");
        pmphPermissionService.updatePmphPermissionById(pp);// 按ID更新资源目录
    }

    /**
     * PmphPermission 删除Test
     */
    // @Test
    // @Rollback(false)
    public void deletePmphPermissionTest() {
        PmphPermission pp = new PmphPermission();
        pp.setId(1L);
        pmphPermissionService.delete(1L);// 按ID删除资源
        pmphPermissionService.deletePmphPermissionById(pp);
    }

    /**
     * PmphPermission 查询Test
     */
    // @Test
    // @Rollback(false)
    public void getPmphPermissionTest() {
        PmphPermission pp = new PmphPermission();
        pp.setId(1L);
        pmphPermissionService.getPmphPermissionById(pp);// 按ID查询资源
        pmphPermissionService.get(1L);
        pmphPermissionService.getListResource();// 查询所有资源
    }
}
