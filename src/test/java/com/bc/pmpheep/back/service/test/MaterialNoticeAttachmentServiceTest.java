/**
 * 
 */
package com.bc.pmpheep.back.service.test;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bc.pmpheep.back.po.MaterialNoticeAttachment;
import com.bc.pmpheep.back.service.MaterialNoticeAttachmentService;
import com.bc.pmpheep.test.BaseTest;

/**
 * <p>
 * Title:DecAcadeService测试类
 * <p>
 * 
 * @author lyc
 * @date 2017年9月25日 上午11:26:59
 */
public class MaterialNoticeAttachmentServiceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(MaterialNoticeAttachmentServiceTest.class);
	@Resource
	MaterialNoticeAttachmentService materialNoticeAttachmentService;

	@Test
	public void test() {
		MaterialNoticeAttachment materialNoticeAttachment= new MaterialNoticeAttachment();
		materialNoticeAttachment.setMaterialExtraId(2L);
		materialNoticeAttachment.setAttachment("--");
		materialNoticeAttachment.setAttachmentName("---");
		materialNoticeAttachment.setDownload(1L);
		materialNoticeAttachment=materialNoticeAttachmentService.addMaterialNoticeAttachment(materialNoticeAttachment);
		Assert.assertTrue("新增失败", materialNoticeAttachment.getId() != null);
		materialNoticeAttachment.setAttachmentName("++++++");
		Integer res=materialNoticeAttachmentService.updateMaterialNoticeAttachment(materialNoticeAttachment);
		Assert.assertTrue("更新失败", res != null);
	}
}
