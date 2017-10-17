package com.bc.pmpheep.back.service.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.annotation.Resource;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
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
		Random random = new Random();
		OrgUser orgUser = new OrgUser("张珊" + random.nextInt(10000), "999", false, 5L, "李四", 1, "zhiwei", "职称",
				"cahunzehn", "shou", "dianhia", "shenfenz", "email", "address", "String postcode", "String note", 2,
				false, null, null);
		orgUserService.addOrgUser(orgUser);
		logger.info("---OrgUserService--------------------------------新增--------------------------------------------");
		Assert.assertTrue("添加失败", orgUser.getId() > 0);
		orgUser.setRealname("ceshiwwwwwwww" + orgUser.getId());
		Assert.assertTrue("更新失败", orgUserService.updateOrgUser(orgUser) > 0);
		Assert.assertTrue("删除失败", orgUserService.deleteOrgUserById(1L) >= 0);
		Assert.assertNotNull("获取数据失败", orgUserService.getOrgUserById(4L));
	}

	@Test
	public void getListOrgUserVO() {
		PageParameter pageParameter = new PageParameter<>();
		PageResult pageResult = new PageResult<>();
		OrgUserManagerVO managerVO = new OrgUserManagerVO();
		managerVO.setUsername(null);
		managerVO.setRealname(null);
		managerVO.setOrgName(null);
		pageParameter.setParameter(managerVO);
		pageParameter.setPageSize(15);
		pageResult = orgUserService.getListOrgUser(pageParameter);
		Assert.assertTrue("更新失败", pageResult.getRows().isEmpty());
	}

	@Test
	public void getOrgUserListByOrgIds() {
		List<Long> orgIds = new ArrayList<Long>();
		orgIds.add(1L);
		orgIds.add(2L);
		orgIds.add(3L);
		orgIds.add(4L);
		orgIds.add(6L);
		List<OrgUser> orgUser = orgUserService.getOrgUserListByOrgIds(orgIds);
		Assert.assertTrue("获取数据失败", orgUser.size() == 5);
	}

	@Test
	public void addOrgUserOfBack() {
		OrgUser orgUser = new OrgUser();
		orgUser.setUsername("OOO");
		orgUser.setRealname("BBc");
		String result = orgUserService.addOrgUserOfBack(orgUser);
		Assert.assertTrue("添加失败", result.equals("SUCCESS"));
		orgUser.setUsername("YYY");
		orgUser.setRealname(null);
		result = orgUserService.addOrgUserOfBack(orgUser);
		Assert.assertTrue("添加失败", result.equals("SUCCESS"));
	}

	@Test
	public void updateOrgUserOfBack() {
		OrgUser orgUser = new OrgUser();
		orgUser = orgUserService.getOrgUserById(5L);
		orgUser.setOrgId(10L);
		String result = orgUserService.updateOrgUserOfBack(orgUser);
		Assert.assertTrue("更新失败", result.equals("SUCCESS"));
	}
}
