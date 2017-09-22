package com.bc.pmpheep.back.service.test;
import java.util.Random;
import javax.annotation.Resource;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import com.bc.pmpheep.back.po.MaterialType;
import com.bc.pmpheep.test.BaseTest;
import com.bc.pmpheep.back.service.MaterialTypeService;
import com.bc.pmpheep.back.util.Const;
/**
 * AreaDao 单元测试
 *
 * @author mryang
 */
public class MaterialTypeServiceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(MaterialTypeServiceTest.class);
	
	@Resource
	private MaterialTypeService testService;
	
    @Test
    @Rollback(Const.ISROLLBACK) 
    public void test() {
    	Random r =new Random();
    	MaterialType testPar=new MaterialType(new Long(r.nextInt(200)), "path"," typeName", r.nextInt(200)," note");
    	logger.info("---MaterialTypeService 测试---------------------------------------------------------------------------------");
    	//新增
    	testService.addMaterialType(testPar);
    	logger.info(testPar.toString());
    	//修改
    	testPar.setTypeName(String.valueOf(r.nextInt(200)));
    	logger.info(testService.updateMaterialType(testPar).toString());
    	//删除
    	logger.info(testService.deleteMaterialTypeById(2L).toString());
    	//查询
    	logger.info(testService.getMaterialTypeById(14L).toString());
    	
    }
    
    
    
}





