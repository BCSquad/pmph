package com.bc.pmpheep.back.service.test;

import java.io.IOException;
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
import com.bc.pmpheep.back.po.Org;
import com.bc.pmpheep.back.po.OrgUser;
import com.bc.pmpheep.back.service.OrgUserService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.vo.OrgAndOrgUserVO;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import com.bc.pmpheep.test.BaseTest;

/**
 * OrgUserSevice 单元测试
 * 
 * @author mryang
 */
public class OrgUserSeviceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(OrgUserSeviceTest.class);

	@Resource
	private OrgUserService orgUserService;
	Random random = new Random();
	OrgUser orgUser = new OrgUser("张珊" + random.nextInt(10000), "999", false, 5L, "李四", 1, "zhiwei", "职称", "cahunzehn",
			"shou", "dianhia", "shenfenz", "email", "address", "String postcode", "DEFAULT", "String note", 2, false,
			null, null);

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testAddOrgUser() {
		orgUserService.addOrgUser(orgUser);
		Assert.assertTrue("添加失败", orgUser.getId() > 0);
	}

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testUpdateOrgUser() {
		orgUserService.addOrgUser(orgUser);
		orgUser.setRealname("ceshiwwwwwwww" + orgUser.getId());
		Assert.assertTrue("更新失败", orgUserService.updateOrgUser(orgUser) > 0);
	}

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testDeleteOrgUserById() {
		orgUserService.addOrgUser(orgUser);
		Assert.assertTrue("删除失败", orgUserService.deleteOrgUserById(orgUser.getId()) >= 0);
	}

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testGetOrgUserById() {
		orgUserService.addOrgUser(orgUser);
		Assert.assertNotNull("获取数据失败", orgUserService.getOrgUserById(orgUser.getId()));
	}

	@Test
	public void testGetListOrgUserVO() {
		PageParameter pageParameter = new PageParameter<>();
		PageResult pageResult = new PageResult<>();
		OrgAndOrgUserVO managerVO = new OrgAndOrgUserVO();
		managerVO.setUsername(null);
		managerVO.setRealname(null);
		managerVO.setOrgName(null);
		pageParameter.setParameter(managerVO);
		pageParameter.setStart(1);
		pageParameter.setPageSize(15);
		pageResult = orgUserService.getListOrgUser(pageParameter);
		Assert.assertNotNull("获取失败", pageResult);
	}

	@Test
	public void testGetOrgUserListByOrgIds() {
		orgUserService.addOrgUser(orgUser);
		List<Long> orgIds = new ArrayList<Long>();
		orgIds.add(5L);
		List<OrgUser> orgUser = orgUserService.getOrgUserListByOrgIds(orgIds);
		Assert.assertTrue("获取数据失败", orgUser.size() > 0);
	}

	@Test
	public void testAddOrgUserOfBack() {
		OrgUser orgUser = new OrgUser();
		orgUser.setUsername("OOO");
		orgUser.setRealname("BBc");
		String result = orgUserService.addOrgUserOfBack(orgUser);
		Assert.assertTrue("添加失败", result.equals("SUCCESS"));
	}

	@Test
	public void testUpdateOrgUserOfBack() {
		OrgAndOrgUserVO orgAndOrgUserVO = new OrgAndOrgUserVO();
		Org org = new Org();
		OrgUser orgUser = new OrgUser();
		org.setAreaId(12345L);// 所属区域
		org.setOrgTypeId(4L);// 机构id
		orgUser.setRealname("s");
		orgUser.setOrgId(org.getId());
		org.setOrgName("asdasdasdasda");// 管理员姓名
		orgUser.setUsername("m1001021");// 机构代码
		orgUserService.addOrgUserAndOrgOfBack(orgUser, org);
		orgAndOrgUserVO.setId(orgUser.getId());
		orgAndOrgUserVO.setRealname("机构");
		orgAndOrgUserVO.setUsername(orgUser.getUsername());
		orgAndOrgUserVO.setOrgId(org.getId());
		orgAndOrgUserVO.setOrgName("name");
		Object result = orgUserService.updateOrgUserOfBack(orgAndOrgUserVO);
		Assert.assertTrue("更新失败", result.equals("SUCCESS"));
	}

	@Test
	public void testUpdateOrgUserProgressById() throws CheckedServiceException, IOException {
		orgUserService.addOrgUser(orgUser);
		List<Long> list = new ArrayList<Long>();
		list.add(orgUser.getId());
		Assert.assertTrue("更新审核状态失败", orgUserService.updateOrgUserProgressById(1, list,"",null) > 0);
	}

	@Test
	public void addOrgUserAndOrgOfBack() {
		Org org = new Org();
		OrgUser orgUser = new OrgUser();
		org.setAreaId(12345L);// 所属区域
		org.setOrgTypeId(4L);// 机构id
		orgUser.setRealname("s");
		org.setSort(null);// 排序码
		org.setNote(null);// 备注
		orgUser.setOrgId(org.getId());
		org.setOrgName("asdasdasdasda");// 管理员姓名
		orgUser.setUsername("m1001021");// 机构代码
		orgUser.setEmail(null);
		orgUser.setHandphone(null);
		Assert.assertNotNull("添加失败", orgUserService.addOrgUserAndOrgOfBack(orgUser, org));
	}

	@Test
	public void resetPasswordTest() {
		String password = orgUserService.resetPassword(1L);
		Assert.assertNotNull("修改失败", password);
	}

}
