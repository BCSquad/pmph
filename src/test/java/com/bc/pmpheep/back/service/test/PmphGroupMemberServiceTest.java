package com.bc.pmpheep.back.service.test;

import java.util.Random;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.PmphGroupMember;
import com.bc.pmpheep.test.BaseTest;
import com.bc.pmpheep.back.service.PmphGroupMemberService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.vo.PmphGroupMemberManagerVO;

/**
 * AreaDao 单元测试
 *
 * @author mryang
 */
public class PmphGroupMemberServiceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(PmphGroupMemberServiceTest.class);

	@Resource
	private PmphGroupMemberService pmphGroupMemberService;

	Random random = new Random();
	PmphGroupMember pmphGroupMember = new PmphGroupMember(new Long(random.nextInt(100)), 1L, false, true, false, false,
			"aaa", null, null);

	@Test
	public void testAddPmphGroupMember() {
		pmphGroupMemberService.addPmphGroupMember(pmphGroupMember);
		Assert.assertTrue("添加失败", pmphGroupMember.getId() > 0);
	}

	@Test
	public void testUpdatePmphGroupMember() {
		pmphGroupMemberService.addPmphGroupMember(pmphGroupMember);
		pmphGroupMember.setDisplayName(String.valueOf(random.nextInt(200)));
		Assert.assertTrue("更新失败", pmphGroupMemberService.updatePmphGroupMember(pmphGroupMember) > 0);
	}

	@Test
	public void testDeletePmphGroupMemberById() {
		pmphGroupMemberService.addPmphGroupMember(pmphGroupMember);
		Assert.assertTrue("删除失败", pmphGroupMemberService.deletePmphGroupMemberById(pmphGroupMember.getId()) >= 0);
	}

	@Test
	public void testDeletePmphGroupMemberOnGroup() {
		pmphGroupMemberService.addPmphGroupMember(pmphGroupMember);
		Assert.assertTrue("删除失败",
				"SUCCESS".equals(pmphGroupMemberService.deletePmphGroupMemberOnGroup(pmphGroupMember.getGroupId())));
	}

	@Test
	public void testGetPmphGroupMemberById() {
		pmphGroupMemberService.addPmphGroupMember(pmphGroupMember);
		Assert.assertNotNull("获取数据失败", pmphGroupMemberService.getPmphGroupMemberById(pmphGroupMember.getId()));
	}

	@Test
	public void testGetPmphGroupMemberByMemberId() {
		pmphGroupMemberService.addPmphGroupMember(pmphGroupMember);
		Assert.assertTrue("添加失败", pmphGroupMember.getId() > 0);
		Assert.assertNotNull("获取数据失败", pmphGroupMemberService.getPmphGroupMemberByMemberId(pmphGroupMember.getGroupId(),
				pmphGroupMember.getUserId(), pmphGroupMember.getIsWriter()));
	}

	@Test
	public void testListGroupMemberManagerVOs() {
		pmphGroupMemberService.addPmphGroupMember(pmphGroupMember);
		PageParameter pageParameter = new PageParameter<>(1, 20);
		PmphGroupMemberManagerVO pmphGroupMemberManagerVO = new PmphGroupMemberManagerVO();
		pmphGroupMemberManagerVO.setName(null);
		pmphGroupMemberManagerVO.setGroupId(pmphGroupMember.getGroupId());
		pageParameter.setParameter(pmphGroupMemberManagerVO);
		Assert.assertNotNull("获取数据失败", pmphGroupMemberService.listGroupMemberManagerVOs(pageParameter));
	}

}
