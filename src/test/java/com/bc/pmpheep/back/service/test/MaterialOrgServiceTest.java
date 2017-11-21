package com.bc.pmpheep.back.service.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.po.MaterialOrg;
import com.bc.pmpheep.test.BaseTest;
import com.bc.pmpheep.back.service.MaterialOrgService;
import com.bc.pmpheep.back.util.Const;

/**
 * AreaDao 单元测试
 *
 * @author mryang
 */
public class MaterialOrgServiceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(MaterialOrgService.class);

	@Resource
	private MaterialOrgService materialOrgService;
	
	Random r =new Random();

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testAddMaterialOrg() {
		MaterialOrg materialOrg1= new MaterialOrg(1L,1L);
		//添加
		Assert.assertTrue("addMaterialOrg 添加失败", materialOrgService.addMaterialOrg(materialOrg1).getId() > 0);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testAddMaterialOrgs() {
		MaterialOrg materialOrg1= new MaterialOrg(1L,1L);
		MaterialOrg materialOrg2= new MaterialOrg(1L,2L);
		MaterialOrg materialOrg3= new MaterialOrg(2L,1L);
		MaterialOrg materialOrg4= new MaterialOrg(2L,2L);
		List<MaterialOrg> materialOrgs =new ArrayList<>();
		materialOrgs.add(materialOrg2);
		materialOrgs.add(materialOrg3);
		materialOrgs.add(materialOrg4);
		Assert.assertTrue("addMaterialOrg 添加失败", materialOrgService.addMaterialOrg(materialOrg1).getId() > 0);
		Assert.assertTrue("addMaterialOrg 添加失败", materialOrgService.addMaterialOrg(materialOrg3).getId() > 0);
		//批量新增 
		Assert.assertTrue("addMaterialOrgs添加失败", materialOrgService.addMaterialOrgs(materialOrgs) > 0);
		
		
	}
	

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testDeleteMaterialOrgByMaterialId() {
		MaterialOrg materialOrg1= new MaterialOrg(1L,1L);
		MaterialOrg materialOrg2= new MaterialOrg(1L,2L);
		Assert.assertTrue("addMaterialOrg 添加失败", materialOrgService.addMaterialOrg(materialOrg1).getId() > 0);
		Assert.assertTrue("addMaterialOrg 添加失败", materialOrgService.addMaterialOrg(materialOrg2).getId() > 0);
		//根据materialId删除materialOrg
		Assert.assertTrue("deleteMaterialOrgByMaterialId删除失败",materialOrgService.deleteMaterialOrgByMaterialId(1L) > 0);
		Assert.assertTrue("deleteMaterialOrgByMaterialId删除失败",materialOrgService.deleteMaterialOrgByMaterialId(r.nextLong()) >= 0);
		
	}

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testDeleteMaterialOrgByOrgId() {
		MaterialOrg materialOrg1= new MaterialOrg(1L,1L);
		MaterialOrg materialOrg3= new MaterialOrg(2L,1L);
		Assert.assertTrue("addMaterialOrg 添加失败", materialOrgService.addMaterialOrg(materialOrg1).getId() > 0);
		Assert.assertTrue("addMaterialOrg 添加失败", materialOrgService.addMaterialOrg(materialOrg3).getId() > 0);
		//根据orgId删除materialOrg
		Assert.assertTrue("deleteMaterialOrgByMaterialId删除失败",materialOrgService.deleteMaterialOrgByOrgId(1L) > 0);
		Assert.assertTrue("deleteMaterialOrgByMaterialId删除失败",materialOrgService.deleteMaterialOrgByOrgId(r.nextLong()) >= 0);
	}


}
