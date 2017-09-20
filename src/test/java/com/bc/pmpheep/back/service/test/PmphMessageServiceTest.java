package com.bc.pmpheep.back.service.test;

import java.util.Random;

import javax.annotation.Resource;

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
    private PmphMessageService testService;

    @Test
    @Rollback(Const.ISROLLBACK)
    public void test() throws Exception {
        Random r = new Random();
        PmphMessage testPar = new PmphMessage("String msgCode", r.nextInt(200));
        logger.info("---PmphMessageService 测试---------------------------------------------------------------------------------");
        // 新增
        testService.addPmphMessage(testPar);
        logger.info(testPar.toString());
        // 修改
        testPar.setMsgCode(String.valueOf(r.nextInt(200)));
        logger.info(testService.updatePmphMessage(testPar).toString());
        // 删除
        logger.info(testService.deletePmphMessageById(1L).toString());
        // 查询
        logger.info(testService.getPmphMessageById(2L).toString());
    }

}
