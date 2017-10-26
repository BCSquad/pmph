package com.bc.pmpheep.back.service;

import java.util.List;

import com.bc.pmpheep.back.po.CmsContentCategory;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 
 * <pre>
 * 功能描述：CmsContentCategoryService 接口
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
public interface CmsContentCategoryService {
    /**
     * 
     * <pre>
     * 功能描述：新增
     * 使用示范：
     *
     * @param cmsContentCategory  CmsContentCategory对象
     * @return CmsContentCategory对象
     * @throws CheckedServiceException
     * </pre>
     */
    CmsContentCategory addCmsContentCategory(CmsContentCategory cmsContentCategory)
    throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：修改CmsContentCategory
     * 使用示范：
     *
     * @param cmsContentCategory 
     * @return 影响行数
     * @throws CheckedServiceException
     * </pre>
     */
    Integer updateCmsContentCategory(CmsContentCategory cmsContentCategory)
    throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：查询CmsContentCategory列表(全部)
     * 使用示范：
     *
     * @param cmsContentCategory 
     * @return CmsContentCategory集合对象
     * @throws CheckedServiceException
     * </pre>
     */
    List<CmsContentCategory> getCmsContentCategoryList(CmsContentCategory cmsContentCategory)
    throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：通过id获取CmsContentCategory对象
     * 使用示范：
     *
     * @param id 主键ID
     * @return CmsContentCategory 对象
     * @throws CheckedServiceException
     * </pre>
     */
    CmsContentCategory getCmsContentCategoryById(Long id) throws CheckedServiceException;

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
    Integer getCmsContentCategoryCount() throws CheckedServiceException;

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
    Integer deleteCmsContentCategoryById(Long id) throws CheckedServiceException;

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
    Integer deleteCmsContentCategoryByIds(List<Long> ids) throws CheckedServiceException;

}
