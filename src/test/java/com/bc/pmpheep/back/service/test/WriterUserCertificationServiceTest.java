package com.bc.pmpheep.back.service.test;
import java.util.Random;

import javax.annotation.Resource;

import org.junit.Assert;
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
	private WriterUserCertificationService writerUserCertificationService;
	
    @Test
    @Rollback(Const.ISROLLBACK) 
    public void test() throws Exception {
    	Random random =new Random();
    	WriterUserCertification writerUserCertification=new WriterUserCertification (new Long(random.nextInt(200)),new Long(random.nextInt(200)),
    			String.valueOf(random.nextInt(200)), "512345678901111111", new Short("1"), "jjj", null, null) ;
    	logger.info("---WriterUserCertificationService测试---------------------------------------------------------------------------------");
    	//新增
    	writerUserCertificationService.addWriterUserCertification(writerUserCertification);
    	Assert.assertTrue("添加失败",writerUserCertification.getId() > 0 );
    	//修改
    	writerUserCertification.setIdcard(String.valueOf(random.nextInt(200)));
    	Assert.assertTrue("更新失败", writerUserCertificationService.updateWriterUserCertification(writerUserCertification) > 0 );
    	//删除
    	Assert.assertTrue("删除失败", writerUserCertificationService.deleteWriterUserCertificationById(2L) >=0);
    	//查询
    	Assert.assertNotNull("获取数据失败",writerUserCertificationService.getWriterUserCertificationById(3L));
    	
    }
    
    
    
}





