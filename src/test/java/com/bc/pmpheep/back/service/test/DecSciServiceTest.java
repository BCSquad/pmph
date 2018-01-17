package com.bc.pmpheep.back.service.test;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.po.DecSci;
import com.bc.pmpheep.back.service.DecSciService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.test.BaseTest;

/**
 * SCI论文投稿及影响因子情况表业务层单元测试
 * @author tyc
 *
 */
public class DecSciServiceTest extends BaseTest {

	Logger logger = LoggerFactory.getLogger(DecSciServiceTest.class);
	
	@Resource
	DecSciService decSciService;
	DecSci decSci = new DecSci(1L, "医学新特性", "人民卫生出版社第一期", "医学", null, "测试", 999);
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void addDecSci(){
		decSciService.addDecSci(decSci);
		Assert.assertNotNull("插入内容后返回的decSci.id不应为空", decSci.getId());
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void updateDecSci(){
		decSciService.addDecSci(decSci);
		decSci.setSort(1);
		Assert.assertTrue("更新失败", 
				decSciService.updateDecSci(decSci) > 0);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void getDecSci(){
		decSciService.addDecSci(decSci);
		Assert.assertNotNull("获取数据失败", 
				decSciService.getDecSci(decSci.getId()));
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void deleteDecSci(){
		decSciService.addDecSci(decSci);
		Assert.assertNotNull("删除数据失败", 
				decSciService.deleteDecSci(decSci.getId()));
	}
}
