package com.bc.pmpheep.back.service.test;



import javax.annotation.Resource;
import org.junit.Assert;
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
    public void addArea() {
         Area area = new Area(5L, "测试hh", 4);
         //a.setId(647L);
        areaService.addArea(area);
        logger.info("----AreaService-------------------------------------------------------------------------");
        //logger.info(a.toString());
        Assert.assertTrue("添加失败",area.getId() > 0 );
        area.setAreaName("ceshiwwwwwwww");
        Assert.assertTrue("更新失败", areaService.updateArea(area) > 0 );
        Assert.assertTrue("删除失败", areaService.deleteAreaById(2L) >= 0 );
        //Assert.assertNotNull("获取数据失败", areaService.getAreaById(6L));
        Assert.assertNotNull("失败", areaService.getAreaTreeVO(0L));
        Assert.assertTrue("删除失败", areaService.deleteAreaBatch(4707L)> 0 );
        //logger.info(areaService.getAreaById(6L).toString());
//        areaService.getTest();
//        areaService.deleteAllArea();
//        Long num = areaDao.getAreacount();
//        logger.info("一共有{}条数据",num);
    }

}
