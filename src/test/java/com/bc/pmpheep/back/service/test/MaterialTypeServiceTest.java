package com.bc.pmpheep.back.service.test;
import java.util.Random;

import javax.annotation.Resource;

import org.junit.Assert;
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
	private MaterialTypeService materialTypeService;
	
    @Test
    @Rollback(Const.ISROLLBACK) 
    public void test() {
    	Random random =new Random();
    	MaterialType materialType=new MaterialType(new Long(random.nextInt(200)), "path"," typeName", random.nextInt(200)," note");
    	logger.info("---MaterialTypeService 测试---------------------------------------------------------------------------------");
    	//新增
    	materialTypeService.addMaterialType(materialType);
    	Assert.assertTrue("添加失败",materialType.getId() > 0 );
    	//修改
    	materialType.setTypeName(String.valueOf(random.nextInt(200)));
    	Assert.assertTrue("更新失败", materialTypeService.updateMaterialType(materialType) > 0 );
    	//删除
    	Assert.assertTrue("删除失败",materialTypeService.deleteMaterialTypeById(2L)  >= 0 );
    	//查询
    	Assert.assertNotNull("获取数据失败",materialTypeService.getMaterialTypeById(14L));
    	
    }
    
    
    
}





