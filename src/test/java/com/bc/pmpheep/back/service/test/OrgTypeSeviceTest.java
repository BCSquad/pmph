package com.bc.pmpheep.back.service.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.bc.pmpheep.back.po.OrgType;
import com.bc.pmpheep.back.service.OrgTypeService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.test.BaseTest;

/**
 * AreaDao 单元测试
 * 
 * @author mryang
 */
public class OrgTypeSeviceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(OrgTypeSeviceTest.class);

	@Resource
	private OrgTypeService orgTypeService;

	@Test
	@Rollback(Const.ISROLLBACK)
	public void addArea() {
		OrgType a = new OrgType("测试", 0);
		orgTypeService.addOrgType(a);
		logger.info("---OrgTypeService--------------------------------------------------------------------------");
		logger.info(a.toString());
		a.setTypeName("ceshiwwwwwwww" + a.getId());
		logger.info(orgTypeService.updateOrgType(a).toString());
		a.setId(3L);
		logger.info(orgTypeService.deleteOrgTypeById(1L).toString());
		logger.info(orgTypeService.getOrgTypeById(2L).toString());
	}

}
