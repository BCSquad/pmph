/**
 * 
 */
package com.bc.pmpheep.back.service.test;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bc.pmpheep.back.po.DecTeachExp;
import com.bc.pmpheep.back.service.DecTeachExpService;
import com.bc.pmpheep.test.BaseTest;

/**
 * <p>
 * Title:DecTeachExpService测试类
 * <p>
 * 
 * @author lyc
 * @date 2017年9月26日 上午9:13:51
 */
public class DecTeachExpServiceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(DecTeachExpServiceTest.class);
	@Resource
	DecTeachExpService decTeachExpService;

	@Test
	public void test() {
       logger.info("-----------作家教学经历-----------");
       DecTeachExp decTeachExp = new DecTeachExp(23L, "西南大学", "心理咨询技术", "第二学位必修课", "2007-02-08", "2017-09-05", 9);
       decTeachExpService.addDecTeachExp(decTeachExp);
       Assert.assertTrue("数据添加失败", decTeachExp.getId()>0);
       logger.info("----------数据添加成功-----------");
       decTeachExp.setSchoolName("第三军医大学");
       Assert.assertTrue("数据更新失败", decTeachExpService.updateDecTeachExp(decTeachExp)>0);
       logger.info("---------数据更新成功----------");
       Assert.assertNotNull("获取数据失败", decTeachExpService.getDecTeachExpById(3L));
       logger.info("---------获取单条数据成功-----------");
       Assert.assertTrue("获取数据集合失败",decTeachExpService.getListDecTeachExpByDeclarationId(23L).size()>0 );
       logger.info("----------获取数据集合成功----------");
       Assert.assertTrue("删除数据失败", decTeachExpService.deleteDecTeachExpById(3L) >= 0);
       logger.info("-----------测试完成------------");
	}
}
