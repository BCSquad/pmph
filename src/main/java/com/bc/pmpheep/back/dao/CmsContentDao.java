package com.bc.pmpheep.back.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.CmsContent;
import com.bc.pmpheep.back.vo.CmsContentVO;
import com.bc.pmpheep.service.exception.CheckedServiceException;

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
	 * 功能描述：定时任务批量更新
	 * 使用示范：
	 *
	 * @param csmContents CmsContent对象集合
	 * @return
	 * </pre>
     */
    Integer updateCmsContentByIds(List<CmsContent> csmContents);

    /**
     * 
     * <pre>
	 * 功能描述： 内容发布
	 * 使用示范：
	 * 
	 * @param id 主键ID
	 * @return 影响行数
	 * </pre>
     */
    Integer publishCmsContentById(Long id);

    /**
     * 
     * <pre>
	 * 功能描述：内容修改 按ID
	 * 使用示范：
	 *
	 * @param id 主键ID
	 * @return 影响行数
	 * </pre>
     */
    Integer updateCmsContentById(Long id);

    /**
     * 
     * <pre>
	 * 功能描述： 内容隐藏
	 * 使用示范：
	 *
	 * @param id 主键ID
	 * @return 影响行数
	 * </pre>
     */
    Integer hideCmsContentById(Long id);

    /**
     * 
     * <pre>
	 * 功能描述： 社外内容审核操作(通过/拒绝)
	 * 使用示范：
	 *
	 * @param id 主键ID
	 * @param authStatus 审核状态
	 * @return 影响行数
	 * </pre>
     */
    Integer checkContentById(CmsContent cmsContent);

    /**
     * 
     * <pre>
	 * 功能描述：获取CmsContent列表（同时查询分页数据和总条数）
	 * 使用示范：
	 *
	 * @param pageParameter
	 * @return List<CmsContentVO>
	 * </pre>
     */
    List<CmsContentVO> listCmsContent(PageParameter<CmsContentVO> pageParameter);

    /**
     * 
     * <pre>
     * 功能描述：获取评论列表（同时查询分页数据和总条数）
     * 使用示范：
     *
     * @param pageParameter
     * @return List<CmsContentVO>
     * </pre>
     */
    List<CmsContentVO> listCmsComment(PageParameter<CmsContentVO> pageParameter);

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
	 * 功能描述：通过materialId获取CmsContent对象
	 * 使用示范：
	 *
	 * @param materialId 教材ID
	 * @return CmsContent对象
	 * </pre>
     */
    CmsContent getCmsContentByMaterialId(Long materialId);

    /**
     * 
     * <pre>
	 * 功能描述：获取总条数
	 * 使用示范：
	  *@param categoryId CmsCategory 主键ID
	 * @return 总条数
	 * </pre>
     */
    Integer getCmsContentCount(@Param("categoryId") Long categoryId);

    /**
     * 
     * <pre>
	 * 功能描述：按主键Id删除(逻辑删除)
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

    /**
     * 
     * <pre>
	 * 功能描述：根据教材id逻辑删除
	 * 使用示范：
	 *
	 * @param id 主键ID
	 * @return 影响行数
	 * @throws CheckedServiceException
	 * </pre>
     */
    Integer updateCmsContentByMaterialId(Long MaterialId);
}
