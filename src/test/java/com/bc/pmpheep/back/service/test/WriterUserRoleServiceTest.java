package com.bc.pmpheep.back.service.test;

import java.util.Random;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.po.WriterUserRole;
import com.bc.pmpheep.back.service.WriterUserRoleService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.test.BaseTest;

/**
 * AreaDao 单元测试
 * 
 * @author mryang
 */
public class WriterUserRoleServiceTest extends BaseTest {
    Logger                        logger = LoggerFactory.getLogger(WriterUserRoleServiceTest.class);

    @Resource
    private WriterUserRoleService testService;
    Random r = new Random();
    
    @Test
    @Rollback(Const.ISROLLBACK)
    public void testAddWriterUserRole() throws Exception {
        // 新增
        WriterUserRole writerUserRole=this.addWriterUserRole();
        Assert.assertNotNull("是否保存成功", writerUserRole.getId());
        //logger.info(testPar.toString());
    }
    @Test
    @Rollback(Const.ISROLLBACK)
    public void testUpdateWriterUserRole(){
    	WriterUserRole writerUserRole=this.addWriterUserRole();
    	writerUserRole.setRoleId(3L);
    	writerUserRole.setUserId(4L);
        Integer aInteger = testService.updateWriterUserRole(writerUserRole);
        Assert.assertTrue("是否修改成功", aInteger > 0 ? true : false);
    }
    @Test
    @Rollback(Const.ISROLLBACK)
    public void testDeleteWriterUserRoleById(){
    	WriterUserRole writerUserRole=this.addWriterUserRole();
    	Integer bInteger = testService.deleteWriterUserRoleById(writerUserRole.getId());
        Assert.assertTrue("是否删除成功", bInteger > 0 ? true : false);
    }
    @Test
    @Rollback(Const.ISROLLBACK)
    public void testGetWriterUserRoleById(){
    	WriterUserRole writerUserRole=this.addWriterUserRole();
    	WriterUserRole wur = testService.getWriterUserRoleById(writerUserRole.getId());
        Assert.assertNotNull("不为空", wur);
    }
    private WriterUserRole addWriterUserRole(){
    	WriterUserRole writerUserRole=testService.addWriterUserRole(new WriterUserRole(1L, 2L));
    	return writerUserRole;
    }
}
