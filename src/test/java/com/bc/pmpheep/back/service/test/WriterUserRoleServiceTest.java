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
    WriterUserRole testPar = new WriterUserRole(new Long(r.nextInt(200)), new Long(r.nextInt(200)));
    
    @Test
    @Rollback(Const.ISROLLBACK)
    public void testAddWriterUserRole() throws Exception {
        // 新增
        testService.addWriterUserRole(testPar);
        Assert.assertNotNull("是否保存成功", testPar.getId());
        //logger.info(testPar.toString());
    }
    @Test
    @Rollback(Const.ISROLLBACK)
    public void testUpdateWriterUserRole(){
    	testService.addWriterUserRole(testPar);
    	testPar.setRoleId(new Long(r.nextInt(200)));
        Integer aInteger = testService.updateWriterUserRole(testPar);
        Assert.assertTrue("是否修改成功", aInteger > 0 ? true : false);
    }
    @Test
    @Rollback(Const.ISROLLBACK)
    public void testDeleteWriterUserRoleById(){
    	testService.addWriterUserRole(testPar);
    	Integer bInteger = testService.deleteWriterUserRoleById(1L);
        Assert.assertTrue("是否删除成功", bInteger > 0 ? true : false);
    }
    @Test
    @Rollback(Const.ISROLLBACK)
    public void testGetWriterUserRoleById(){
    	testService.addWriterUserRole(testPar);
    	WriterUserRole wur = testService.getWriterUserRoleById(2L);
        Assert.assertNotNull("不为空", wur);
    }
}
