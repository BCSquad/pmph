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
    private PmphRolePermissionService pmphRolePermissionService;

    @Test
    @Rollback(Const.ISROLLBACK)
    public void test() {
        Random random = new Random();
        PmphRolePermission pmphRolePermission =
        new PmphRolePermission(new Long(random.nextInt(200)), new Long(random.nextInt(200)));
        logger.info("---PmphRolePermissionService 测试---------------------------------------------------------------------------------");
        // 新增
        pmphRolePermissionService.addPmphRolePermission(pmphRolePermission);
        Assert.assertNotNull("是否添加成功", pmphRolePermission.getId());
        //logger.info(testPar.toString());
        // 修改
        pmphRolePermission.setPermissionId(new Long(random.nextInt(200)));
        Integer aInteger = pmphRolePermissionService.updatePmphRolePermission(pmphRolePermission);
        Assert.assertTrue("更新失败", aInteger > 0 ? true : false);
        //logger.info(aInteger.toString());
        // 删除
        Integer bInteger = pmphRolePermissionService.deletePmphRolePermissionById(2L);
        Assert.assertTrue("删除失败", bInteger > 0 ? true : false);
        //logger.info(bInteger.toString());

        // 查询
        PmphRolePermission pp = pmphRolePermissionService.getPmphRolePermissionById(7L);
        Assert.assertNotNull("是否获取数据", pp);
        //logger.info(pp.toString());

    }
}
