/**
 * 
 */
package com.bc.pmpheep.back.service.test;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.po.DecPosition;
import com.bc.pmpheep.back.service.DecPositionService;
import com.bc.pmpheep.back.util.Const;
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
     @Rollback(Const.ISROLLBACK)
     public void testAddDecPosition(){
    	 DecPosition decPosition = new DecPosition();
    	 decPosition.setDeclarationId(6L);
    	 decPosition.setTextbookId(8L);
    	 decPosition.setPresetPosition("1");
    	 decPosition = decPositionService.addDecPosition(decPosition);
    	 Assert.assertTrue("添加数据失败", decPosition.getId() > 0);
     }
     
     @Test
     @Rollback(Const.ISROLLBACK)
     public void testDeleteDecPosition(){
    	 long id = add().getId();
    	 Integer count = decPositionService.deleteDecPosition(id);
    	 Assert.assertTrue("删除数据失败", count > 0);
     }
     
     @Test
     @Rollback(Const.ISROLLBACK)
     public void testUpdateDecPosition(){
    	 DecPosition decPosition = add();
    	 decPosition.setIsOnList(true);
    	 decPosition.setChosenPosition(1);
    	 Integer count = decPositionService.updateDecPosition(decPosition);
    	 Assert.assertTrue("更新数据失败", count > 0);
     }
     
     @Test
     @Rollback(Const.ISROLLBACK)
     public void testGetDecPosition(){
    	 long id = add().getId();
    	 DecPosition decPosition = decPositionService.getDecPositionById(id);
    	 Assert.assertNotNull("获取作家申报职位信息失败", decPosition);
     }
     
     @Test
     @Rollback(Const.ISROLLBACK)
     public void testListDecPositions(){
    	 add();
    	 List<DecPosition> list = new ArrayList<>();
    	 list = decPositionService.listDecPositions(8L);
    	 Assert.assertTrue("通过申报表id获取作家申报职位信息集合失败", list.size() > 1);
     }
     
     @Test
     @Rollback(Const.ISROLLBACK)
     public void testListDecPositionsByTextbookId(){
    	 add();
    	 List<DecPosition> list = new ArrayList<>();
    	 list = decPositionService.listDecPositionsByTextbookId(1L);
    	 boolean flag = list.size() == 0;
    	 list = decPositionService.listDecPositionsByTextbookId(3L);
    	 boolean flag2 = list.size()== 0;
    	 Assert.assertTrue("通过书籍id获取作家申报职位信息集合失败", flag && flag2);
    	 /*
    	  * 因为mapper文件sql语句是以declaration表在左的做连接，而数据库里declaration是没有数据的。
    	  * 因此，测试结果必为0，我将mapper文件改为右连接后，方法返回的size应该都应该大于1，测试结果
    	  * 如预期都大于1.
    	  */
     }
     
     @Test
     @Rollback(Const.ISROLLBACK)
     public void listChosenDecPositionsByTextbookId(){
    	 List<DecPosition> list = new ArrayList<>();
    	 list = decPositionService.listChosenDecPositionsByTextbookId(1L);
    	 boolean flag = list.size() == 0;
    	 Assert.assertTrue("通过书籍id获取作家申报职位信息集合失败", flag );
    	 
     }
     
     @Test
     @Rollback(Const.ISROLLBACK)
     public void testListDecPositionsByTextbookIds(){
    	 add();
    	 String[] a = {"1","2","3"};
    	 List<Long> list = new ArrayList<>();
    	 list = decPositionService.listDecPositionsByTextbookIds(a);
    	 Assert.assertTrue("获取申报表id集合失败", list.size() == 5);
     }
     
     private DecPosition add(){
    	 DecPosition decPosition = new DecPosition();
    	 decPosition.setDeclarationId(5L);
    	 decPosition.setTextbookId(1L);
    	 decPosition.setPresetPosition("2");
    	 decPositionService.addDecPosition(decPosition);
    	 DecPosition decPosition2 = new DecPosition(8L, 3L, "7");
    	 decPositionService.addDecPosition(decPosition2);
    	 DecPosition decPosition3 = new DecPosition(8L, 2L, "6");
    	 decPositionService.addDecPosition(decPosition3);
    	 DecPosition decPosition4 = new DecPosition(5L, 3L, "1");
    	 decPositionService.addDecPosition(decPosition4);
    	 DecPosition decPosition5 = new DecPosition(2L, 1L, "3");
    	 decPositionService.addDecPosition(decPosition5);
    	 return decPosition3;
     }
     
     
     @Test
     @Rollback(Const.ISROLLBACK)
     public void testListDecPositionsByTextbookIdAndOrgid(){
    	 List<Long> textBookIds =new ArrayList<Long>();
    	 textBookIds.add(158L);
    	 textBookIds.add(165L);
    	 List<DecPosition> decPositions=decPositionService.listDecPositionsByTextbookIdAndOrgid(textBookIds, 530L);
    	 Assert.assertNotNull("获取作家申报职位信息失败", decPositions);
     }
}
