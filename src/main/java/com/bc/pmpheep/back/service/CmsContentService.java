package com.bc.pmpheep.back.service;

import java.util.List;

import com.bc.pmpheep.back.po.CmsContent;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 
 * <pre>
 * 功能描述：CmsContentService 接口
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
public interface CmsContentService {
    /**
     * 
     * <pre>
     * 功能描述：新增
     * 使用示范：
     *
     * @param cmsContent  CmsContent对象
     * @return CmsContent对象
     * @throws CheckedServiceException
     * </pre>
     */
    CmsContent addCmsContent(CmsContent cmsContent) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：修改CmsContent
     * 使用示范：
     *
     * @param cmsContent 
     * @return  cmsContent CmsContent对象
     * @throws CheckedServiceException
     * </pre>
     */
    Integer updateCmsContent(CmsContent cmsContent) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：查询CmsContent列表(全部)
     * 使用示范：
     *
     * @param cmsContent 
     * @return CmsContent集合对象
     * @throws CheckedServiceException
     * </pre>
     */
    List<CmsContent> getCmsContentList(CmsContent cmsContent) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：通过id获取CmsContent对象
     * 使用示范：
     *
     * @param id 主键ID
     * @return CmsContent 对象
     * @throws CheckedServiceException
     * </pre>
     */
    CmsContent getCmsContentById(Long id) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：获取总条数
     * 使用示范：
     *
     * @return 总条数
     * @throws CheckedServiceException
     * </pre>
     */
    Integer getCmsContentCount() throws CheckedServiceException;

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
    Integer deleteCmsContentById(Long id) throws CheckedServiceException;

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
    Integer deleteCmsContentByIds(List<Long> ids) throws CheckedServiceException;
}
