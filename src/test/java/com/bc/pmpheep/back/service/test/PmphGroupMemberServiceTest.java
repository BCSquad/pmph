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

	@Test
	@Rollback(Const.ISROLLBACK)
	public void test() throws Exception {
		// Random random = new Random();
		// PmphGroupMember pmphGroupMember = new PmphGroupMember(new
		// Long(random.nextInt(200)),
		// new Long(random.nextInt(200)), true, true, true, "jadskjdkas", null, null);
		// logger.info(
		// "---PmphGroupMemberService
		// 测试---------------------------------------------------------------------------------");
		// // 新增
		// pmphGroupMemberService.addPmphGroupMember(pmphGroupMember);
		// Assert.assertTrue("添加失败", pmphGroupMember.getId() > 0);
		// // 修改
		// pmphGroupMember.setDisplayName(String.valueOf(random.nextInt(200)));
		// Assert.assertTrue("更新失败",
		// pmphGroupMemberService.updatePmphGroupMember(pmphGroupMember) > 0);
		// // 删除
		// Assert.assertTrue("删除失败",
		// pmphGroupMemberService.deletePmphGroupMemberById(1L) >= 0);
		// Assert.assertTrue("删除失败",
		// "SUCCESS".equals(pmphGroupMemberService.deletePmphGroupMemberOnGroup(144L)));
		// // 查询
		// Assert.assertNotNull("获取数据失败",
		// pmphGroupMemberService.getPmphGroupMemberById(2L));
		// Assert.assertNotNull("获取数据失败",
		// pmphGroupMemberService.getPmphGroupMemberByMemberId(100L, 1L, false));
		// PageParameter pageParameter = new PageParameter<>(1, 20);
		// PmphGroupMemberManagerVO pmphGroupMemberManagerVO = new
		// PmphGroupMemberManagerVO();
		// pmphGroupMemberManagerVO.setName(null);
		// pmphGroupMemberManagerVO.setGroupId(100L);
		// pageParameter.setParameter(pmphGroupMemberManagerVO);
		// Assert.assertNotNull("获取数据失败",
		// pmphGroupMemberService.listGroupMemberManagerVOs(pageParameter));
	}

}
