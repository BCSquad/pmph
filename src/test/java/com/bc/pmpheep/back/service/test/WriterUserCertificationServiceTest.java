package com.bc.pmpheep.back.service.test;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.po.WriterUserCertification;
import com.bc.pmpheep.back.service.WriterUserCertificationService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import com.bc.pmpheep.test.BaseTest;

/**
 * AreaDao 单元测试
 * 
 * @author mryang
 */
public class WriterUserCertificationServiceTest extends BaseTest {
    Logger                                 logger                  =
                                                                   LoggerFactory.getLogger(WriterUserCertificationServiceTest.class);

    @Resource
    private WriterUserCertificationService writerUserCertificationService;

    @Test
    @Rollback(Const.ISROLLBACK)
    public void testUpdateWriterUserCertificationProgressByUserId() throws CheckedServiceException, Exception {
    	WriterUserCertification writerUserCertification=this.addWriterUserCertification();
        // 教师审核按userId更新WriterUserCertification中Progress状态字段
        Short progress = 2;
        Long[] idsLongs = { 1L, 2L };
        Assert.assertTrue("修改失败",
                          writerUserCertificationService.updateWriterUserCertificationProgressByUserId(progress,
                                                                                                       idsLongs, null) >= 0);
    }

    @Test
    @Rollback(Const.ISROLLBACK)
    public void testAddWriterUserCertification() {
    	WriterUserCertification writerUserCertification=this.addWriterUserCertification();
        Assert.assertTrue("添加失败", writerUserCertification.getId() > 0);
    }

    @Test
    @Rollback(Const.ISROLLBACK)
    public void testUpdateWriterUserCertification() {
    	WriterUserCertification writerUserCertification=this.addWriterUserCertification();
        writerUserCertification.setIdcard("12345678");
        Assert.assertTrue("更新失败",
                          writerUserCertificationService.updateWriterUserCertification(writerUserCertification) > 0);
    }

    @Test
    @Rollback(Const.ISROLLBACK)
    public void testDeleteWriterUserCertificationById() {
    	WriterUserCertification writerUserCertification=this.addWriterUserCertification();
        Assert.assertTrue("删除失败",
                          writerUserCertificationService.deleteWriterUserCertificationById(writerUserCertification.getId()) >= 0);
    }

    @Test
    @Rollback(Const.ISROLLBACK)
    public void testGetWriterUserCertificationById() {
    	WriterUserCertification writerUserCertification=this.addWriterUserCertification();
        Assert.assertNotNull("获取数据失败",
                             writerUserCertificationService.getWriterUserCertificationById(writerUserCertification.getId()));
    }
    private WriterUserCertification addWriterUserCertification(){
    	WriterUserCertification wuc=writerUserCertificationService.addWriterUserCertification(new WriterUserCertification(1L, 2L, null, null, (short) 1, null, null, null));
    	return wuc;
    }
}
