package com.bc.pmpheep.back.service.test;
import java.util.Random;
import javax.annotation.Resource;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import com.bc.pmpheep.back.po.WriterUserCertification;
import com.bc.pmpheep.test.BaseTest;
import com.bc.pmpheep.back.service.WriterUserCertificationService;
import com.bc.pmpheep.back.util.Const;
/**
 * AreaDao 单元测试
 *
 * @author mryang
 */
public class WriterUserCertificationServiceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(WriterUserCertificationServiceTest.class);
	
	@Resource
	private WriterUserCertificationService testService;
	
    @Test
    @Rollback(Const.ISROLLBACK) 
    public void test() throws Exception {
    	Random r =new Random();
    	WriterUserCertification testPar=new WriterUserCertification (new Long(r.nextInt(200)),new Long(r.nextInt(200)),
    			String.valueOf(r.nextInt(200)), "512345678901111111", new Short("1"), "jjj", null, null) ;
    	logger.info("---WriterUserCertificationService测试---------------------------------------------------------------------------------");
    	//新增
    	testService.addWriterUserCertification(testPar);
    	logger.info(testPar.toString());
    	//修改
    	testPar.setIdcard(String.valueOf(r.nextInt(200)));
    	logger.info(testService.updateWriterUserCertification(testPar).toString());
    	//删除
    	logger.info(testService.deleteWriterUserCertificationById(2L).toString());
    	//查询
    	logger.info(testService.getWriterUserCertificationById(3L).toString());
    	
    }
    
    
    
}





