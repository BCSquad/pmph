package com.bc.pmpheep.back.service;

import java.util.List;

import com.bc.pmpheep.back.po.CmsSchedule;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 
 * <pre>
 * 功能描述：CmsScheduleService 接口
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017-10-25
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
public interface CmsScheduleService {
    /**
     * 
     * <pre>
     * 功能描述：新增
     * 使用示范：
     *
     * @param cmsSchedule  CmsSchedule对象
     * @return CmsSchedule对象
     * @throws CheckedServiceException
     * </pre>
     */
    CmsSchedule addCmsSchedule(CmsSchedule cmsSchedule) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：修改CmsCategory
     * 使用示范：
     *
     * @param cmsSchedule 
     * @return 影响行数
     * @throws CheckedServiceException
     * </pre>
     */
    Integer updateCmsSchedule(CmsSchedule cmsSchedule) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：查询CmsSchedule列表(全部)
     * 使用示范：
     *
     * @param cmsSchedule 
     * @return CmsSchedule集合对象
     * @throws CheckedServiceException
     * </pre>
     */
    List<CmsSchedule> getCmsScheduleList(CmsSchedule cmsSchedule) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：通过id获取CmsSchedule对象
     * 使用示范：
     *
     * @param id 主键ID
     * @return CmsSchedule 对象
     * @throws CheckedServiceException
     * </pre>
     */
    CmsSchedule getCmsScheduleById(Long id) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：通过ContentId获取CmsSchedule对象
     * 使用示范：
     *
     * @param id cms_Content_id 主键ID
     * @return CmsSchedule 对象
     * @throws CheckedServiceException
     * </pre>
     */
    CmsSchedule getCmsScheduleByContentId(Long contentId) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：获取总条数
     * 使用示范：
     *
     * @return 总条数
     * </pre>
     */
    Integer getCmsScheduleCount() throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：按主键Id删除
     * 使用示范：
     *
     * @param id 主键ID
     * @return 影响行数
     * @throws CheckedServiceException
     * </pre>
     */
    Integer deleteCmsScheduleById(Long id) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：按contentId删除
     * 使用示范：
     *
     * @param contentId Cms_Content_id 
     * @return 影响行数
     * @throws CheckedServiceException
     * </pre>
     */
    Integer deleteCmsScheduleByContentId(Long contentId) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：批量删除
     * 使用示范：
     *
     * @param ids 主键id 集合
     * @return 影响行数
     * @throws CheckedServiceException
     * </pre>
     */
    Integer deleteCmsScheduleByIds(List<Long> ids) throws CheckedServiceException;
}
