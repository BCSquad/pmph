package com.bc.pmpheep.back.service.test;

import java.util.List;
import javax.annotation.Resource;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bc.pmpheep.back.po.DecExtension;
import com.bc.pmpheep.back.service.DecExtensionService;
import com.bc.pmpheep.test.BaseTest;

/**
 *
 * @author Mryang
 *
 * @createDate 2017年11月16日 下午1:49:38
 *
 */
@SuppressWarnings("all")
public class DecExtensionServiceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(DecExtensionServiceTest.class);
	@Resource
	DecExtensionService decExtensionService;

	
	@Test
	public void test() {
		DecExtension decExtension = new DecExtension(null,6L,6L,"测试内容");
		decExtension = decExtensionService.addDecExtension(decExtension);
		Assert.assertTrue("数据添加失败", decExtension.getId()>0);
		decExtension.setContent("随便咯");
		Integer updateReS = decExtensionService.updateDecExtension(decExtension);
		Assert.assertTrue("更新失败", updateReS>0);
		decExtension = decExtensionService.getDecExtensionById(decExtension.getId());
		Assert.assertTrue("获取失败", decExtension != null);
		List<DecExtension> list1 =decExtensionService.getListDecExtensionsByExtensionId(6L);
		List<DecExtension> list2 =decExtensionService.getListDecExtensionsByDeclarationId(6L);
		decExtensionService.deleteDecExtension(decExtension.getId());
		decExtensionService.deleteDecExtensionByExtensionId(6L);
	}
}
