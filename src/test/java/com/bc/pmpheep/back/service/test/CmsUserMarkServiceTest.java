package com.bc.pmpheep.back.service.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.bc.pmpheep.back.po.CmsUserMark;
import com.bc.pmpheep.back.service.CmsUserMarkService;
import com.bc.pmpheep.back.util.DateUtil;
import com.bc.pmpheep.test.BaseTest;

/**
 * 
 * <pre>
 * 功能描述：CmsUserMarkService 测试类
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
public class CmsUserMarkServiceTest extends BaseTest {
    Logger             logger = LoggerFactory.getLogger(CmsUserMarkServiceTest.class);

    @Autowired
    CmsUserMarkService cmsUserMarkService;

    /**
     * 
     * <pre>
     * 功能描述：add 方法测试
     * 使用示范：
     *
     * </pre>
     */
    @Test
    public void testAddCmsUserMark() {
        CmsUserMark cmsUserMark = this.addCmsUserMark();
        logger.info(cmsUserMark.toString());
        Assert.assertNotNull("插入内容后返回的CmsUserMark.id不应为空", cmsUserMark.getId());
    }

    @Test
    public void testUpdateCmsUserMark() {
        CmsUserMark cmsUserMark = this.addCmsUserMark();
        Integer count =
        cmsUserMarkService.updateCmsUserMark(new CmsUserMark(cmsUserMark.getId(), 4L, 6L, 7L,
                                                             DateUtil.getCurrentTime()));
        Assert.assertTrue("是否更新CmsUserMark成功", count > 0);

    }

    @Test
    public void testGetCmsUserMarkById() {
        CmsUserMark cmsUserMark = this.addCmsUserMark();
        CmsUserMark cms = cmsUserMarkService.getCmsUserMarkById(cmsUserMark.getId());
        logger.info(cms.toString());
        Assert.assertNotNull("按ID查询是否该对象", cms);

    }

    @Test
    public void testDeleteCmsUserMarkById() {
        CmsUserMark cmsUserMark = this.addCmsUserMark();
        Assert.assertTrue("是否删除成功",
                          cmsUserMarkService.deleteCmsUserMarkById(cmsUserMark.getId()) > 0);
        CmsUserMark cum = this.addCmsUserMark();
        List<Long> idList = new ArrayList<Long>(1);
        idList.add(cum.getId());
        Assert.assertTrue("批量删除是否成功", cmsUserMarkService.deleteCmsUserMarkByIds(idList) > 0);

    }

    private CmsUserMark addCmsUserMark() {
        CmsUserMark cmsUserMark =
        cmsUserMarkService.addCmsUserMark(new CmsUserMark(2L, 3L, 4L, DateUtil.getCurrentTime()));
        return cmsUserMark;
    }
}
