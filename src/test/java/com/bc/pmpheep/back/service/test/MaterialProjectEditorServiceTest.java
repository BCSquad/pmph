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
import com.bc.pmpheep.back.po.MaterialProjectEditor;
import com.bc.pmpheep.test.BaseTest;
import com.bc.pmpheep.back.service.MaterialOrgService;
import com.bc.pmpheep.back.service.MaterialProjectEditorService;
import com.bc.pmpheep.back.util.Const;

/**
 * AreaDao 单元测试
 *
 * @author mryang
 */
public class MaterialProjectEditorServiceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(MaterialProjectEditorServiceTest.class);

	@Resource
	private MaterialProjectEditorService materialProjectEditorService;

	@Test
	@Rollback(Const.ISROLLBACK)
	public void test() {
		MaterialProjectEditor materialProjectEditor1= new MaterialProjectEditor(1L,1L);
		MaterialProjectEditor MaterialProjectEditor2= new MaterialProjectEditor(1L,2L);
		MaterialProjectEditor MaterialProjectEditor3= new MaterialProjectEditor(2L,1L);
		MaterialProjectEditor MaterialProjectEditor4= new MaterialProjectEditor(2L,2L);
		List<MaterialProjectEditor> materialOrgs =new ArrayList<>();
		materialOrgs.add(MaterialProjectEditor2);
		materialOrgs.add(MaterialProjectEditor3);
		materialOrgs.add(MaterialProjectEditor4);
		Assert.assertTrue("addMaterialProjectEditor 添加失败", materialProjectEditorService.addMaterialProjectEditor(materialProjectEditor1).getId() > 0);
		Assert.assertTrue("addMaterialProjectEditor 添加失败", materialProjectEditorService.addMaterialProjectEditor(materialProjectEditor1).getId() > 0);
		Assert.assertTrue("addMaterialOrgs添加失败", materialProjectEditorService.addMaterialProjectEditors(materialOrgs) >= 0);
		Assert.assertTrue("deleteMaterialOrgByMaterialId删除失败",materialProjectEditorService.deleteMaterialProjectEditorByMaterialId(1L) > 0);
		
	}

}
