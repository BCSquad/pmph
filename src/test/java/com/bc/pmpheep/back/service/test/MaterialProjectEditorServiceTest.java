package com.bc.pmpheep.back.service.test;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import com.bc.pmpheep.back.po.MaterialProjectEditor;
import com.bc.pmpheep.test.BaseTest;
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
	public void testAddMaterialProjectEditor() {
		MaterialProjectEditor materialProjectEditor1= new MaterialProjectEditor(1L,1L);
		Assert.assertTrue("addMaterialProjectEditor 添加失败", materialProjectEditorService.addMaterialProjectEditor(materialProjectEditor1).getId() > 0);
	}
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testAddMaterialProjectEditors() {
		MaterialProjectEditor MaterialProjectEditor2= new MaterialProjectEditor(1L,2L);
		MaterialProjectEditor MaterialProjectEditor3= new MaterialProjectEditor(2L,1L);
		MaterialProjectEditor MaterialProjectEditor4= new MaterialProjectEditor(2L,2L);
		List<MaterialProjectEditor> materialOrgs =new ArrayList<>();
		materialOrgs.add(MaterialProjectEditor2);
		materialOrgs.add(MaterialProjectEditor3);
		materialOrgs.add(MaterialProjectEditor4);
		Assert.assertTrue("addMaterialOrgs添加失败", materialProjectEditorService.addMaterialProjectEditors(materialOrgs) >= 0);
	}
	
	
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testListMaterialProjectEditors() {
		List<MaterialProjectEditor> materialProjectEditor = materialProjectEditorService.listMaterialProjectEditors(13L);
		Assert.assertTrue("listMaterialProjectEditors查询失败",materialProjectEditor  != null );
		List<MaterialProjectEditor> materialProjectEditor2 = materialProjectEditorService.listMaterialProjectEditors(12345678987654L);
		Assert.assertTrue("listMaterialProjectEditors查询失败",materialProjectEditor2 != null );
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void testDeleteMaterialProjectEditorByMaterialId() {
		Assert.assertTrue("deleteMaterialOrgByMaterialId删除失败",materialProjectEditorService.deleteMaterialProjectEditorByMaterialId(1L) >= 0);
	}

}
