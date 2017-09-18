package com.bc.pmpheep.back.service.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.bc.pmpheep.back.po.WriterPermission;
import com.bc.pmpheep.back.service.WriterPermissionService;
import com.bc.pmpheep.test.BaseTest;

/**
 * Writer 资源Test
 * 
 * @author Administrator
 * 
 */
public class WriterPermissionTest extends BaseTest {
    private static final Logger log = LoggerFactory.getLogger(WriterPermissionTest.class);

    @Autowired
    WriterPermissionService     writerPermissionService;

    /**
     * PmphPermission 添加Test
     */
    @Test
    // @Rollback(false)
    public void addPmphPermissionTest() {
        WriterPermission pp = new WriterPermission();
        pp.setMenuName("用户管理");
        pp.setUrl("admin/user/add");
        pp.setPeermissionName("用户管理添加");
        pp.setPath("admin:add");
        pp.setParentId(1L);
        writerPermissionService.addWriterPermission(pp);// 添加资源目录

    }

    /**
     * PmphPermission 更新Test
     */
    // @Test
    // @Rollback(false)
    public void updatePmphPermissionTest() {
        WriterPermission pp = new WriterPermission();
        pp.setId(1L);
        pp.setMenuName("用户管理1");
        writerPermissionService.updateWriterPermissionById(pp);// 按ID更新资源目录
    }

    /**
     * PmphPermission 删除Test
     */
    // @Test
    // @Rollback(false)
    public void deletePmphPermissionTest() {
        WriterPermission pp = new WriterPermission();
        pp.setId(1L);
        String[] ids = { "1L" };
        writerPermissionService.delete(1L);// 按ID删除资源
        writerPermissionService.deleteWriterPermissionById(ids);
    }

    /**
     * PmphPermission 查询Test
     */
    // @Test
    // @Rollback(false)
    public void getPmphPermissionTest() {
        WriterPermission pp = new WriterPermission();
        pp.setId(1L);
        writerPermissionService.getWriterPermissionByPermissionName("用户管理添加");// 按ID查询资源
        writerPermissionService.get(1L);
        writerPermissionService.getListResource();// 查询所有资源
    }
}
