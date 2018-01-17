package com.bc.pmpheep.back.service.test;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.po.DecMonograph;
import com.bc.pmpheep.back.service.DecMonographService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.test.BaseTest;

/**
 * 主编学术专著情况表业务层单元测试
 * @author tyc
 *
 */
public class DecMonographServiceTest extends BaseTest {

	Logger logger = LoggerFactory.getLogger(DecMonographServiceTest.class);
	
	@Resource
	DecMonographService decMonographService;
	DecMonograph decMonograph = new DecMonograph(1L, "医学", null, true, 
			"人民卫生出版社", null, null, 999);
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void addDecMonograph(){
		decMonographService.addDecMonograph(decMonograph);
		Assert.assertNotNull("插入内容后返回的decMonograph.id不应为空", decMonograph.getId());
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void updateDecMonograph(){
		decMonographService.addDecMonograph(decMonograph);
		decMonograph.setMonographName("医药");
		Assert.assertTrue("更新失败", 
				decMonographService.updateDecMonograph(decMonograph) > 0);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void getDecMonograph(){
		decMonographService.addDecMonograph(decMonograph);
		Assert.assertNotNull("获取数据失败", 
				decMonographService.getDecMonograph(decMonograph.getId()));
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void deleteDecMonograph(){
		decMonographService.addDecMonograph(decMonograph);
		Assert.assertNotNull("删除数据失败", 
				decMonographService.deleteDecMonograph(decMonograph.getId()));
	}
}
