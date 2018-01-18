package com.bc.pmpheep.back.service.test;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.po.DecClinicalReward;
import com.bc.pmpheep.back.service.DecClinicalRewardService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.test.BaseTest;

/**
 * 临床医学获奖情况表业务层单元测试
 * @author tyc
 *
 */
public class DecClinicalRewardServiceTest extends BaseTest {

	Logger logger = LoggerFactory.getLogger(DecClinicalRewardServiceTest.class);
	
	@Resource
	DecClinicalRewardService decClinicalRewardService;
	DecClinicalReward decClinicalReward = new DecClinicalReward(1L, "医学一等奖", 
			2, null, "测试", 999);
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void addDecClinicalReward(){
		decClinicalRewardService.addDecClinicalReward(decClinicalReward);
		Assert.assertNotNull("插入内容后返回的decClinicalReward.id不应为空", decClinicalReward.getId());
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void updateDecClinicalReward(){
		decClinicalRewardService.addDecClinicalReward(decClinicalReward);
		decClinicalReward.setSort(1);
		Assert.assertTrue("更新失败", 
				decClinicalRewardService.updateDecClinicalReward(decClinicalReward) > 0);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void getDecClinicalReward(){
		decClinicalRewardService.addDecClinicalReward(decClinicalReward);
		Assert.assertNotNull("获取数据失败", 
				decClinicalRewardService.getDecClinicalReward(decClinicalReward.getId()));
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void deleteDecClinicalReward(){
		decClinicalRewardService.addDecClinicalReward(decClinicalReward);
		Assert.assertNotNull("删除数据失败", 
				decClinicalRewardService.deleteDecClinicalReward(decClinicalReward.getId()));
	}
}
