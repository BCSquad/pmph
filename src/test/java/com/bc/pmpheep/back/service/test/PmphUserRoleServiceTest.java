package com.bc.pmpheep.back.service.test;

import java.util.Random;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.po.PmphUserRole;
import com.bc.pmpheep.back.service.PmphUserRoleService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.test.BaseTest;

/**
 * AreaDao 单元测试
 * 
 * @author mryang
 */
public class PmphUserRoleServiceTest extends BaseTest {
    Logger                      logger = LoggerFactory.getLogger(PmphUserRoleServiceTest.class);

    @Resource
    private PmphUserRoleService testService;

    @Test
    @Rollback(Const.ISROLLBACK)
    public void test() {
        Random r = new Random();
        PmphUserRole testPar = new PmphUserRole(new Long(r.nextInt(200)), new Long(r.nextInt(200)));
        logger.info("---PmphUserRoleService 测试---------------------------------------------------------------------------------");
        // 新增
        testService.addPmphUserRole(testPar);
        Assert.assertNotNull("是否保存成功", testPar.getId());
        logger.info(testPar.toString());
        // 修改
        testPar.setRoleId(new Long(r.nextInt(200)));
        Integer aInteger = testService.updatePmphUserRole(testPar);
        Assert.assertTrue("是否修改成功", aInteger > 0 ? true : false);
        logger.info(aInteger.toString());
        // 删除
        Integer bInteger = testService.deletePmphUserRoleById(26L);
        Assert.assertTrue("是否删除成功", bInteger > 0 ? true : false);
        logger.info(bInteger.toString());
        // 查询
        PmphUserRole pur = testService.getPmphUserRoleById(25L);
        Assert.assertNotNull("不为空", pur);
        logger.info(pur.toString());

    }

}
