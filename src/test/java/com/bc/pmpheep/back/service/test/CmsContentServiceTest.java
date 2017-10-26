package com.bc.pmpheep.back.service.test;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bc.pmpheep.back.po.CmsContent;
import com.bc.pmpheep.back.service.CmsContentService;
import com.bc.pmpheep.back.util.DateUtil;
import com.bc.pmpheep.test.BaseTest;

/**
 * 
 * <pre>
 * 功能描述：CmsContentService 测试类
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017-10-26
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
public class CmsContentServiceTest extends BaseTest {
    Logger            logger = LoggerFactory.getLogger(CmsContentServiceTest.class);

    @Resource
    CmsContentService cmsContentService;

    /**
     * 
     * <pre>
     * 功能描述：add 方法测试
     * 使用示范：
     *
     * </pre>
     */
    @Test
    public void serviceAllMethodTest() {
        // add
        CmsContent cmsContent =
        cmsContentService.addCmsContent(new CmsContent(1L, "d:/pmph", "内容id", "标题", "摘要", "关键字",
                                                       (short) 1, DateUtil.getCurrentTime(),
                                                       DateUtil.getCurrentTime(),
                                                       DateUtil.getCurrentTime(), 2L,
                                                       DateUtil.getCurrentTime(),
                                                       DateUtil.getCurrentTime()));
        logger.info(cmsContent.getPath());
        Assert.assertNotNull("插入内容后返回的CmsContent.id不应为空", cmsContent.getId());
        // uddate
        Integer count =
        cmsContentService.updateCmsContent(new CmsContent(cmsContent.getId(), 3L, "d:/pmph/img",
                                                          DateUtil.getCurrentTime()));
        Assert.assertTrue("是否更新CmsContent成功", count > 0 ? true : false);
        // getById
        CmsContent cms = cmsContentService.getCmsContentById(cmsContent.getId());
        logger.info(cms.toString());
        Assert.assertNotNull("按ID查询是否该对象", cms);
        // delete
        // Assert.assertTrue("是否删除成功",
        // cmsContentService.deleteCmsContentById(cmsContent.getId()) > 0 ? true : false);
        List<Long> idList = new ArrayList<Long>(1);
        idList.add(cmsContent.getId());
        Assert.assertTrue("批量删除是否成功",
                          cmsContentService.deleteCmsContentByIds(idList) > 0 ? true : false);

    }
}