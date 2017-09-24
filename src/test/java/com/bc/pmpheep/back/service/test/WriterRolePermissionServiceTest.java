package com.bc.pmpheep.back.service.test;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.po.WriterRolePermission;
import com.bc.pmpheep.back.service.WriterRolePermissionService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.test.BaseTest;

/**
 * WriterRolePermissionService 单元测试
 * 
 * @author 曾庆峰 <791038935@qq.com>
 * 
 */
public class WriterRolePermissionServiceTest extends BaseTest {
    Logger                              logger =
                                               LoggerFactory.getLogger(WriterRolePermissionServiceTest.class);

    @Resource
    private WriterRolePermissionService writerRolePermissionService;

    @Test
    @Rollback(Const.ISROLLBACK)
    public void addWriterRolePermission() throws Exception {
        WriterRolePermission a = new WriterRolePermission(6L, 25L);
        Assert.assertNotNull("addWriterRolePermission是否添加成功",
                             writerRolePermissionService.addWriterRolePermission(a));
        logger.info("----WriterRolePermissionService-------------------------------------------------------------------------");
        logger.info(a.toString());
        a.setRoleId(15L);
        Integer aInteger = writerRolePermissionService.updateWriterRolePermission(a);
        Assert.assertTrue("是否更新成功", aInteger > 0 ? true : false);
        logger.info(aInteger.toString());
        Integer bInteger = writerRolePermissionService.deleteWriterRolePermissionById(2L);
        Assert.assertTrue("是否删除成功", bInteger > 0 ? true : false);
        logger.info(bInteger.toString());
        WriterRolePermission wrp = writerRolePermissionService.getWriterRolePermissionById(3L);
        Assert.assertNotEquals("getWriterRolePermissionById是否为空", null, wrp);
        logger.info(wrp.toString());
    }
}
