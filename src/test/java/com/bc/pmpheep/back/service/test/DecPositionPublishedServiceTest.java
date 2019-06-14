package com.bc.pmpheep.back.service.test;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.po.DecPositionPublished;
import com.bc.pmpheep.back.service.DecPositionPublishedService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.test.BaseTest;

/**
 * 已公布作家申报职位表业务层单元测试
 * @author tyc
 *
 */
public class DecPositionPublishedServiceTest extends BaseTest {

	Logger logger = LoggerFactory.getLogger(DecPositionPublishedServiceTest.class);
	
	@Resource
	DecPositionPublishedService decPositionPublishedService;
	DecPositionPublished decPositionPublished = new DecPositionPublished(1L, 
			1L, 3L, "1", true, 1, 3, "123", "测试");
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void addDecPositionPublished(){
		decPositionPublishedService.addDecPositionPublished(decPositionPublished);
		Assert.assertNotNull("插入内容后返回的decPositionPublished.id不应为空", decPositionPublished.getId());
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void updateDecPositionPublished(){
		decPositionPublishedService.addDecPositionPublished(decPositionPublished);
		decPositionPublished.setRank(2);
		Assert.assertTrue("更新失败", 
				decPositionPublishedService.updateDecPositionPublished(decPositionPublished) > 0);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void getDecPositionPublished(){
		decPositionPublishedService.addDecPositionPublished(decPositionPublished);
		Assert.assertNotNull("获取数据失败", 
				decPositionPublishedService.getDecPositionPublishedById(decPositionPublished.getId()));
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void deleteDecPositionPublished(){
		decPositionPublishedService.addDecPositionPublished(decPositionPublished);
		Assert.assertNotNull("删除数据失败", 
				decPositionPublishedService.deleteDecPositionPublished(decPositionPublished.getId()));
	}
}
