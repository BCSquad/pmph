package com.bc.pmpheep.back.service.test;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.po.DecPublishReward;
import com.bc.pmpheep.back.service.DecPublishRewardService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.test.BaseTest;

/**
 * 出版行业获奖情况表业务层单元测试
 * @author tyc
 *
 */
public class DecPublishRewardServiceTest extends BaseTest {

	Logger logger = LoggerFactory.getLogger(DecPublishRewardServiceTest.class);
	
	@Resource
	DecPublishRewardService decPublishRewardService;
	DecPublishReward decPublishReward = new DecPublishReward(1L, "医学特等奖", 
			"人民卫生出版社", null, "获奖", 999);
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void addDecPublishReward(){
		decPublishRewardService.addDecPublishReward(decPublishReward);
		Assert.assertNotNull("插入内容后返回的decPublishReward.id不应为空", decPublishReward.getId());
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void updateDecPublishReward(){
		decPublishRewardService.addDecPublishReward(decPublishReward);
		decPublishReward.setRewardName("中药");
		Assert.assertTrue("更新失败", 
				decPublishRewardService.updateDecPublishReward(decPublishReward) > 0);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void getDecPublishReward(){
		decPublishRewardService.addDecPublishReward(decPublishReward);
		Assert.assertNotNull("获取数据失败", 
				decPublishRewardService.getDecPublishReward(decPublishReward.getId()));
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void deleteDecPublishReward(){
		decPublishRewardService.addDecPublishReward(decPublishReward);
		Assert.assertNotNull("删除数据失败", 
				decPublishRewardService.deleteDecPublishReward(decPublishReward.getId()));
	}
}
