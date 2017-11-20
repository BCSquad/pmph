/**
 * 
 */
package com.bc.pmpheep.back.service.test;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.po.WriterProfile;
import com.bc.pmpheep.back.service.WriterProfileService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.test.BaseTest;

/**
 * <p>
 * Title:WriterProfileService测试类
 * <p>
 * 
 * @author lyc
 * @date 2017年9月26日 上午10:53:58
 */
public class WriterProfileServiceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(WriterProfileServiceTest.class);
	@Resource
	WriterProfileService writerProfileService;
	
	/*@Test
	@Rollback(Const.ISROLLBACK)
	public void testAllWriterProfile() {
		logger.info("----------作家简介与标签-----------");
		WriterProfile writerProfile = new WriterProfile(3L, "物理学专家，机械工程学教授", "科研大赛冠军");
		writerProfileService.addWriterProfile(writerProfile);
		Assert.assertTrue("数据添加失败", writerProfile.getId()>0);
		logger.info("-------数据添加成功---------");
		writerProfile.setTag("中科院名誉教授");
		Assert.assertTrue("数据更新失败", writerProfileService.updateWriterProfile(writerProfile)>0);
		logger.info("----------数据更新成功----------");
		Assert.assertNotNull("数据获取失败", writerProfileService.getWriterProfileById(writerProfile.getId()));
		logger.info("----------数据获取成功----------");
		Assert.assertTrue("删除数据失败", writerProfileService.deleteWriterProfileById(writerProfile.getId()) >= 0);
		logger.info("----------测试成功-----------");
	}*/
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testAddWriterProfile(){
		WriterProfile writerProfile = new WriterProfile(1L, "物理学专家，机械工程学教授", "科研大赛冠军");
		writerProfileService.addWriterProfile(writerProfile);
		Assert.assertTrue("数据添加失败", writerProfile.getId()>0);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testUpdateWriterProfile(){
		WriterProfile writerProfile = new WriterProfile(2L, "物理学专家，机械工程学教授", "科研大赛冠军");
		writerProfileService.addWriterProfile(writerProfile);
		writerProfile.setId(writerProfile.getId());
		writerProfile.setTag("中科院名誉教授");
		Assert.assertTrue("数据更新失败", writerProfileService.updateWriterProfile(writerProfile)>0);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testGetWriterProfileById(){
		WriterProfile writerProfile = new WriterProfile(3L, "物理学专家，机械工程学教授", "科研大赛冠军");
		writerProfileService.addWriterProfile(writerProfile);
		writerProfile.setId(writerProfile.getId());
		Assert.assertNotNull("数据获取失败", writerProfileService.getWriterProfileById(writerProfile.getId()));
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testDeleteWriterProfileById(){
		WriterProfile writerProfile = new WriterProfile(4L, "物理学专家，机械工程学教授", "科研大赛冠军");
		writerProfileService.addWriterProfile(writerProfile);
		writerProfile.setId(writerProfile.getId());
		Assert.assertTrue("删除数据失败", writerProfileService.deleteWriterProfileById(writerProfile.getId()) >= 0);
	}
	
}
