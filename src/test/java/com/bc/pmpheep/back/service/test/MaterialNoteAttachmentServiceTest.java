/**
 * 
 */
package com.bc.pmpheep.back.service.test;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bc.pmpheep.back.po.MaterialNoteAttachment;
import com.bc.pmpheep.back.service.MaterialNoteAttachmentService;
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

	@Test
	public void test() {
		MaterialNoteAttachment materialNoteAttachment= new MaterialNoteAttachment();
		materialNoteAttachment.setMaterialExtraId(2L);
		materialNoteAttachment.setAttachment("--");
		materialNoteAttachment.setAttachmentName("---");
		materialNoteAttachment.setDownload(1L);
		materialNoteAttachment=materialNoteAttachmentService.addMaterialNoteAttachment(materialNoteAttachment);
		Assert.assertTrue("新增失败", materialNoteAttachment.getId() != null);
		materialNoteAttachment.setAttachmentName("++++++");
		Integer res=materialNoteAttachmentService.updateMaterialNoteAttachment(materialNoteAttachment);
		Assert.assertTrue("更新失败", res != null);
	}
}
