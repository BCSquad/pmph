package com.bc.pmpheep.back.service.test;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bc.pmpheep.back.po.Area;
import com.bc.pmpheep.back.service.AreaService;
import com.bc.pmpheep.back.vo.AreaTreeVO;
import com.bc.pmpheep.test.BaseTest;

/**
 * AreaDao 单元测试
 * 
 * @author mryang
 */
public class AreaSeviceTest extends BaseTest {
    Logger              logger = LoggerFactory.getLogger(AreaSeviceTest.class);

    @Resource
    private AreaService areaService;

    @Test
    public void testAddArea() {
        Area area = addArea();
        logger.info("插入的Area对象=" + area.toString());
        Assert.assertNotNull("插入内容后返回的Area不应为空", area.getId());
    }

    @Test
    public void testUpdateArea() {
        Area area = addArea();
        areaService.updateArea(new Area(area.getId(), "测试区域修改方法"));
        Area area1 = areaService.getAreaById(area.getId());
        Assert.assertEquals("是否更新成功", "测试区域修改方法", area1.getAreaName());
    }

    @Test
    public void testDeleteAreaById() {
        Area area = addArea();
        Integer count = areaService.deleteAreaById(area.getId());
        Assert.assertTrue("如果删除成功更新数应该会大于或等于1", count >= 1);
    }

    @Test
    public void testGetAreaById() {
        Area area = addArea();
        Area area2 = areaService.getAreaById(area.getId());
        Assert.assertNotNull("是否存在", area2);
        Assert.assertEquals("查询结果是否相等", "测试区域", area2.getAreaName());

    }

    @Test
    public void testGetAreaChirldren() {
        Area area = addArea();
        List<AreaTreeVO> listAreaTreeVOs = areaService.getAreaChirldren(area.getId());
        Assert.assertNotNull("有子节点", listAreaTreeVOs);
    }

    private Area addArea() {
        Area area = areaService.addArea(new Area(11L, "测试区域", 4));
        return area;
    }
}
