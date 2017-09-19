package com.bc.pmpheep.back.service.test;

import javax.annotation.Resource;

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
        try {
            writerRole = writerRoleService.add(writerRole);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (null != writerRole) {
            logger.info("添加了{}", writerRole.toString());
        } else {
            logger.info("失败了");
        }
    }

    @Test
    public void delete() {
        int num = -1;
        WriterRole writerRole = new WriterRole();
        writerRole.setRoleName("qi");
        writerRole.setNote("ajsdgahsgdyajd");
        writerRole.setSort(12);
        writerRole.setIsDisabled(false);
        try {
            writerRole = writerRoleService.add(writerRole);
            Long id = writerRole.getId();
            num = writerRoleService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (-1 != num) {
            logger.info("删除了{}条数据", num);
        } else {
            logger.info("失败了");
        }
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
        try {
            writerRole = writerRoleService.add(writerRole);
            writerRole2.setId(writerRole.getId());
            num = writerRoleService.update(writerRole2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (-1 != num) {
            logger.info("修改了{}条数据", num);
        } else {
            logger.info("失败了");
        }
    }

    @Test
    public void get() {
        WriterRole writerRole = new WriterRole();
        writerRole.setRoleName("qi");
        writerRole.setNote("ajsdgahsgdyajd");
        writerRole.setSort(12);
        writerRole.setIsDisabled(false);
        WriterRole writerRole2 = new WriterRole();
        try {
            writerRole = writerRoleService.add(writerRole);
            Long id = writerRole.getId();
            writerRole2 = writerRoleService.get(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (null != writerRole2) {
            logger.info("查询到了{}", writerRole2.toString());
        } else {
            logger.info("失败了");
        }
    }

}
