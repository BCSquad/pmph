package com.bc.pmpheep.back.service.test;

import javax.annotation.Resource;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.test.BaseTest;
import com.bc.pmpheep.back.service.OrgService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.vo.OrgVO;

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
//        logger.info("---OrgService-----------------------------------------------------------------------------");
//    	Org org=new Org(5L,"测试", 4L, 4L,"ZHANGS", "1234", "BEIZHU", 4, false, null, null);
//    	orgService.addOrg(org);
//    	Assert.assertTrue("添加失败",org.getId() > 0 );
//    	org.setOrgName("ceshiwwwwwwww"+org.getId());
//    	Assert.assertTrue("更新失败", orgService.updateOrg(org) > 0 );
//    	Assert.assertTrue("删除失败",orgService.deleteOrgById(4L)  >= 0 );
//    	Assert.assertNotNull("获取数据失败",orgService.getOrgById(3L));
    	OrgVO orgVO=new OrgVO();
    	orgVO.setOrgName("w29");
    	PageParameter<OrgVO> pageParameter =new PageParameter<OrgVO>();
    	pageParameter.setParameter(orgVO);
    	System.out.println(orgService.getOrgList(pageParameter).getRows());
    }
    
}





