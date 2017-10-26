package com.bc.pmpheep.back.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.CmsContent;

/**
 * 
 * <pre>
 * 功能描述：CmsContent 实体类数据访问层接口
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
public interface CmsContentDao {
    /**
     * 
     * <pre>
     * 功能描述：新增
     * 使用示范：
     *
     * @param cmsContent  CmsContent对象
     * @return 影响行数
     * </pre>
     */
    Integer addCmsContent(CmsContent cmsContent);

    /**
     * 
     * <pre>
     * 功能描述：修改CmsContent
     * 使用示范：
     *
     * @param cmsContent 
     * @return 影响行数
     * </pre>
     */
    Integer updateCmsContent(CmsContent cmsContent);

    /**
     * 
     * <pre>
     * 功能描述：查询CmsContent列表(全部)
     * 使用示范：
     *
     * @param cmsContent 
     * @return CmsContent集合对象
     * </pre>
     */
    List<CmsContent> getCmsContentList(CmsContent cmsContent);

    /**
     * 
     * <pre>
     * 功能描述：通过id获取CmsContent对象
     * 使用示范：
     *
     * @param id 主键ID
     * @return CmsContent 对象
     * </pre>
     */
    CmsContent getCmsContentById(Long id);

    /**
     * 
     * <pre>
     * 功能描述：获取总条数
     * 使用示范：
     *
     * @return 总条数
     * </pre>
     */
    Integer getCmsContentCount();

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
    Integer deleteCmsContentById(Long id);

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
    Integer deleteCmsContentByIds(List<Long> ids);
}
