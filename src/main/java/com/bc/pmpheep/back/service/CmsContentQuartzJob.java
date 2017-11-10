package com.bc.pmpheep.back.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.bc.pmpheep.back.po.CmsContent;
import com.bc.pmpheep.back.po.CmsSchedule;
import com.bc.pmpheep.back.util.CollectionUtil;
import com.bc.pmpheep.back.util.DateUtil;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 
 * <pre>
 * 功能描述：CMS内容定时发布任务
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017-11-5
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
public class CmsContentQuartzJob {
    @Autowired
    CmsScheduleService cmsScheduleService;
    @Autowired
    CmsContentService  cmsContentService;

    // 定时执行的方法
    public void execute() throws CheckedServiceException {
        // 获取当前时间前5分钟定时任务数
        List<CmsSchedule> cmsSchedules =
        cmsScheduleService.getCmsScheduleList(new CmsSchedule(DateUtil.getCurrentTime()));
        if (CollectionUtil.isNotEmpty(cmsSchedules)) {
            List<Long> contentIds = new ArrayList<Long>(cmsSchedules.size());// contentId集合
            List<CmsContent> cmsContents = new ArrayList<CmsContent>(cmsSchedules.size());// content对象集合
            for (CmsSchedule cmsSchedule : cmsSchedules) {
                contentIds.add(cmsSchedule.getContentId());
                cmsContents.add(new CmsContent(cmsSchedule.getContentId(), true,
                                               cmsSchedule.getScheduledTime()));
            }
            // 按content对象集合批量更新
            cmsContentService.updateCmsContentByIds(cmsContents);
            // 按contentId批量删除
            cmsScheduleService.deleteCmsScheduleByIds(contentIds);
        }
        System.out.println("执行时间" + new Date());
    }
}
