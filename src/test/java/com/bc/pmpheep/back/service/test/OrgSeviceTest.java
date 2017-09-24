package com.bc.pmpheep.back.service.test;

import javax.annotation.Resource;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import com.bc.pmpheep.back.po.Org;
import com.bc.pmpheep.test.BaseTest;
import com.bc.pmpheep.back.service.OrgService;
import com.bc.pmpheep.back.util.Const;

/**
 * AreaDao 单元测试
 *
 * @author mryang
 */
public class OrgSeviceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(OrgSeviceTest.class);
	
	@Resource
	private OrgService orgService;
	
    @Test
    @Rollback(Const.ISROLLBACK) 
    public void test() {
        logger.info("---OrgService-----------------------------------------------------------------------------");
    	Org a=new Org(5L,"测试", 4L, 4L,"ZHANGS", "1234", "BEIZHU", 4, false, null, null);
    	orgService.addOrg(a);
    	logger.info(a.toString());
    	a.setOrgName("ceshiwwwwwwww"+a.getId());
    	logger.info(orgService.updateOrg(a).toString());
    	a.setId(1L);
    	logger.info(orgService.deleteOrgById(4L).toString());
    	logger.info(orgService.getOrgById(3L).toString());
    }
    
}





