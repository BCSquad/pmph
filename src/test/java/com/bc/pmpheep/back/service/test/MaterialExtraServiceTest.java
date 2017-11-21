package com.bc.pmpheep.back.service.test;
import java.util.Random;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.po.MaterialExtra;
import com.bc.pmpheep.test.BaseTest;
import com.bc.pmpheep.back.service.MaterialExtraService;
import com.bc.pmpheep.back.util.Const;
/**
 * AreaDao 单元测试
 *
 * @author mryang
 */
public class MaterialExtraServiceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(MaterialExtraServiceTest.class);
	
	@Resource
	private MaterialExtraService materialExtraService;
	
	Random r =new Random();
	MaterialExtra materialExtra=new MaterialExtra (new Long(r.nextInt(200)),"notice", "note");
	
    @Test
    @Rollback(Const.ISROLLBACK) 
    public void testAddMaterialExtra()  {
    	//新增
    	materialExtraService.addMaterialExtra(materialExtra);
    	Assert.assertTrue("添加失败",materialExtra.getId() > 0 );
    }
    
    @Test
    @Rollback(Const.ISROLLBACK) 
    public void testUpdateMaterialExtra()  {
    	materialExtraService.addMaterialExtra(materialExtra);
    	//修改
    	materialExtra.setNotice(String.valueOf(r.nextInt(200)));
    	Assert.assertTrue("更新失败",materialExtraService.updateMaterialExtra(materialExtra)> 0 );
    	//修改
    	materialExtra.setId(r.nextLong());
    	materialExtra.setNotice(String.valueOf(r.nextInt(200)));
    	Assert.assertTrue("更新失败",materialExtraService.updateMaterialExtra(materialExtra)>= 0 );
    	
    }
    @Test
    @Rollback(Const.ISROLLBACK) 
    public void testDeleteMaterialExtraById()  {
    	materialExtraService.addMaterialExtra(materialExtra);
    	//删除
    	Assert.assertTrue("删除失败",materialExtraService.deleteMaterialExtraById(materialExtra.getId()) > 0 );
    	//删除
    	Assert.assertTrue("删除失败",materialExtraService.deleteMaterialExtraById(r.nextLong()) >= 0 );
    	
    }
    @Test
    @Rollback(Const.ISROLLBACK) 
    public void testGetMaterialExtraById()  {
    	materialExtraService.addMaterialExtra(materialExtra);
    	//查询
    	Assert.assertNotNull("获取数据失败",materialExtraService.getMaterialExtraById(materialExtra.getId()));
    	materialExtraService.getMaterialExtraById(r.nextLong());
    }
    
    
}





