package com.bc.pmpheep.back.service.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.bc.pmpheep.back.po.Area;
import com.bc.pmpheep.test.BaseTest;
import com.bc.pmpheep.back.service.AreaService;

/**
 * AreaDao 单元测试
 *
 * @author mryang
 */
public class AreaSeviceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(AreaSeviceTest.class);

	@Resource
	private AreaService areaService;

	@Test
	@Rollback(false)
	public void addArea() throws Exception {
		Area a = new Area(5L, "测试", 4);
		areaService.addArea(a);
		logger.info("-----------------------------------新增--------------------------------------------");
		logger.info(a.toString());
		logger.info("------------------------------------修改-------------------------------------------");
		a.setAreaName("ceshiwwwwwwww");
		logger.info(areaService.updateAreaById(a).toString());
		logger.info("------------------------------------删除-------------------------------------------");
		logger.info(areaService.deleteAreaById(a).toString());
		logger.info("-----------------------------------查询-------------------------------------------");
		logger.info(areaService.getAreaById(new Area(6L)).toString());
	}

}
