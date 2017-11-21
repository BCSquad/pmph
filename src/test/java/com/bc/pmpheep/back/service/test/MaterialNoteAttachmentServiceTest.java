/**
 * 
 */
package com.bc.pmpheep.back.service.test;

import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.po.MaterialNoteAttachment;
import com.bc.pmpheep.back.service.MaterialNoteAttachmentService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.test.BaseTest;

/**
 * <p>
 * MaterialNoteAttachmentService测试类
 * <p>
 * 
 * @author mryang
 * @date 2017年9月25日 上午11:26:59
 */
public class MaterialNoteAttachmentServiceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(MaterialNoteAttachmentServiceTest.class);
	@Resource
	MaterialNoteAttachmentService materialNoteAttachmentService;

	Random r =new Random();
	
	@Test
	@Rollback(Const.ISROLLBACK) 
	public void testAddMaterialNoteAttachment() {
		MaterialNoteAttachment materialNoteAttachment= new MaterialNoteAttachment();
		materialNoteAttachment.setMaterialExtraId(2L);
		materialNoteAttachment.setAttachment("--");
		materialNoteAttachment.setAttachmentName("---");
		materialNoteAttachment.setDownload(1L);
		//新增
		materialNoteAttachment=materialNoteAttachmentService.addMaterialNoteAttachment(materialNoteAttachment);
		Assert.assertTrue("新增失败", materialNoteAttachment.getId() != null);
		
		
	}
	
	@Test
	@Rollback(Const.ISROLLBACK) 
	public void testUpdateMaterialNoteAttachment() {
		MaterialNoteAttachment materialNoteAttachment= new MaterialNoteAttachment();
		materialNoteAttachment.setMaterialExtraId(2L);
		materialNoteAttachment.setAttachment("--");
		materialNoteAttachment.setAttachmentName("---");
		materialNoteAttachment.setDownload(1L);
		materialNoteAttachment.setAttachmentName("++++++");
		materialNoteAttachmentService.addMaterialNoteAttachment(materialNoteAttachment);
		//更新
		Integer res=materialNoteAttachmentService.updateMaterialNoteAttachment(materialNoteAttachment);
		Assert.assertTrue("更新失败", res >0);
		materialNoteAttachment.setId(r.nextLong());
		Assert.assertTrue("更新失败", materialNoteAttachmentService.updateMaterialNoteAttachment(materialNoteAttachment) >=0);
		
		
		
	}
	
	@Test
	@Rollback(Const.ISROLLBACK) 
	public void testGetMaterialNoteAttachmentByMaterialExtraId() {
		MaterialNoteAttachment materialNoteAttachment= new MaterialNoteAttachment();
		materialNoteAttachment.setMaterialExtraId(2L);
		materialNoteAttachment.setAttachment("--");
		materialNoteAttachment.setAttachmentName("---");
		materialNoteAttachment.setDownload(1L);
		materialNoteAttachment.setAttachmentName("++++++");
		materialNoteAttachmentService.addMaterialNoteAttachment(materialNoteAttachment);
		//根据MaterialExtraId获取
		List<MaterialNoteAttachment> materialNoteAttachments=materialNoteAttachmentService.getMaterialNoteAttachmentByMaterialExtraId(materialNoteAttachment.getMaterialExtraId());
		Assert.assertTrue("获取失败", materialNoteAttachments != null);
		materialNoteAttachmentService.getMaterialNoteAttachmentByMaterialExtraId(r.nextLong());
	}
	
	@Test
	@Rollback(Const.ISROLLBACK) 
	public void testDeleteMaterialNoteAttachmentById() {
		MaterialNoteAttachment materialNoteAttachment= new MaterialNoteAttachment();
		materialNoteAttachment.setMaterialExtraId(2L);
		materialNoteAttachment.setAttachment("--");
		materialNoteAttachment.setAttachmentName("---");
		materialNoteAttachment.setDownload(1L);
		materialNoteAttachment.setAttachmentName("++++++");
		materialNoteAttachmentService.addMaterialNoteAttachment(materialNoteAttachment);
		//根据主键id获取
		Assert.assertTrue("获取失败", materialNoteAttachmentService.deleteMaterialNoteAttachmentById(materialNoteAttachment.getId()) !=null );
	
		
	}
	
	@Test
	@Rollback(Const.ISROLLBACK) 
	public void testDeleteMaterialNoteAttachmentByMaterialExtraId() {
		MaterialNoteAttachment materialNoteAttachment= new MaterialNoteAttachment();
		materialNoteAttachment.setMaterialExtraId(2L);
		materialNoteAttachment.setAttachment("--");
		materialNoteAttachment.setAttachmentName("---");
		materialNoteAttachment.setDownload(1L);
		materialNoteAttachment.setAttachmentName("++++++");
		materialNoteAttachmentService.addMaterialNoteAttachment(materialNoteAttachment);
		//根据主键MaterialExtraId获取
		Assert.assertTrue("获取失败",materialNoteAttachmentService.deleteMaterialNoteAttachmentByMaterialExtraId(2L) != null );
	}
}
