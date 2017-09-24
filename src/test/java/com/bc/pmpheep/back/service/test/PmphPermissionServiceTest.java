package com.bc.pmpheep.back.service.test;

import java.util.Random;

import javax.annotation.Resource;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bc.pmpheep.back.po.PmphPermission;
import com.bc.pmpheep.back.service.PmphPermissionService;
import com.bc.pmpheep.test.BaseTest;

/**
 * AreaDao 单元测试
 * 
 * @author mryang
 */
public class PmphPermissionServiceTest extends BaseTest {
    Logger                        logger = LoggerFactory.getLogger(PmphPermissionServiceTest.class);

    @Resource
    private PmphPermissionService testService;

    // @Test
    // @Rollback(Const.ISROLLBACK)
    public void test() {
        Random r = new Random();
        PmphPermission testPar =
        new PmphPermission(new Long(r.nextInt(200)), String.valueOf(r.nextInt(200)),
                           String.valueOf(r.nextInt(200)), String.valueOf(r.nextInt(200)),
                           String.valueOf(r.nextInt(200)), true, String.valueOf(r.nextInt(200)),
                           r.nextInt(200), null, null);
        logger.info("---PmphPermissionService 测试---------------------------------------------------------------------------------");
        // 新增
        testService.addPmphPermission(testPar);
        Assert.assertNotNull("是否添加成功", testPar.getId());
        logger.info(testPar.toString());
        // 修改
        testPar.setMenuName(String.valueOf(r.nextInt(200)));
        Integer aInteger = testService.updatePmphPermissionById(testPar);
        Assert.assertTrue("是否更新成功", aInteger > 0 ? true : false);
        logger.info(aInteger.toString());
        // 删除
        Integer bInteger = testService.deletePmphPermissionById(new PmphPermission((1L)));
        Assert.assertTrue("是否删除成功", bInteger > 0 ? true : false);
        logger.info(bInteger.toString());
        // 查询
        PmphPermission pp = testService.getPmphPermissionById(new PmphPermission((2L)));
        Assert.assertNotNull("是否获取数据", pp);
        logger.info(pp.toString());

    }
}
