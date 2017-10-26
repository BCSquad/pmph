package com.bc.pmpheep.back.service;

import java.util.List;

import com.bc.pmpheep.back.po.CmsCategory;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 
 * <pre>
 * 功能描述：CmsCategoryService 接口
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
public interface CmsCategoryService {

    /**
     * 
     * <pre>
     * 功能描述：新增
     * 使用示范：
     *
     * @param cmsCategory  CmsCategory对象
     * @return CmsCategory对象
     * @throws CheckedServiceException
     * </pre>
     */
    CmsCategory addCmsCategory(CmsCategory cmsCategory) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：修改
     * 使用示范：
     *
     * @param cmsCategory 
     * @return 影响行数
     * @throws CheckedServiceException
     * </pre>
     */
    Integer updateCmsCategory(CmsCategory cmsCategory) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：查询CmsCategory列表(全部)
     * 使用示范：
     *
     * @param cmsCategory 
     * @return CmsCategory集合对象
     * @throws CheckedServiceException
     * </pre>
     */
    public List<CmsCategory> getCmsCategoryList(CmsCategory cmsCategory)
    throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：通过id获取数据
     * 使用示范：
     *
     * @param id 主键ID
     * @return CmsCategory 对象
     * @throws CheckedServiceException
     * </pre>
     */
    CmsCategory getCmsCategoryById(Long id) throws CheckedServiceException;

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
    Integer getCmsCategoryCount() throws CheckedServiceException;

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
    Integer deleteCmsCategoryById(Long id) throws CheckedServiceException;

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
    Integer deleteCmsCategoryByIds(List<Long> ids) throws CheckedServiceException;
}
