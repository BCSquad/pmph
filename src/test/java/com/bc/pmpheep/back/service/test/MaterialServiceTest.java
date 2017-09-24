package com.bc.pmpheep.back.service.test;
import java.util.Date;
import java.util.Random;
import javax.annotation.Resource;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import com.bc.pmpheep.back.po.Material;
import com.bc.pmpheep.test.BaseTest;
import com.bc.pmpheep.back.service.MaterialService;
import com.bc.pmpheep.back.util.Const;
/**
 * AreaDao 单元测试
 *
 * @author mryang
 */
public class MaterialServiceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(MaterialServiceTest.class);
	
	@Resource
	private MaterialService testService;
	
    @Test
    @Rollback(Const.ISROLLBACK) 
    public void test() {
    	Random r =new Random();
    	Material testPar=new Material ("materialName",r.nextInt(200),
    			new Long(r.nextInt(200)), new Date(), new Date(),
    			new Date(),"mailAddress",new Short("1"),
    			new Long(r.nextInt(200)), new Long(r.nextInt(200)),new Long(r.nextInt(200)),
    			true, true,
    			true, true,
    			true, true,
    			true, true,
    			true, true,
    			true, true,
    			true, true,
    			true, true,
    			true, true,
    			true, true,
    			true, true,
    			true, true,
    			true, true,
    			true, true, null,
    			new Long(r.nextInt(200)), null, new Long(r.nextInt(200)));
    	logger.info("---MaterialService 测试---------------------------------------------------------------------------------");
    	//新增
    	testService.addMaterial(testPar);
    	logger.info(testPar.toString());
    	//修改
    	testPar.setMaterialName(String.valueOf(r.nextInt(200)));
    	logger.info(testService.updateMaterial(testPar).toString());
    	//删除
    	logger.info(testService.deleteMaterialById(2L).toString());
    	//查询
    	logger.info(testService.getMaterialById(3L).toString());
    	
    }
    
    
    
}





