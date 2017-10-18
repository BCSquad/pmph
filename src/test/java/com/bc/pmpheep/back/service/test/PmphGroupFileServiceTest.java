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
    Logger                       logger = LoggerFactory.getLogger(PmphGroupFileServiceTest.class);

    @Resource
    private PmphGroupFileService pmphGroupFileService;

    @Test
    @Rollback(Const.ISROLLBACK)
    public void test() throws Exception {
        Random random = new Random();
        List<Long> ids = new ArrayList<Long>();
        List<PmphGroupFile> files = new ArrayList<PmphGroupFile>();
        PmphGroupFile pmphGroupFile =
        new PmphGroupFile(new Long(random.nextInt(200)), new Long(random.nextInt(200)),
                          String.valueOf(random.nextInt(200)), "String fileName",
                          random.nextInt(200), null);
        files.add(pmphGroupFile);
        logger.info("---PmphGroupFileSevice 测试---------------------------------------------------------------------------------");
        // 新增
        File file = new File("C:/Users/Administrator/Desktop/学校信息.xlsx");
        FileInputStream fis = new FileInputStream(file);
        MockMultipartFile multipartFile =
        new MockMultipartFile("C:/Users/Administrator/Desktop", "学校信息.xlsx",
                              "application/vnd_ms-excel", fis);
        List<MultipartFile> multipartFiles = new ArrayList<MultipartFile>(1);
        List<Long> goupIds = new ArrayList<Long>(1);
        goupIds.add(1L);
        pmphGroupFileService.addPmphGroupFile(goupIds, multipartFiles, "");
        Assert.assertTrue("添加失败", pmphGroupFile.getId() > 0);
        // 修改
        pmphGroupFile.setFileName(String.valueOf(random.nextInt(200)));
        Assert.assertTrue("更新失败", pmphGroupFileService.updatePmphGroupFile(pmphGroupFile) > 0);
        // 删除
        ids.add(1L);
        ids.add(2L);
        ids.add(3L);
        Assert.assertTrue("删除失败",
                          pmphGroupFileService.deletePmphGroupFileById(ids).equals("SUCCESS"));
        // 查询
        Assert.assertNotNull("获取数据失败", pmphGroupFileService.getPmphGroupFileById(5L));

    }

    @Test
    public void getList() {
        PageResult pageResult = new PageResult<>();
        PageParameter pageParameter = new PageParameter<>();
        PmphGroupFileVO fileVO = new PmphGroupFileVO();
        fileVO.setFileName("555");
        fileVO.setFileId("7");
        fileVO.setGroupId(2L);
        ;
        pageParameter.setParameter(fileVO);
        pageParameter.setPageSize(15);
        pageResult = pmphGroupFileService.listGroupFile(pageParameter);
        Assert.assertNotNull(pageResult);
        if (pageResult.getRows().isEmpty()) {
            logger.info("获取失败");
        } else {
            logger.info("获取成功");
        }
    }

}
