package com.bc.pmpheep.back.service.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.dao.AreaDao;
import com.bc.pmpheep.back.po.Area;
import com.bc.pmpheep.test.BaseTest;
import com.bc.pmpheep.back.service.AreaService;
import com.bc.pmpheep.back.util.Const;

/**
 * AreaDao 单元测试
 *
 * @author mryang
 */
public class AreaSeviceTest extends BaseTest {
    Logger              logger = LoggerFactory.getLogger(AreaSeviceTest.class);

    @Resource
    private AreaService areaService;
    @Resource
    AreaDao             areaDao;

    @Test
    @Rollback(Const.ISROLLBACK)
    public void addArea() throws Exception {
        // Area a = new Area(5L, "测试", 4);
        // areaService.addArea(a);
        logger.info("----AreaService-------------------------------------------------------------------------");
        // logger.info(a.toString());
        // a.setAreaName("ceshiwwwwwwww");
        // logger.info(areaService.updateArea(a).toString());
        // logger.info(areaService.deleteAreaById(2L).toString());
        // logger.info(areaService.getAreaById(6L).toString());
        // areaService.getTest();
        // areaService.deleteAllArea();
        Long num = areaDao.getAreacount();
        logger.info("一共有{}条数据",num);
    }

}
