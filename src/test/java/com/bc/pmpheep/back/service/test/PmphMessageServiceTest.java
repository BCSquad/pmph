package com.bc.pmpheep.back.service.test;

import java.util.Random;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.po.PmphMessage;
import com.bc.pmpheep.back.service.PmphMessageService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.test.BaseTest;

/**
 * AreaDao 单元测试
 * 
 * @author mryang
 */
public class PmphMessageServiceTest extends BaseTest {
    Logger                     logger = LoggerFactory.getLogger(PmphMessageServiceTest.class);

    @Resource
    private PmphMessageService pmphMessageService;

    @Test
    @Rollback(Const.ISROLLBACK)
    public void test() {
        Random random = new Random();
        PmphMessage pmphMessage = new PmphMessage("String msgCode", random.nextInt(200));
        logger.info("---PmphMessageService 测试---------------------------------------------------------------------------------");
        // 新增
        pmphMessageService.addPmphMessage(pmphMessage);
        Assert.assertTrue("添加失败",pmphMessage.getId() > 0 );
        // 修改
        pmphMessage.setMsgCode(String.valueOf(random.nextInt(200)));
        Assert.assertTrue("更新失败",pmphMessageService.updatePmphMessage(pmphMessage)> 0 );
        // 删除
        Assert.assertTrue("删除失败",pmphMessageService.deletePmphMessageById(1L)  >= 0 );
        // 查询
        Assert.assertNotNull("获取数据失败",pmphMessageService.getPmphMessageById(2L));
    }

}
