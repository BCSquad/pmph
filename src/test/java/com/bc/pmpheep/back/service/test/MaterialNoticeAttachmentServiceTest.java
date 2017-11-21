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

import com.bc.pmpheep.back.po.MaterialNoticeAttachment;
import com.bc.pmpheep.back.service.MaterialNoticeAttachmentService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.test.BaseTest;

/**
 * <p>
 * MaterialNoticeAttachmentService测试类
 * <p>
 * 
 * @author yangliang
 * @date 2017年9月25日 上午11:26:59
 */
public class MaterialNoticeAttachmentServiceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(MaterialNoticeAttachmentServiceTest.class);
	@Resource
	MaterialNoticeAttachmentService materialNoticeAttachmentService;
	
	Random r =new Random();

	@Test
	@Rollback(Const.ISROLLBACK) 
	public void testAddMaterialNoticeAttachment() {
		MaterialNoticeAttachment materialNoticeAttachment= new MaterialNoticeAttachment();
		materialNoticeAttachment.setMaterialExtraId(2L);
		materialNoticeAttachment.setAttachment("--");
		materialNoticeAttachment.setAttachmentName("---");
		materialNoticeAttachment.setDownload(1L);
		//新增
		materialNoticeAttachment=materialNoticeAttachmentService.addMaterialNoticeAttachment(materialNoticeAttachment);
		Assert.assertTrue("新增失败", materialNoticeAttachment.getId() != null);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK) 
	public void testUpdateMaterialNoticeAttachment() {
		MaterialNoticeAttachment materialNoticeAttachment= new MaterialNoticeAttachment();
		materialNoticeAttachment.setMaterialExtraId(2L);
		materialNoticeAttachment.setAttachment("--");
		materialNoticeAttachment.setAttachmentName("---");
		materialNoticeAttachment.setDownload(1L);
		materialNoticeAttachment=materialNoticeAttachmentService.addMaterialNoticeAttachment(materialNoticeAttachment);
		//修改
		materialNoticeAttachment.setAttachmentName("++++++");
		Integer res=materialNoticeAttachmentService.updateMaterialNoticeAttachment(materialNoticeAttachment);
		Assert.assertTrue("updateMaterialNoticeAttachment更新失败", res != null);
		materialNoticeAttachment.setId(r.nextLong());
		materialNoticeAttachmentService.updateMaterialNoticeAttachment(materialNoticeAttachment);
		
		
		
		
	}
	
	@Test
	@Rollback(Const.ISROLLBACK) 
	public void testDeleteMaterialNoticeAttachmentById() {
		MaterialNoticeAttachment materialNoticeAttachment= new MaterialNoticeAttachment();
		materialNoticeAttachment.setMaterialExtraId(2L);
		materialNoticeAttachment.setAttachment("--");
		materialNoticeAttachment.setAttachmentName("---");
		materialNoticeAttachment.setDownload(1L);
		materialNoticeAttachment=materialNoticeAttachmentService.addMaterialNoticeAttachment(materialNoticeAttachment);
		//删除
		Integer res=materialNoticeAttachmentService.deleteMaterialNoticeAttachmentById(materialNoticeAttachment.getId());
		Assert.assertTrue("deleteMaterialNoticeAttachmentsById删除失败", res >0);
		//删除
		res=materialNoticeAttachmentService.deleteMaterialNoticeAttachmentById(r.nextLong());
		Assert.assertTrue("deleteMaterialNoticeAttachmentsById删除失败", res >= 0);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK) 
	public void testDeleteMaterialNoticeAttachmentsByMaterialExtraId() {
		MaterialNoticeAttachment materialNoticeAttachment= new MaterialNoticeAttachment();
		materialNoticeAttachment.setMaterialExtraId(2L);
		materialNoticeAttachment.setAttachment("--");
		materialNoticeAttachment.setAttachmentName("---");
		materialNoticeAttachment.setDownload(1L);
		materialNoticeAttachment=materialNoticeAttachmentService.addMaterialNoticeAttachment(materialNoticeAttachment);
		//再插入一个对象
		materialNoticeAttachment= new MaterialNoticeAttachment();
		materialNoticeAttachment.setMaterialExtraId(2L);
		materialNoticeAttachment.setAttachment("---");
		materialNoticeAttachment.setAttachmentName("----");
		materialNoticeAttachment.setDownload(1L);
		materialNoticeAttachment=materialNoticeAttachmentService.addMaterialNoticeAttachment(materialNoticeAttachment);
		//删除通过MaterialExtraId
		Integer res=materialNoticeAttachmentService.deleteMaterialNoticeAttachmentsByMaterialExtraId(materialNoticeAttachment.getMaterialExtraId());
		Assert.assertTrue("deleteMaterialNoticeAttachmentsByMaterialExtraId删除失败", res > 0);
		Assert.assertTrue("deleteMaterialNoticeAttachmentsByMaterialExtraId删除失败", materialNoticeAttachmentService.deleteMaterialNoticeAttachmentsByMaterialExtraId(r.nextLong()) >= 0);
		
	}
	
	@Test
	@Rollback(Const.ISROLLBACK) 
	public void testGetMaterialNoticeAttachmentsByMaterialExtraId() {
		MaterialNoticeAttachment materialNoticeAttachment= new MaterialNoticeAttachment();
		materialNoticeAttachment.setMaterialExtraId(2L);
		materialNoticeAttachment.setAttachment("--");
		materialNoticeAttachment.setAttachmentName("---");
		materialNoticeAttachment.setDownload(1L);
		materialNoticeAttachment=materialNoticeAttachmentService.addMaterialNoticeAttachment(materialNoticeAttachment);
		//再插入一个对象
		materialNoticeAttachment= new MaterialNoticeAttachment();
		materialNoticeAttachment.setMaterialExtraId(2L);
		materialNoticeAttachment.setAttachment("---");
		materialNoticeAttachment.setAttachmentName("----");
		materialNoticeAttachment.setDownload(1L);
		materialNoticeAttachment=materialNoticeAttachmentService.addMaterialNoticeAttachment(materialNoticeAttachment);
		//根据MaterialExtraId获取
		List<MaterialNoticeAttachment>materialNoticeAttachments=materialNoticeAttachmentService.getMaterialNoticeAttachmentsByMaterialExtraId(materialNoticeAttachment.getMaterialExtraId());
		Assert.assertTrue("获取失败", materialNoticeAttachments != null);
	}
	
}











