package com.bc.pmpheep.back.service.test;
import java.util.Random;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.po.Textbook;
import com.bc.pmpheep.test.BaseTest;
import com.bc.pmpheep.back.service.TextbookService;
import com.bc.pmpheep.back.util.Const;
/**
 * AreaDao 单元测试
 *
 * @author mryang
 */
public class TextbookServiceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(TextbookServiceTest.class);
	
	@Resource
	private TextbookService textbookService;
	
    @Test
    @Rollback(Const.ISROLLBACK) 
    public void test()  {
    	Random random =new Random();
    	Textbook textbook=new Textbook(new Long(random.nextInt(200)), "Name", random.nextInt(200), false,false, null,String.valueOf(random.nextInt(200)),1,1L,null)  ;
    	logger.info("---TextbookService 测试---------------------------------------------------------------------------------");
    	//新增
    	textbookService.addTextbook(textbook);
    	Assert.assertTrue("添加失败",textbook.getId() > 0 );
    	//修改
    	textbook.setTextbookName(String.valueOf(random.nextInt(200)));
    	Assert.assertTrue("更新失败", textbookService.updateTextbook(textbook) > 0 );
    	//删除
    	Assert.assertTrue("删除失败",textbookService.deleteTextbookById(2L)  >= 0 );
    	//查询
    	Assert.assertNotNull("获取数据失败",textbookService.getTextbookById(1L));
    	
    }
    
    
    
}





