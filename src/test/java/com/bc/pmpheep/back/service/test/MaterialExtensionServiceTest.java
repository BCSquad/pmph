package com.bc.pmpheep.back.service.test;
import java.util.Random;

import javax.annotation.Resource;

import org.junit.Assert;
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
	private MaterialExtensionService materialExtensionService;
	
    @Test
    @Rollback(Const.ISROLLBACK) 
    public void test() {
    	Random r =new Random();
    	MaterialExtension materialExtension=new MaterialExtension (new Long(r.nextInt(200)), "extensionName", true);
    	logger.info("---MaterialExtensionService 测试---------------------------------------------------------------------------------");
    	//新增
    	materialExtensionService.addMaterialExtension(materialExtension);
    	Assert.assertTrue("添加失败",materialExtension.getId() > 0 );
    	//修改
    	materialExtension.setExtensionName(String.valueOf(r.nextInt(200)));
    	Assert.assertTrue("更新失败",materialExtensionService.updateMaterialExtension(materialExtension)> 0 );
    	//删除
    	Assert.assertTrue("删除失败",materialExtensionService.deleteMaterialExtensionById(16L) >= 0 );
    	//查询
    	Assert.assertNotNull("获取数据失败",materialExtensionService.getMaterialExtensionById(15L));
    	
    }
    
    
    
}





