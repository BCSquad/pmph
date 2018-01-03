package com.bc.pmpheep.back.service.test;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.WriterPointRule;
import com.bc.pmpheep.back.service.WriterPointRuleService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.vo.WriterPointRuleVO;
import com.bc.pmpheep.test.BaseTest;

/**
 * 积分规则 业务层接口单元测试
 * @author tyc
 *
 */
public class WriterPointRuleServiceTest extends BaseTest {

	Logger logger = LoggerFactory.getLogger(WriterPointRuleServiceTest.class);
	
	@Resource
	WriterPointRuleService writerPointRuleService;
	WriterPointRule writerPointRule = new WriterPointRule("登录规则", "login", 1,
			false, null, 0, null, false);
	public WriterPointRule addWriterPointRules(){
		WriterPointRule writerPointRule = writerPointRuleService.addWriterPointRule(
				new WriterPointRule("登录规则", "login", 1, false, null, 0, null, false));
		return writerPointRule;
	}
	public WriterPointRule addWriterPointRulePoint(){
		WriterPointRule writerPointRule = writerPointRuleService.addWriterPointRule(
				new WriterPointRule("登录兑换规则", "logins", 5, true, "登录平台", 10, null, false));
		return writerPointRule;
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void addWriterPointRule(){
		writerPointRuleService.addWriterPointRule(writerPointRule);
		Assert.assertNotNull("插入内容后返回的writerPointRule.id不应为空", 
				writerPointRule.getId());
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void updateWriterPointRule(){
		writerPointRuleService.addWriterPointRule(writerPointRule);
		writerPointRule.setPoint(2);
		Assert.assertTrue("更新失败", 
				writerPointRuleService.updateWriterPointRule(writerPointRule) > 0);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void deleteWriterPointRule(){
		writerPointRuleService.addWriterPointRule(writerPointRule);
		Integer deleteInteger = writerPointRuleService.deleteWriterPointRule(writerPointRule.getId());
		Assert.assertTrue("删除失败", deleteInteger > 0);
	}
	
	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	@Test
	@Rollback(Const.ISROLLBACK)
	public void getListWriterPointRule(){
		WriterPointRule writerPointRule = this.addWriterPointRules();
		PageParameter pageParameter = new PageParameter<>();
        PageResult pageResult = new PageResult<>();
        WriterPointRuleVO writerPointRuleVO = new WriterPointRuleVO();
        pageParameter.setParameter(writerPointRuleVO);
		pageParameter.setPageSize(10);
		pageResult = writerPointRuleService.getListWriterPointRule(pageParameter);
		Assert.assertNotNull("分页数据失败", pageResult);
	}
	
	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	@Test
	@Rollback(Const.ISROLLBACK)
	public void getlistWriterPointRulePoint(){
		WriterPointRule writerPointRule = this.addWriterPointRulePoint();
		PageParameter pageParameter = new PageParameter<>();
        PageResult pageResult = new PageResult<>();
        WriterPointRuleVO writerPointRuleVO = new WriterPointRuleVO();
        pageParameter.setParameter(writerPointRuleVO);
		pageParameter.setPageSize(10);
		pageResult = writerPointRuleService.getlistWriterPointRulePoint(pageParameter);
		Assert.assertNotNull("分页数据失败", pageResult);
	}
	
}
