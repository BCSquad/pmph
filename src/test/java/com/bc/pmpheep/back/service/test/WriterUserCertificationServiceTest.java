package com.bc.pmpheep.back.service.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.po.WriterUserCertification;
import com.bc.pmpheep.back.service.WriterUserCertificationService;
import com.bc.pmpheep.back.util.Const;
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

    Random                                 random                  = new Random();
    WriterUserCertification                writerUserCertification =
                                                                   new WriterUserCertification(
                                                                                               new Long(
                                                                                                        random.nextInt(200)),
                                                                                               new Long(
                                                                                                        random.nextInt(200)),
                                                                                               String.valueOf(random.nextInt(200)),
                                                                                               "512345678901111111",
                                                                                               new Short(
                                                                                                         "1"),
                                                                                               "jjj",
                                                                                               null,
                                                                                               null);

    @Test
    @Rollback(Const.ISROLLBACK)
    public void testUpdateWriterUserCertificationProgressByUserId() {
        writerUserCertificationService.addWriterUserCertification(writerUserCertification);
        // 教师审核按userId更新WriterUserCertification中Progress状态字段
        Short progress = 1;
        List<Long> list = new ArrayList<Long>();
        list.add(1L);
        list.add(2L);
        Assert.assertTrue("修改失败",
                          writerUserCertificationService.updateWriterUserCertificationProgressByUserId(progress,
                                                                                                       list) >= 0);
    }

    @Test
    @Rollback(Const.ISROLLBACK)
    public void testAddWriterUserCertification() {
        writerUserCertificationService.addWriterUserCertification(writerUserCertification);
        Assert.assertTrue("添加失败", writerUserCertification.getId() > 0);
    }

    @Test
    @Rollback(Const.ISROLLBACK)
    public void testUpdateWriterUserCertification() {
        writerUserCertificationService.addWriterUserCertification(writerUserCertification);
        writerUserCertification.setIdcard(String.valueOf(random.nextInt(200)));
        Assert.assertTrue("更新失败",
                          writerUserCertificationService.updateWriterUserCertification(writerUserCertification) > 0);
    }

    @Test
    @Rollback(Const.ISROLLBACK)
    public void testDeleteWriterUserCertificationById() {
        writerUserCertificationService.addWriterUserCertification(writerUserCertification);
        Assert.assertTrue("删除失败",
                          writerUserCertificationService.deleteWriterUserCertificationById(2L) >= 0);
    }

    @Test
    @Rollback(Const.ISROLLBACK)
    public void testGetWriterUserCertificationById() {
        writerUserCertificationService.addWriterUserCertification(writerUserCertification);
        Assert.assertNotNull("获取数据失败",
                             writerUserCertificationService.getWriterUserCertificationById(3L));
    }
}
