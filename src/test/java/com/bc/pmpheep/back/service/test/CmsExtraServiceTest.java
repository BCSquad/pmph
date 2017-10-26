package com.bc.pmpheep.back.service.test;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bc.pmpheep.back.po.CmsExtra;
import com.bc.pmpheep.back.service.CmsExtraService;
import com.bc.pmpheep.test.BaseTest;

/**
 * 
 * <pre>
 * 功能描述：CmsExtraService 测试类
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
public class CmsExtraServiceTest extends BaseTest {
    Logger          logger = LoggerFactory.getLogger(CmsExtraServiceTest.class);

    @Resource
    CmsExtraService cmsExtraService;

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
        CmsExtra cmsExtra = cmsExtraService.addCmsExtra(new CmsExtra(1L, "附件", "附件名称", 10L));
        logger.info(cmsExtra.getAttachmentName());
        Assert.assertNotNull("插入内容后返回的CmsExtra.id不应为空", cmsExtra.getId());
        // uddate
        Integer count = cmsExtraService.updateCmsExtra(new CmsExtra(cmsExtra.getId(), 2L, "图片路径"));
        Assert.assertTrue("是否更新CmsExtra成功", count > 0 ? true : false);
        // getById
        CmsExtra cms = cmsExtraService.getCmsExtraById(cmsExtra.getId());
        logger.info(cms.toString());
        Assert.assertNotNull("按ID查询是否该对象", cms);
        // delete
        // Assert.assertTrue("是否删除成功",
        // cmsExtraService.deleteCmsExtraById(cmsExtra.getId()) > 0 ? true : false);
        List<Long> idList = new ArrayList<Long>(1);
        idList.add(cmsExtra.getId());
        Assert.assertTrue("批量删除是否成功",
                          cmsExtraService.deleteCmsExtraByIds(idList) > 0 ? true : false);

    }
}
