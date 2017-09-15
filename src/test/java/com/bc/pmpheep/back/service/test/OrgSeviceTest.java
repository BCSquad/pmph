package com.bc.pmpheep.back.service.test;

import javax.annotation.Resource;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import com.bc.pmpheep.back.po.Org;
import com.bc.pmpheep.test.BaseTest;
import com.bc.pmpheep.back.service.OrgService;

/**
 * AreaDao 单元测试
 *
 * @author mryang
 */
public class OrgSeviceTest extends BaseTest {
	Logger l = LoggerFactory.getLogger(OrgSeviceTest.class);
	
	@Resource
	private OrgService orgService;
	
    @Test
    @Transactional  
    @Rollback(false) 
    public void addArea() throws Exception {
        l.info("---Org---以下测试什么内容，是怎么考虑的-----------");
    	Org a=new Org(5L,"测试", 4L, 4L,"ZHANGS", "1234", "BEIZHU", 4, false, null, null);
    	orgService.addOrg(a);
    	l.debug("---Org--------------------------------新增--------------------------------------------");
    	l.info(a.toString());
    	l.info("---Org---------------------------------修改-------------------------------------------");
    	a.setOrgName("ceshiwwwwwwww"+a.getId());
    	l.info(orgService.updateOrgById(a).toString());
    	a.setId(1L);
    	l.info("---Org---------------------------------删除-------------------------------------------");
    	l.info(orgService.deleteOrgById(a).toString());
    	l.info("---Org--------------------------------查询-------------------------------------------");
    	l.info(orgService.getOrgById(new Org(3L)).toString());
    }
    
}





