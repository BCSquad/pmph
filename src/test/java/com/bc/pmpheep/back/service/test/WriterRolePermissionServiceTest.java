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
    WriterRolePermission writerRolePermission = new WriterRolePermission(6L, 25L);
    @Test
    @Rollback(Const.ISROLLBACK)
    public void addWriterRolePermission() throws Exception {
    	writerRolePermission.setId(1L);
    	writerRolePermission.setPermissionId(2L);
        Assert.assertNotNull("addWriterRolePermission是否添加成功",
                             writerRolePermissionService.addWriterRolePermission(writerRolePermission));
    }
    @Test
    @Rollback(Const.ISROLLBACK)
    public void testUpdateWriterRolePermission(){
    	writerRolePermission.setId(2L);
    	writerRolePermission.setPermissionId(3L);
    	writerRolePermission.setRoleId(4L);
    	Integer aInteger = writerRolePermissionService.updateWriterRolePermission(writerRolePermission);
        Assert.assertTrue("更新成功失败", aInteger > 0 ? true : false);
    }
    @Test
    @Rollback(Const.ISROLLBACK)
    public void testDeleteWriterRolePermissionById(){
    	Integer bInteger = writerRolePermissionService.deleteWriterRolePermissionById(1L);
        Assert.assertTrue("删除成功失败", bInteger > 0 ? true : false);
    }
    @Test
    @Rollback(Const.ISROLLBACK)
    public void testGetWriterRolePermissionById(){
    	 WriterRolePermission wrp = writerRolePermissionService.getWriterRolePermissionById(3L);
    	 Assert.assertNotNull("获取失败", wrp);
    }
}
