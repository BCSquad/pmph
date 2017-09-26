package com.bc.pmpheep.back.service.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.plugin.Page;
import com.bc.pmpheep.back.po.OrgUser;
import com.bc.pmpheep.test.BaseTest;
import com.bc.pmpheep.back.service.OrgUserService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.vo.OrgUserManagerVO;

/**
 * OrgUserSevice 单元测试
 *
 * @author mryang
 */
public class OrgUserSeviceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(OrgUserSeviceTest.class);

	@Resource
	private OrgUserService orgUserService;

	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void test() throws Exception {
		 Random random =new Random();
		 OrgUser orgUser=new OrgUser("张珊"+random.nextInt(10000),"999", false, 5L, "李四",
		 1,"zhiwei", "职称","cahunzehn", "shou", "dianhia","shenfenz", "email",
		 "address", "String postcode", "String note",2, false, null,null);
		 orgUserService.addOrgUser(orgUser);
		 logger.info("---OrgUserService--------------------------------新增--------------------------------------------");
		 Assert.assertTrue("添加失败",orgUser.getId() > 0 );
		 orgUser.setRealname("ceshiwwwwwwww"+orgUser.getId());
		 Assert.assertTrue("更新失败", orgUserService.updateOrgUser(orgUser) > 0 );
		 Assert.assertTrue("删除失败",orgUserService.deleteOrgUserById(1l) >= 0 );
		 Assert.assertNotNull("获取数据失败",orgUserService.getOrgUserById(4L));
	}
	
	@Test
	public void getListOrgUserVO() {
		Page<OrgUserManagerVO, Map<String, String>> page = new Page<>();
		Map<String, String> map = new HashMap<>();
		map.put("username", " ");
		map.put("realname", null);
		map.put("orgName", null);
		page.setParameter(map);
		page.setPageSize(15);
		page = orgUserService.getListOrgUser(page);
		Assert.assertTrue("更新失败", page.getRows().isEmpty() );
	}
}
