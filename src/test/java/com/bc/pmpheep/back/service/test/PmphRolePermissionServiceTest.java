package com.bc.pmpheep.back.service.test;

import java.util.Random;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.po.PmphRolePermission;
import com.bc.pmpheep.back.service.PmphRolePermissionService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.test.BaseTest;

/**
 * AreaDao 单元测试
 * 
 * @author mryang
 */
public class PmphRolePermissionServiceTest extends BaseTest {
    Logger                            logger =
                                             LoggerFactory.getLogger(PmphRolePermissionServiceTest.class);

    @Resource
    private PmphRolePermissionService testService;

    @Test
    @Rollback(Const.ISROLLBACK)
    public void test() {
        Random r = new Random();
        PmphRolePermission testPar =
        new PmphRolePermission(new Long(r.nextInt(200)), new Long(r.nextInt(200)));
        logger.info("---PmphRolePermissionService 测试---------------------------------------------------------------------------------");
        // 新增
        testService.addPmphRolePermission(testPar);
        Assert.assertNotNull("是否添加成功", testPar.getId());
        logger.info(testPar.toString());
        // 修改
        testPar.setPermissionId(new Long(r.nextInt(200)));
        Integer aInteger = testService.updatePmphRolePermission(testPar);
        Assert.assertTrue("是否更新成功", aInteger > 0 ? true : false);
        logger.info(aInteger.toString());
        // 删除
        Integer bInteger = testService.deletePmphRolePermissionById(2L);
        Assert.assertTrue("是否删除成功", bInteger > 0 ? true : false);
        logger.info(bInteger.toString());

        // 查询
        PmphRolePermission pp = testService.getPmphRolePermissionById(7L);
        Assert.assertNotNull("是否获取数据", pp);
        logger.info(pp.toString());

    }
}
