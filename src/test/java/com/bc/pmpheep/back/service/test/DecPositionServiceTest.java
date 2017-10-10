/**
 * 
 */
package com.bc.pmpheep.back.service.test;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bc.pmpheep.back.po.DecPosition;
import com.bc.pmpheep.back.service.DecPositionService;
import com.bc.pmpheep.test.BaseTest;

/**
 * <p>Title:测试类<p>
 * <p>Description:作家申报职位测试<p>
 * @author lyc
 * @date 2017年10月10日 上午10:29:05
 */
public class DecPositionServiceTest extends BaseTest{
	 Logger logger = LoggerFactory.getLogger(DecPositionServiceTest.class);
     @Resource
     DecPositionService decPositionService;
     
     @Test
     public void test(){
    	 logger.info("--------作家申报职位测试--------");
    	 DecPosition decPosition = new DecPosition();
    	 decPosition.setDeclarationId(2L);
    	 decPosition.setTextbookId(1L);
    	 decPosition.setPresetPosition((short) 2);
    	 decPositionService.decPosition(decPosition);
    	 Assert.assertTrue("添加数据失败", decPosition.getId() > 0);
    	 decPosition.setPresetPosition((short)3);
    	 Assert.assertTrue("更新失败", decPositionService.updateDecPosition(decPosition) > 0);
    	 DecPosition decPosition2 = new DecPosition();
    	 decPosition2.setDeclarationId(2L);
    	 decPosition2.setTextbookId(3L);
    	 decPosition2.setPresetPosition((short)1);
    	 decPositionService.decPosition(decPosition2);
    	 Assert.assertNotNull("获取消息失败", decPositionService.decPositionById(2L));
    	 Assert.assertTrue("获取数据集合失败", decPositionService.listDecPositions(2L).size() > 0);
    	 Assert.assertTrue("删除数据失败", decPositionService.deleteDecPosition(1L)>= 0);
     }
}
