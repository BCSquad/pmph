package com.bc.pmpheep.back.service.test;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.web.multipart.MultipartFile;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.PmphGroupFile;
import com.bc.pmpheep.back.service.PmphGroupFileService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.vo.PmphGroupFileVO;
import com.bc.pmpheep.test.BaseTest;

/**
 * AreaDao 单元测试
 * 
 * @author mryang
 */
public class PmphGroupFileServiceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(PmphGroupFileServiceTest.class);

	@Resource
	private PmphGroupFileService pmphGroupFileService;

	Random random = new Random();
	List<Long> ids = new ArrayList<Long>();
	PmphGroupFile pmphGroupFile = new PmphGroupFile(new Long(random.nextInt(200)), new Long(random.nextInt(200)),
			String.valueOf(random.nextInt(200)), "String fileName", random.nextInt(200), null);

	@Test
	public void testAdd() {
		pmphGroupFileService.updatePmphGroupFileOfDown(13L, "59e80ffa0c4398e12bd5f772");
		pmphGroupFileService.add(pmphGroupFile);
		Assert.assertTrue("添加失败", pmphGroupFile.getId() > 0);
	}

	@Test
	public void testUpdate() {
		pmphGroupFileService.add(pmphGroupFile);
		pmphGroupFile.setFileName(String.valueOf(random.nextInt(200)));
		Assert.assertTrue("更新失败", pmphGroupFileService.updatePmphGroupFile(pmphGroupFile) > 0);
	}

	@Test
	public void testUpdateDown() {
		pmphGroupFileService.add(pmphGroupFile);
		Assert.assertTrue("下载失败", pmphGroupFileService.updatePmphGroupFileOfDown(pmphGroupFile.getGroupId(),
				pmphGroupFile.getFileId()) > 0);
	}

	@Test
	public void testGetPmphGroupFileById() {
		pmphGroupFileService.add(pmphGroupFile);
		Assert.assertNotNull("获取数据失败", pmphGroupFileService.getPmphGroupFileById(pmphGroupFile.getId()));
	}

	@Test
	public void testListPmphGroupFileByGroupId() {
		pmphGroupFileService.add(pmphGroupFile);
		Assert.assertNotNull("获取数据失败", pmphGroupFileService.listPmphGroupFileByGroupId(pmphGroupFile.getGroupId()));
	}

	@Test
	public void testGetList() {
		pmphGroupFileService.add(pmphGroupFile);
		PageResult pageResult = new PageResult<>();
		PageParameter pageParameter = new PageParameter<>();
		PmphGroupFileVO fileVO = new PmphGroupFileVO();
		fileVO.setFileName(null);
		fileVO.setFileId(null);
		fileVO.setGroupId(pmphGroupFile.getGroupId());
		;
		pageParameter.setParameter(fileVO);
		pageParameter.setPageSize(15);
		pageResult = pmphGroupFileService.listGroupFile(pageParameter);
		Assert.assertNotNull("没有获取到数据", pageResult.getRows());
	}

}
