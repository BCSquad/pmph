package com.bc.pmpheep.back.service.test;
import java.util.Random;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.po.PmphGroupFile;
import com.bc.pmpheep.test.BaseTest;
import com.bc.pmpheep.back.service.PmphGroupFileService;
import com.bc.pmpheep.back.util.Const;
/**
 * AreaDao 单元测试
 *
 * @author mryang
 */
public class PmphGroupFileServiceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(PmphGroupFileServiceTest.class);
	
	@Resource
	private PmphGroupFileService pmphGroupFileService;
	
    @Test
    @Rollback(Const.ISROLLBACK) 
    public void test() throws Exception {
    	Random random =new Random();
    	PmphGroupFile pmphGroupFile=new PmphGroupFile(new Long(random.nextInt(200)), new Long(random.nextInt(200)), String.valueOf(random.nextInt(200)),
    			"String fileName",random.nextInt(200),
    		    null)  ;
    	logger.info("---PmphGroupFileSevice 测试---------------------------------------------------------------------------------");
    	//新增
    	//pmphGroupFileService.addPmphGroupFile(pmphGroupFile);
    	Assert.assertTrue("添加失败",pmphGroupFile.getId() > 0 );
    	//修改
    	pmphGroupFile.setFileName(String.valueOf(random.nextInt(200)));
    	Assert.assertTrue("更新失败",pmphGroupFileService.updatePmphGroupFile(pmphGroupFile) > 0 );
    	//删除
    	//Assert.assertTrue("删除失败",pmphGroupFileService.deletePmphGroupFileById(2L)  >= 0 );
    	//查询
    	Assert.assertNotNull("获取数据失败",pmphGroupFileService.getPmphGroupFileById(5L));
    	
    }
    
    
    
}





