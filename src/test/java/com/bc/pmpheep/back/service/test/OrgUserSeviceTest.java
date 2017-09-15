package com.bc.pmpheep.back.service.test;
import java.util.Random;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.bc.pmpheep.back.po.OrgUser;
import com.bc.pmpheep.test.BaseTest;
import com.bc.pmpheep.back.service.OrgUserService;
/**
 * AreaDao 单元测试
 *
 * @author mryang
 */
public class OrgUserSeviceTest extends BaseTest {
	Logger l = LoggerFactory.getLogger(OrgUserSeviceTest.class);
	
	@Resource
	private OrgUserService testService;
	
    @Test
    @Transactional  
    @Rollback(false) 
    public void test() throws Exception {
    	Random r =new Random();
    	OrgUser a=new OrgUser("张珊"+r.nextInt(10000),"999", false, 5L, "李四", 1,"zhiwei", "职称","cahunzehn", "shou", "dianhia","shenfenz", "email",
			"address", "String postcode", "String note",2, false, null,null);
    	testService.addOrgUser(a);
    	l.info("---OrgUser--------------------------------新增--------------------------------------------");
    	l.info(a.toString());
    	l.info("---OrgUser---------------------------------修改-------------------------------------------");
    	a.setRealname("ceshiwwwwwwww"+a.getId());
    	l.info(testService.updateOrgUserById(a).toString());
    	a.setId(2L);
    	
    	l.info("---OrgUser---------------------------------删除-------------------------------------------");
    	l.info(testService.deleteOrgUserById(a).toString());
    	l.info("---OrgUser--------------------------------查询-------------------------------------------");
    	l.info(testService.getOrgUserById(new OrgUser(4L)).toString());
    }    
}





