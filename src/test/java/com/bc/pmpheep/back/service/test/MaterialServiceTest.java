package com.bc.pmpheep.back.service.test;
import java.util.Date;
import java.util.Random;

import javax.annotation.Resource;

import org.junit.Assert;
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
 * @修改人：mr
 */
public class MaterialServiceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(MaterialServiceTest.class);
	
	@Resource
	private MaterialService materialService;
	
	
	Random random =new Random();
	Material material=new Material("materialName",random.nextInt(200),
			new Long(random.nextInt(200)), new Date(), new Date(),
			new Date(), "String mailAddress"+new Date(),new Long(random.nextInt(200)),
			new Long(random.nextInt(200)), new Long(random.nextInt(200)),new Long(random.nextInt(200)),
			Integer.valueOf("11111101",2), Integer.valueOf("00011101",2));
	
    @Test
    @Rollback(Const.ISROLLBACK) 
    public void testAddMaterial() {
    	
    	//新增
    	materialService.addMaterial(material);
    	Assert.assertTrue("添加失败",material.getId() > 0 );
    	
    	
    }
    
    @Test
    @Rollback(Const.ISROLLBACK) 
    public void testUpdateMaterial() {
    	materialService.addMaterial(material);
    	//修改
    	material.setMaterialName(String.valueOf(random.nextInt(200)));
    	Assert.assertTrue("更新失败", materialService.updateMaterial(material, null) > 0 );
    	
    }
    
    @Test
    @Rollback(Const.ISROLLBACK) 
    public void testDeleteMaterialById() {
    	materialService.addMaterial(material);
    	//删除
    	Assert.assertTrue("删除失败",materialService.deleteMaterialById(material.getId())  > 0 );
    	Assert.assertTrue("删除失败",materialService.deleteMaterialById(2L)  >= 0 );
    	
    }
    @Test
    @Rollback(Const.ISROLLBACK) 
    public void testGetMaterialById() {
    	materialService.addMaterial(material);
    	//查询
    	Assert.assertNotNull("获取数据失败",materialService.getMaterialById(material.getId()));
    	materialService.getMaterialById(random.nextLong());
    	
    }
    @Test
    @Rollback(Const.ISROLLBACK) 
    public void testGetMaterialNameById() {
    	materialService.addMaterial(material);
    	//查询
    	String name = materialService.getMaterialNameById(material.getId());
    	Assert.assertNotNull("获取数据失败",name);
    }
    @Test
    @Rollback(Const.ISROLLBACK) 
    public void testGetListMaterial() {
    	// 获取教材集合
    	Assert.assertNotNull("获取教材集合失败", materialService.getListMaterial(material.getMaterialName()));
    	Assert.assertNotNull("获取教材集合失败", materialService.getListMaterial(null));
    }
    
    @Test
    @Rollback(Const.ISROLLBACK) 
    public void testGetPlanningEditorSum() {
    	// 获取教材集合
    	System.out.println(materialService.getPlanningEditorSum(6L, 985L));
    	Assert.assertNotNull("策划编辑总数获取失败", materialService.getPlanningEditorSum(6L, 985L));

    }
}





