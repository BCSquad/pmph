package com.bc.pmpheep.back.service.test;
import java.util.Random;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.dao.MaterialContactDao;
import com.bc.pmpheep.back.po.MaterialContact;
import com.bc.pmpheep.test.BaseTest;
import com.bc.pmpheep.back.service.MaterialContactService;
import com.bc.pmpheep.back.util.Const;
/**
 * AreaDao 单元测试
 *
 * @author mryang
 */
public class MaterialContactServiceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(MaterialContactServiceTest.class);
	
	@Resource
	private MaterialContactService testService;
	@Resource
	MaterialContactDao materialContactDao;
	
    @Test
    @Rollback(Const.ISROLLBACK) 
    public void test() throws Exception {
    	Random r =new Random();
    	MaterialContact testPar=new MaterialContact(new Long(r.nextInt(200)),new Long(r.nextInt(200)),"contactUserName", "contactPhone", "contactEmai");
    	logger.info("---TextbookService 测试---------------------------------------------------------------------------------");
    	Long num = materialContactDao.getMaterialContactCount();
    	logger.info("一共有{}", num);
    	//新增
    	testService.addMaterialContact(testPar);
    	logger.info(testPar.toString());
    	//修改
    	testPar.setContactUserName(String.valueOf(r.nextInt(200)));
    	logger.info(testService.updateMaterialContact(testPar).toString());
    	//删除
    	logger.info(testService.deleteMaterialContactById(2L).toString());
    	//查询
    	logger.info(testService.getMaterialContactById(1L).toString());
    	
    }
    
    
    
}





