package com.bc.pmpheep.back.service.test;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.po.DecAcadeReward;
import com.bc.pmpheep.back.service.DecAcadeRewardService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.test.BaseTest;

/**
 * 学术荣誉授予情况表业务层单元测试
 * @author tyc
 *
 */
public class DecAcadeRewardServiceTest extends BaseTest {

	Logger logger = LoggerFactory.getLogger(DecAcadeRewardServiceTest.class);
	
	@Resource
	DecAcadeRewardService decAcadeRewardService;
	DecAcadeReward decAcadeReward = new DecAcadeReward(1L, "医学荣誉奖", 
			3, null, "测试", 999);
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void addDecClinicalReward(){
		decAcadeRewardService.addDecAcadeReward(decAcadeReward);
		Assert.assertNotNull("插入内容后返回的decAcadeReward.id不应为空", decAcadeReward.getId());
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void updateDecClinicalReward(){
		decAcadeRewardService.addDecAcadeReward(decAcadeReward);
		decAcadeReward.setSort(1);
		Assert.assertTrue("更新失败", 
				decAcadeRewardService.updateDecAcadeReward(decAcadeReward) > 0);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void getDecClinicalReward(){
		decAcadeRewardService.addDecAcadeReward(decAcadeReward);
		Assert.assertNotNull("获取数据失败", 
				decAcadeRewardService.getDecAcadeReward(decAcadeReward.getId()));
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void deleteDecClinicalReward(){
		decAcadeRewardService.addDecAcadeReward(decAcadeReward);
		Assert.assertNotNull("删除数据失败", 
				decAcadeRewardService.deleteDecAcadeReward(decAcadeReward.getId()));
	}
}
