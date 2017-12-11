/**
 * 
 */
package com.bc.pmpheep.back.service.test;

import java.util.List;
import javax.annotation.Resource;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import com.bc.pmpheep.back.po.DecAchievement;
import com.bc.pmpheep.back.service.DecAchievementService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.test.BaseTest;

public class DecAchievementServiceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(DecAchievementServiceTest.class);

	@Resource
	DecAchievementService decAchievementService;

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testAddDecAchievement() {
		DecAchievement decAchievement = new DecAchievement();
		decAchievement.setDeclarationId(2L);
		decAchievement.setContent("djaskdjlasjdk");
		decAchievementService.addDecAchievement(decAchievement);
		Assert.assertTrue("添加数据失败", decAchievement.getId() > 0);
	}

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testGetDecAchievementByDeclarationId() {
		DecAchievement decAchievement = new DecAchievement();
		decAchievement.setDeclarationId(2L);
		decAchievement.setContent("djaskdjlasjdk");
		decAchievementService.addDecAchievement(decAchievement);
		decAchievementService.addDecAchievement(decAchievement);
		decAchievementService.addDecAchievement(decAchievement);
		decAchievementService.addDecAchievement(decAchievement);
		DecAchievement lst = decAchievementService.getDecAchievementByDeclarationId(2L);
		Assert.assertTrue("添加数据失败", null != lst);
	}

}
