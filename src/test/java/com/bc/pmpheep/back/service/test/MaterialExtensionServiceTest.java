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
	
	Random r =new Random();
	MaterialExtension materialExtension=new MaterialExtension (new Long(r.nextInt(200)), "extensionName", true);
	
    @Test
    @Rollback(Const.ISROLLBACK) 
    public void testAddMaterialExtension() {
    	//新增
    	materialExtensionService.addMaterialExtension(materialExtension);
    	Assert.assertTrue("添加失败",materialExtension.getId() > 0 );
    	
    }
    
    @Test
    @Rollback(Const.ISROLLBACK) 
    public void testUpdateMaterialExtension() {
    	materialExtensionService.addMaterialExtension(materialExtension);
    	//修改
    	materialExtension.setExtensionName(String.valueOf(r.nextInt(200)));
    	Assert.assertTrue("更新失败",materialExtensionService.updateMaterialExtension(materialExtension)> 0 );
    	//修改
    	materialExtension.setId(r.nextLong());
    	materialExtension.setExtensionName(String.valueOf(r.nextInt(200)));
    	Assert.assertTrue("更新失败",materialExtensionService.updateMaterialExtension(materialExtension)>= 0 );
    }
    
    @Test
    @Rollback(Const.ISROLLBACK) 
    public void testDeleteMaterialExtensionById() {
    	materialExtensionService.addMaterialExtension(materialExtension);
    	//删除
    	Assert.assertTrue("删除失败",materialExtensionService.deleteMaterialExtensionById(materialExtension.getId()) > 0 );
    	//删除
    	Assert.assertTrue("删除失败",materialExtensionService.deleteMaterialExtensionById(16L) >= 0 );
    }
    	
    	
    @Test
    @Rollback(Const.ISROLLBACK) 
    public void testGetMaterialExtensionById() {
    	materialExtensionService.addMaterialExtension(materialExtension);
    	//查询
    	Assert.assertNotNull("获取数据失败",materialExtensionService.getMaterialExtensionById(materialExtension.getId()));
    	materialExtensionService.getMaterialExtensionById(r.nextLong());
    }
    
    
    @Test
    @Rollback(Const.ISROLLBACK) 
    public void testGetMaterialExtensionByMaterialId() {
    	materialExtensionService.addMaterialExtension(materialExtension);
    	//查询
    	Assert.assertNotNull("获取数据失败",materialExtensionService.getMaterialExtensionByMaterialId(materialExtension.getMaterialId()));
    	
    }
    
    
}





