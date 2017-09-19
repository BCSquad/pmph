package com.bc.pmpheep.back.service.test;
import java.util.Random;
import javax.annotation.Resource;
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
	private TextbookService testService;
	
    @Test
    @Rollback(Const.ISROLLBACK) 
    public void test() throws Exception {
    	Random r =new Random();
    	Textbook testPar=new Textbook(new Long(r.nextInt(200)), "Name", r.nextInt(200), false,false, null,String.valueOf(r.nextInt(200)),1,1L,null)  ;
    	logger.info("---TextbookService 测试---------------------------------------------------------------------------------");
    	//新增
    	testService.addTextbook(testPar);
    	logger.info(testPar.toString());
    	//修改
    	testPar.setTextbookName(String.valueOf(r.nextInt(200)));
    	logger.info(testService.updateTextbook(testPar).toString());
    	//删除
    	logger.info(testService.deleteTextbookById(2L).toString());
    	//查询
    	logger.info(testService.getTextbookById(1L).toString());
    	
    }
    
    
    
}





