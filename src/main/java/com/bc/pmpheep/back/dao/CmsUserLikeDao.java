package com.bc.pmpheep.back.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.CmsUserLike;

/**
 * 
 * <pre>
 * 功能描述：CmsUserLike 实体类数据访问层接口
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
public interface CmsUserLikeDao {
    /**
     * 
     * <pre>
     * 功能描述：新增
     * 使用示范：
     *
     * @param cmsUserLike  CmsUserLike对象
     * @return 影响行数
     * </pre>
     */
    Integer addCmsUserLike(CmsUserLike cmsUserLike);

    /**
     * 
     * <pre>
     * 功能描述：修改CmsUserLike
     * 使用示范：
     *
     * @param cmsUserLike 
     * @return 影响行数
     * </pre>
     */
    Integer updateCmsUserLike(CmsUserLike cmsUserLike);

    /**
     * 
     * <pre>
     * 功能描述：查询CmsUserLike列表(全部)
     * 使用示范：
     *
     * @param cmsUserLike 
     * @return CmsUserLike集合对象
     * </pre>
     */
    List<CmsUserLike> getCmsUserLikeList(CmsUserLike cmsUserLike);

    /**
     * 
     * <pre>
     * 功能描述：通过id获取CmsUserLike对象
     * 使用示范：
     *
     * @param id 主键ID
     * @return CmsUserLike 对象
     * </pre>
     */
    CmsUserLike getCmsUserLikeById(Long id);

    /**
     * 
     * <pre>
     * 功能描述：获取总条数
     * 使用示范：
     *
     * @return 总条数
     * </pre>
     */
    Integer getCmsUserLikeCount();

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
    Integer deleteCmsUserLikeById(Long id);

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
    Integer deleteCmsUserLikeByIds(List<Long> ids);
}
