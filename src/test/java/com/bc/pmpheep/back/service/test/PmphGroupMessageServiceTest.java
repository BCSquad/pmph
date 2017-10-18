package com.bc.pmpheep.back.service.test;

import java.io.IOException;
import java.util.Random;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.po.PmphGroupMessage;
import com.bc.pmpheep.back.service.PmphGroupMessageService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import com.bc.pmpheep.test.BaseTest;

/**
 * AreaDao 单元测试
 * 
 * @author mryang
 */
public class PmphGroupMessageServiceTest extends BaseTest {
    Logger                          logger =
                                           LoggerFactory.getLogger(PmphGroupMessageServiceTest.class);

    @Resource
    private PmphGroupMessageService pmphGroupMessageService;

    @Test
    @Rollback(Const.ISROLLBACK)
    public void test() {
        Random random = new Random();
        PmphGroupMessage pmphGroupMessage =
        new PmphGroupMessage(new Long(random.nextInt(200)), new Long(random.nextInt(200)),
                             "String msgContent");
        ;
        logger.info("---PmphGroupMessageService 测试---------------------------------------------------------------------------------");
        // 新增
        pmphGroupMessageService.addPmphGroupMessage(pmphGroupMessage);
        Assert.assertTrue("添加失败", pmphGroupMessage.getId() > 0);
        // 修改
        pmphGroupMessage.setMsgContent(String.valueOf(random.nextInt(200)));
        Assert.assertTrue("更新失败",
                          pmphGroupMessageService.updatePmphGroupMessage(pmphGroupMessage) > 0);
        try {
            // 删除
            Assert.assertTrue("删除失败",
                              "".equals(pmphGroupMessageService.deletePmphGroupMessageById(1L, "")));
        } catch (CheckedServiceException e) {
        } catch (IOException e) {
        }
        Assert.fail();
        // 查询
        Assert.assertNotNull("获取数据失败", pmphGroupMessageService.getPmphGroupMessageById(2L));

    }

}
