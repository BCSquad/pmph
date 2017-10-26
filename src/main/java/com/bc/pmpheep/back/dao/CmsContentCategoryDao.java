package com.bc.pmpheep.back.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.CmsContentCategory;

/**
 * 
 * <pre>
 * 功能描述：CmsContentCategory 实体类数据访问层接口
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
@Repository
public interface CmsContentCategoryDao {
    /**
     * 
     * <pre>
     * 功能描述：新增
     * 使用示范：
     *
     * @param cmsContentCategory  CmsContentCategory对象
     * @return 影响行数
     * </pre>
     */
    Integer addCmsContentCategory(CmsContentCategory cmsContentCategory);

    /**
     * 
     * <pre>
     * 功能描述：修改CmsContentCategory
     * 使用示范：
     *
     * @param cmsContentCategory 
     * @return 影响行数
     * </pre>
     */
    Integer updateCmsContentCategory(CmsContentCategory cmsContentCategory);

    /**
     * 
     * <pre>
     * 功能描述：查询CmsContentCategory列表(全部)
     * 使用示范：
     *
     * @param cmsContentCategory 
     * @return CmsContentCategory集合对象
     * </pre>
     */
    List<CmsContentCategory> getCmsContentCategoryList(CmsContentCategory cmsContentCategory);

    /**
     * 
     * <pre>
     * 功能描述：通过id获取CmsContentCategory对象
     * 使用示范：
     *
     * @param id 主键ID
     * @return CmsContentCategory 对象
     * </pre>
     */
    CmsContentCategory getCmsContentCategoryById(Long id);

    /**
     * 
     * <pre>
     * 功能描述：获取总条数
     * 使用示范：
     * @return 总条数
     * </pre>
     */
    Integer getCmsContentCategoryCount();

    /**
     * 
     * <pre>
     * 功能描述：按主键Id删除
     * 使用示范：
     *
     * @param id 主键ID
     * @return 影响行数
     * </pre>
     */
    Integer deleteCmsContentCategoryById(Long id);

    /**
     * 
     * <pre>
     * 功能描述：批量删除
     * 使用示范：
     *
     * @param ids 主键id 集合
     * @return 影响行数
     * </pre>
     */
    Integer deleteCmsContentCategoryByIds(List<Long> ids);

}
