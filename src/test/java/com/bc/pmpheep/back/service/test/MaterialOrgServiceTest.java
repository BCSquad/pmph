package com.bc.pmpheep.back.service.test;

import java.util.ArrayList;
import java.util.List;
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

	@Test
	@Rollback(Const.ISROLLBACK)
	public void test() {
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
		Assert.assertTrue("addMaterialOrgs添加失败", materialOrgService.addMaterialOrgs(materialOrgs) >= 0);
		Assert.assertTrue("deleteMaterialOrgByMaterialId删除失败",materialOrgService.deleteMaterialOrgByMaterialId(1L) > 0);
		Assert.assertTrue("deleteMaterialOrgByMaterialId删除失败",materialOrgService.deleteMaterialOrgByOrgId(1L) > 0);
	}

}
