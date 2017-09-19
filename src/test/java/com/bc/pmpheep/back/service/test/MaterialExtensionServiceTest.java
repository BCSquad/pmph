package com.bc.pmpheep.back.service.test;
import java.util.Random;
import javax.annotation.Resource;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import com.bc.pmpheep.back.po.MaterialExtension;
import com.bc.pmpheep.test.BaseTest;
import com.bc.pmpheep.back.service.MaterialExtensionService;
import com.bc.pmpheep.back.util.Const;
/**
 * AreaDao 单元测试
 *
 * @author mryang
 */
public class MaterialExtensionServiceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(MaterialExtensionServiceTest.class);
	
	@Resource
	private MaterialExtensionService testService;
	
    @Test
    @Rollback(Const.ISROLLBACK) 
    public void test() throws Exception {
    	Random r =new Random();
    	MaterialExtension testPar=new MaterialExtension (new Long(r.nextInt(200)), "extensionName", true);
    	logger.info("---MaterialExtensionService 测试---------------------------------------------------------------------------------");
    	//新增
    	testService.addMaterialExtension(testPar);
    	logger.info(testPar.toString());
    	//修改
    	testPar.setExtensionName(String.valueOf(r.nextInt(200)));
    	logger.info(testService.updateMaterialExtension(testPar).toString());
    	//删除
    	logger.info(testService.deleteMaterialExtensionById(16L).toString());
    	//查询
    	logger.info(testService.getMaterialExtensionById(15L).toString());
    	
    }
    
    
    
}





