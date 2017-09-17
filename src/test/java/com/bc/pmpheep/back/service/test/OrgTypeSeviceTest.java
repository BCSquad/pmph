package com.bc.pmpheep.back.service.test;
import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import com.bc.pmpheep.back.po.OrgType;
import com.bc.pmpheep.test.BaseTest;
import com.bc.pmpheep.back.service.OrgTypeService;
/**
 * AreaDao 单元测试
 *
 * @author mryang
 */
public class OrgTypeSeviceTest extends BaseTest {
	Logger l = LoggerFactory.getLogger(OrgTypeSeviceTest.class);
	
	@Resource
	private OrgTypeService orgTypeService;
	
    @Test
    @Transactional  
    @Rollback(false) 
    public void addArea() throws Exception {
    	OrgType a=new OrgType("测试",0);
    	orgTypeService.addOrgType(a);
    	l.info("---OrgType--------------------------------新增--------------------------------------------");
    	l.info(a.toString());
    	l.info("---OrgType---------------------------------修改-------------------------------------------");
    	a.setTypeName("ceshiwwwwwwww"+a.getId());
    	l.info(orgTypeService.updateOrgType(a).toString());
    	a.setId(3L);
    	
    	l.info("---OrgType---------------------------------删除-------------------------------------------");
    	l.info(orgTypeService.deleteOrgTypeById(1L).toString());
    	l.info("---OrgType--------------------------------查询-------------------------------------------");
    	l.info(orgTypeService.getOrgTypeById(2L).toString());
    }
    
}





