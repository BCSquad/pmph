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
 */
public class MaterialServiceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(MaterialServiceTest.class);
	
	@Resource
	private MaterialService materialService;
	
    @Test
    @Rollback(Const.ISROLLBACK) 
    public void test() {
    	Random random =new Random();
    	Material material=new Material ("materialName",random.nextInt(200),
    			new Long(random.nextInt(200)), new Date(), new Date(),
    			new Date(),"mailAddress",new Short("1"),
    			new Long(random.nextInt(200)), new Long(random.nextInt(200)),new Long(random.nextInt(200)),
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
    			new Long(random.nextInt(200)), null, new Long(random.nextInt(200)));
    	logger.info("---MaterialService 测试---------------------------------------------------------------------------------");
    	//新增
    	materialService.addMaterial(material);
    	Assert.assertTrue("添加失败",material.getId() > 0 );
    	//修改
    	material.setMaterialName(String.valueOf(random.nextInt(200)));
    	Assert.assertTrue("更新失败", materialService.updateMaterial(material) > 0 );
    	//删除
    	Assert.assertTrue("删除失败",materialService.deleteMaterialById(2L)  >= 0 );
    	//查询
    	Assert.assertNotNull("获取数据失败",materialService.getMaterialById(3L));
    	
    }
    
    
    
}





