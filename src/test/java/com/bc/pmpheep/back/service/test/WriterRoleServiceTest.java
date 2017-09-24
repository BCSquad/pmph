package com.bc.pmpheep.back.service.test;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bc.pmpheep.back.po.WriterRole;
import com.bc.pmpheep.back.service.WriterRoleService;
import com.bc.pmpheep.test.BaseTest;

/**
 * 
 * @author 曾庆峰 <791038935@qq.com>
 * 
 */
public class WriterRoleServiceTest extends BaseTest {

    Logger            logger = LoggerFactory.getLogger(WriterRoleServiceTest.class);

    @Resource
    WriterRoleService writerRoleService;

    @Test
    public void add() {
        WriterRole writerRole = new WriterRole();
        writerRole.setRoleName("qi");
        writerRole.setNote("ajsdgahsgdyajd");
        writerRole.setSort(12);
        writerRole.setIsDisabled(false);
        writerRole = writerRoleService.add(writerRole);
        Assert.assertNotNull("是否添加成功", writerRole.getId());
        logger.info("添加了{}", writerRole.toString());
    }

    @Test
    public void delete() {
        int num = -1;
        WriterRole writerRole = new WriterRole();
        writerRole.setRoleName("qi");
        writerRole.setNote("ajsdgahsgdyajd");
        writerRole.setSort(12);
        writerRole.setIsDisabled(false);
        writerRole = writerRoleService.add(writerRole);
        Assert.assertNotNull("是否添加成功", writerRole.getId());
        Long id = writerRole.getId();
        num = writerRoleService.delete(id);
        Assert.assertTrue("是否删除", num > 0 ? true : false);
        logger.info("删除了{}条数据", num);
    }

    @Test
    public void update() {
        int num = -1;
        WriterRole writerRole = new WriterRole();
        writerRole.setRoleName("qi");
        writerRole.setNote("ajsdgahsgdyajd");
        writerRole.setSort(12);
        writerRole.setIsDisabled(false);
        WriterRole writerRole2 = new WriterRole();
        writerRole2.setIsDisabled(true);
        writerRole2.setNote("35324354321");
        writerRole2.setRoleName("ssa");
        writerRole2.setSort(22);
        writerRole = writerRoleService.add(writerRole);
        Assert.assertNotNull("是否添加成功", writerRole.getId());
        writerRole2.setId(writerRole.getId());
        num = writerRoleService.update(writerRole2);
        Assert.assertTrue("是否更新成功", num > 0 ? true : false);
        logger.info("修改了{}条数据", num);
    }

    @Test
    public void get() {
        WriterRole writerRole = new WriterRole();
        writerRole.setRoleName("qi");
        writerRole.setNote("ajsdgahsgdyajd");
        writerRole.setSort(12);
        writerRole.setIsDisabled(false);
        WriterRole writerRole2 = new WriterRole();
        writerRole = writerRoleService.add(writerRole);
        Assert.assertNotNull("是否添加成功", writerRole.getId());
        Long id = writerRole.getId();
        writerRole2 = writerRoleService.get(id);
        Assert.assertNotNull("是否查询成功", writerRole2);
        logger.info("查询到了{}", writerRole2.toString());
    }

}
