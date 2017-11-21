package com.bc.pmpheep.back.service.test;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.aspectj.weaver.ast.Or;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.Org;
import com.bc.pmpheep.test.BaseTest;
import com.bc.pmpheep.back.service.OrgService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.vo.OrgVO;
import com.bc.pmpheep.back.vo.PmphUserManagerVO;

/**
 * AreaDao 单元测试
 *
 * @author mryang
 */
public class OrgSeviceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(OrgSeviceTest.class);

	@Resource
	private OrgService orgService;
	Org org = new Org(5L, "zenngqingfeng", 4L, 4L, "ZHANGS", "1234", "BEIZHU", 4, false, null, null);

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testUpdateOrg() {
		orgService.addOrg(org);
		org.setOrgName("ceshiwwwwwwww" + org.getId());
		Assert.assertTrue("更新失败", orgService.updateOrg(org) > 0);
	}

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testAddOrg() {
		orgService.addOrg(org);
		Assert.assertTrue("添加失败", org.getId() > 0);
	}

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testDeleteOrgById() {
		orgService.addOrg(org);
		Assert.assertTrue("删除失败", orgService.deleteOrgById(org.getId()) >= 0);
	}

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testGetOrgById() {
		orgService.addOrg(org);
		Assert.assertNotNull("获取数据失败", orgService.getOrgById(org.getId()));
	}

	@Test
	public void testListOrg() {
		OrgVO orgVO = new OrgVO();
		orgVO.getOrgName();
		PageResult<OrgVO> page = new PageResult<OrgVO>();
		PageParameter<OrgVO> pageParameter = new PageParameter<OrgVO>();
		pageParameter.setParameter(orgVO);
		page = orgService.listOrg(pageParameter);
	}

	@Test
	public void testListOrgByOrgName() {
		PageParameter pageParameter = new PageParameter<>();
		PageResult<OrgVO> pageResult = new PageResult<OrgVO>();
		OrgVO orgVO = new OrgVO();
		orgVO.setOrgName(null);
		orgVO.setAreaId(null);
		orgVO.setRealname(null);
		pageParameter.setPageNumber(1);
		pageParameter.setPageSize(20);
		pageParameter.setParameter(orgVO);
		pageResult = orgService.getSchoolAdminCheckList(pageParameter);
		Assert.assertNotNull("在新增用户与修改用户时查询机构失败", orgService.listOrgByOrgName("机构名称"));
	}

	@Test
	public void testListSendToSchoolAdminOrAllUser() {
		PageParameter pageParameter = new PageParameter<>();
		PageResult<OrgVO> pageResult = new PageResult<OrgVO>();
		OrgVO orgVO = new OrgVO();
		orgVO.setOrgName(null);
		orgVO.setAreaId(null);
		orgVO.setRealname(null);
		pageParameter.setPageNumber(1);
		pageParameter.setPageSize(20);
		pageParameter.setParameter(orgVO);
		pageResult = orgService.getSchoolAdminCheckList(pageParameter);
		Assert.assertNotNull("系统消息——发送新消息——发送对象失败", orgService.listSendToSchoolAdminOrAllUser("机构名称"));
	}

	@Test
	public void testGetSchoolAdminCheckList() {
		PageParameter pageParameter = new PageParameter<>();
		PageResult<OrgVO> pageResult = new PageResult<OrgVO>();
		OrgVO orgVO = new OrgVO();
		orgVO.setOrgName(null);
		orgVO.setAreaId(null);
		orgVO.setRealname(null);
		pageParameter.setPageNumber(1);
		pageParameter.setPageSize(20);
		pageParameter.setParameter(orgVO);
		pageResult = orgService.getSchoolAdminCheckList(pageParameter);
		Assert.assertNotNull("获取学校管理员审核列表失败", pageResult);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK) 
	public void testListBeElectedOrgByBookIds() {
		List<Long> bookIds = new ArrayList<Long>(2);
		bookIds.add(165L);
		bookIds.add(158L);
		List<Org> lstOrg=orgService.listBeElectedOrgByBookIds(bookIds);
		Assert.assertNotNull("获取学校管理员审核列表失败",lstOrg);
	}
	
}
