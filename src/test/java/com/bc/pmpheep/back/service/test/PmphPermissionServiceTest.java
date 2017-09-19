package com.bc.pmpheep.back.service.test;

import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.bc.pmpheep.back.po.Page;
import com.bc.pmpheep.back.po.PmphPermission;
import com.bc.pmpheep.back.service.PmphPermissionService;
import com.bc.pmpheep.back.util.PageData;
import com.bc.pmpheep.test.BaseTest;

/**
 * AreaDao 单元测试
 * 
 * @author mryang
 */
public class PmphPermissionServiceTest extends BaseTest {
    Logger                logger = LoggerFactory.getLogger(PmphPermissionServiceTest.class);

    @Autowired
    PmphPermissionService testService;

    // @Test
    // @Rollback(Const.ISROLLBACK)
    public void test() throws Exception {

        Random r = new Random();
        PmphPermission testPar =
        new PmphPermission(new Long(r.nextInt(200)), String.valueOf(r.nextInt(200)),
                           String.valueOf(r.nextInt(200)), String.valueOf(r.nextInt(200)),
                           String.valueOf(r.nextInt(200)), true, String.valueOf(r.nextInt(200)),
                           r.nextInt(200), null, null);
        logger.info("---PmphPermissionService 测试---------------------------------------------------------------------------------"); // 新增
        testPar.setMenuName(String.valueOf(r.nextInt(200)));
        logger.info(testService.updatePmphPermissionById(testPar).toString()); // 删除
        logger.info(testService.deletePmphPermissionById(new PmphPermission((1L))).toString());
        // 查询
        logger.info(testService.getPmphPermissionById(new PmphPermission((2L))).toString());

    }

    /**
     * 分页测试
     * 
     * @throws Exception
     */
    @Test
    public void test1() throws Exception {
        PmphPermission pp = new PmphPermission();
        // pp.setParentId(1L);
        Page page = new Page();
        PageData pData = new PageData();
        // pData.put("parentId", 1L);
        page.setObject(pp);
        page.setPd(pData);
        List<PmphPermission> list = testService.getListPageResource(page);
        for (PmphPermission ppp : list) {
            System.out.println(ppp.toString());
        }
        System.out.println("分布函数==>" + page.getPageStr());
        System.out.println("当前页==>" + page.getCurrentPage());
        System.out.println("当前记录起始索引==>" + page.getCurrentResult());
        System.out.println("每页显示记录数 ==>" + page.getShowCount());
        System.out.println("总页数==>" + page.getTotalPage());
        System.out.println("总记录数==>" + page.getTotalResult());
    }
};
