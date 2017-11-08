package com.bc.pmpheep.back.service.test;

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

/**
 * AreaDao 单元测试
 *
 * @author mryang
 */
public class OrgSeviceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(OrgSeviceTest.class);

	@Resource
	private OrgService orgService;

	/*@Test
	@Rollback(Const.ISROLLBACK)
	public void test() {
		// logger.info("---OrgService-----------------------------------------------------------------------------");
		// Org org=new Org(5L,"测试", 4L, 4L,"ZHANGS", "1234", "BEIZHU", 4, false,
		// null, null);
		// orgService.addOrg(org);
		// Assert.assertTrue("添加失败",org.getId() > 0 );
		// org.setOrgName("ceshiwwwwwwww"+org.getId());
		// Assert.assertTrue("更新失败", orgService.updateOrg(org) > 0 );
		// Assert.assertTrue("删除失败",orgService.deleteOrgById(4L) >= 0 );
		// Assert.assertNotNull("获取数据失败",orgService.getOrgById(3L));
		OrgVO orgVO = new OrgVO();
		orgVO.setOrgName("w29");
		PageResult<OrgVO> page = new PageResult<OrgVO>();
		PageParameter pageParameter = new PageParameter<>();
		pageParameter.setParameter(orgVO);
		page = orgService.listOrg(pageParameter);
		System.out.println(page.getRows());
	}*/
	
	@Test
	//@Rollback(false)
	public void testOrg(){
		logger.info("---OrgService------------------------testOrg----------------------------------------------");
		Org org = new Org();
		//org.setParentId(0L);
		org.setOrgName("测试1");
		org.setOrgTypeId(4L);
		org.setAreaId(1L);
		org.setContactPerson("测试人");
		org.setContactPhone("13611111111");
		org.setNote("11111111");
		org.setSort(999);
		//org.setIsDeleted(false);
		orgService.addOrg(org);
		System.out.println("添加成功并获取数据："+orgService.getOrgById(org.getId()));
		Long id = org.getId();
		orgService.deleteOrgById(id);
		System.out.println("删除成功");
		orgService.getOrgById(472L);
		org.setOrgName("测试2");
		orgService.updateOrg(org);
		System.out.println("获取更新后的数据："+orgService.getOrgById(472L));
	}
	
	@Test
	public void listOrg(){
		OrgVO orgVO = new OrgVO();
		orgVO.getOrgName();
		PageResult<OrgVO> page = new PageResult<OrgVO>();
		PageParameter<OrgVO> pageParameter = new PageParameter<OrgVO>();
		pageParameter.setParameter(orgVO);
		page = orgService.listOrg(pageParameter);
		System.out.println(page.getRows());
	}
	@Test
	public void getSchoolAdminCheckList(){
		OrgVO orgVO=new OrgVO();
		orgVO.setOrgName("机构名称");
		orgVO.setAreaId("2");
		PageParameter<OrgVO> pageParameter = new PageParameter<OrgVO>(1, 1, orgVO);
		Assert.assertNull("获取学校管理员审核列表失败", orgService.getSchoolAdminCheckList(pageParameter));
		Assert.assertNull("在新增用户与修改用户时查询机构失败", orgService.listOrgByOrgName("机构名称"));
		Assert.assertNull("系统消息——发送新消息——发送对象失败", orgService.listSendToSchoolAdminOrAllUser("机构名称"));
	}
}
