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
 * @修改人：mr
 */
public class MaterialTypeServiceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(MaterialTypeServiceTest.class);
	
	@Resource
	private MaterialTypeService materialTypeService;
	
	Random random =new Random();
	MaterialType materialType=new MaterialType(new Long(random.nextInt(200)), "path"," typeName", random.nextInt(200)," note");
	
	
    @Test
    @Rollback(Const.ISROLLBACK) 
    public void testAddMaterialType() {
    	//新增
    	materialTypeService.addMaterialType(materialType);
    	Assert.assertTrue("添加失败",materialType.getId() > 0 );
    	
    }
    
    @Test
    @Rollback(Const.ISROLLBACK) 
    public void testUpdateMaterialType() {
    	materialTypeService.addMaterialType(materialType);
    	//修改
    	materialType.setTypeName(String.valueOf(random.nextInt(200)));
    	Assert.assertTrue("更新失败", materialTypeService.updateMaterialType(materialType) > 0 );
    	
    }
    @Test
    @Rollback(Const.ISROLLBACK) 
    public void testDeleteMaterialTypeById() {
    	materialTypeService.addMaterialType(materialType);
    	//删除
    	Assert.assertTrue("删除失败",materialTypeService.deleteMaterialTypeById(materialType.getId())  >= 0 );
    	
    }
    @Test
    @Rollback(Const.ISROLLBACK) 
    public void testGetMaterialTypeById() {
    	materialTypeService.addMaterialType(materialType);
    	//查询
    	Assert.assertNotNull("获取数据失败",materialTypeService.getMaterialTypeById(materialType.getId()));
    	
    }
    @Test
    @Rollback(Const.ISROLLBACK) 
    public void test() {
    	materialTypeService.addMaterialType(materialType);
    	//获取所有书籍类别
    	Assert.assertNotNull("获取所有书籍类别失败", materialTypeService.listMaterialType(materialType.getParentId()));
    }
    
}





