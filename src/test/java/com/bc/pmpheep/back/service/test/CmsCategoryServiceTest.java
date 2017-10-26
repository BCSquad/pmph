package com.bc.pmpheep.back.service.test;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bc.pmpheep.back.po.CmsCategory;
import com.bc.pmpheep.back.service.CmsCategoryService;
import com.bc.pmpheep.test.BaseTest;

/**
 * 
 * <pre>
 * 功能描述：CmsCategoryService 测试类
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
public class CmsCategoryServiceTest extends BaseTest {
    Logger             logger = LoggerFactory.getLogger(CmsCategoryServiceTest.class);

    @Resource
    CmsCategoryService cmsCategoryService;

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
        CmsCategory category =
        cmsCategoryService.addCmsCategory(new CmsCategory(1L, "d:/pmph", "类别名称", true));
        logger.info(category.getPath());
        Assert.assertNotNull("插入内容后返回的CmsCategory.id不应为空", category.getId());
        // uddate
        Integer count =
        cmsCategoryService.updateCmsCategory(new CmsCategory(category.getId(), "d:/pmph/img",
                                                             "图片路径"));
        Assert.assertTrue("是否更新CmsCategory成功", count > 0 ? true : false);
        // getById
        CmsCategory cms = cmsCategoryService.getCmsCategoryById(category.getId());
        logger.info(cms.toString());
        Assert.assertNotNull("按ID查询是否该对象", cms);
        // delete
        // Assert.assertTrue("是否删除成功",
        // cmsCategoryService.deleteCmsCategoryById(category.getId()) > 0 ? true : false);
        List<Long> idList = new ArrayList<Long>(1);
        idList.add(category.getId());
        Assert.assertTrue("批量删除是否成功",
                          cmsCategoryService.deleteCmsCategoryByIds(idList) > 0 ? true : false);

    }
}
