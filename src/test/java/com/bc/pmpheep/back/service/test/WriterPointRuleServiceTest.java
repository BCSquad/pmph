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
		WriterPointRuleVO writerPointRule1 = new WriterPointRuleVO(writerPointRule.getId(), "登录规则", "login", 1,
				false, null, 0, null, false);
		Assert.assertTrue("更新失败", 
				writerPointRuleService.updateWriterPointRule(writerPointRule1) > 0);
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
	@Test
	@Rollback(Const.ISROLLBACK)
	public void point(){
		 WriterPointRule writerPointRule1=new WriterPointRule("连续登录", "logins", 1, false, null, null, "连续登录每天增加1分", true);
		 writerPointRuleService.addWriterPointRule(writerPointRule1);
		 WriterPointRule writerPointRule2=new WriterPointRule("连续最大积分", "max_login_integral", 5, false, null, null, "到第5天增加5分后，每天给5分，中间有一天不来，重新开始积分", true);
		 writerPointRuleService.addWriterPointRule(writerPointRule2);
		 WriterPointRule writerPointRule3=new WriterPointRule("登录", "login", 1, false, null, null, "每天登录一次给1分，一天仅一次", true);
		 writerPointRuleService.addWriterPointRule(writerPointRule3);
		 WriterPointRule writerPointRule4=new WriterPointRule("回复话题", "reply_topic", 1, false, null, null, "回复话题给1分", true);
		 writerPointRuleService.addWriterPointRule(writerPointRule4);
		 WriterPointRule writerPointRule5=new WriterPointRule("创建话题", "create_topic", 2, false, null, null, "创建话题给1分", true);
		 writerPointRuleService.addWriterPointRule(writerPointRule5);
		 WriterPointRule writerPointRule6=new WriterPointRule("平台a", "sys_a", 50, true, "2", 2, "本平台50积分=商城2积分", true);
		 writerPointRuleService.addWriterPointRule(writerPointRule6);
		 WriterPointRule writerPointRule7=new WriterPointRule("平台b", "sys_b", 200, true, "0", 10, "本平台200积分=平台b10积分", true);
		 writerPointRuleService.addWriterPointRule(writerPointRule7);
		 WriterPointRule writerPointRule8=new WriterPointRule("智慧商城", "buss", 100, true, "1", 1, "本平台100积分=智慧商城1积分", true);
		 writerPointRuleService.addWriterPointRule(writerPointRule8);
	}
}
