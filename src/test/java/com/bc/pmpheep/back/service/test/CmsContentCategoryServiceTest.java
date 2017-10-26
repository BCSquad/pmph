package com.bc.pmpheep.back.service.test;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bc.pmpheep.back.po.CmsContentCategory;
import com.bc.pmpheep.back.service.CmsContentCategoryService;
import com.bc.pmpheep.test.BaseTest;

/**
 * 
 * <pre>
 * 功能描述：CmsContentCategoryService 测试类
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
public class CmsContentCategoryServiceTest extends BaseTest {
    Logger                    logger = LoggerFactory.getLogger(CmsContentCategoryServiceTest.class);

    @Resource
    CmsContentCategoryService cmsContentCategoryService;

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
        CmsContentCategory cmsContentCategory =
        cmsContentCategoryService.addCmsContentCategory(new CmsContentCategory(1L, 2L));
        Assert.assertNotNull("插入内容后返回的CmsContentCategory.id不应为空", cmsContentCategory.getId());
        // uddate
        Integer count =
        cmsContentCategoryService.updateCmsContentCategory(new CmsContentCategory(
                                                                                  cmsContentCategory.getId(),
                                                                                  32L, 3L));
        Assert.assertTrue("是否更新CmsContentCategory成功", count > 0 ? true : false);
        // getById
        CmsContentCategory cms =
        cmsContentCategoryService.getCmsContentCategoryById(cmsContentCategory.getId());
        logger.info(cms.toString());
        Assert.assertNotNull("按ID查询是否该对象", cms);
        // delete
        // Assert.assertTrue("是否删除成功",
        // cmsContentCategoryService.deleteCmsContentCategoryById(cmsContentCategory.getId()) > 0 ?
        // true :
        // false);
        List<Long> idList = new ArrayList<Long>(1);
        idList.add(cmsContentCategory.getId());
        Assert.assertTrue("批量删除是否成功",
                          cmsContentCategoryService.deleteCmsContentCategoryByIds(idList) > 0 ? true : false);

    }
}
