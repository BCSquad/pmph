package com.bc.pmpheep.back.servicetest;
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
	Logger l = LoggerFactory.getLogger(AreaSeviceTest.class);
	
	@Resource
	private AreaService areaService;
	
    @Test
    @Transactional  
    @Rollback(false) 
    public void addArea() throws Exception {
    	Area a=new Area(5L,"测试", 4);
    	areaService.addArea(a);
    	l.info("-----------------------------------新增--------------------------------------------");
    	l.info(a.toString());
    	l.info("------------------------------------修改-------------------------------------------");
    	a.setAreaName("ceshiwwwwwwww");
    	l.info(areaService.updateAreaById(a).toString());
    	l.info("------------------------------------删除-------------------------------------------");
    	l.info(areaService.deleteAreaById(a).toString());
    	l.info("-----------------------------------查询-------------------------------------------");
    	l.info(areaService.getAreaById(new Area(6L)).toString());
    }
    
}
