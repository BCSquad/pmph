package com.bc.pmpheep.back.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.CmsCategory;
import com.bc.pmpheep.back.po.CmsCategoryRole;
import com.bc.pmpheep.back.vo.CmsCategoryRoleVO;

/**
 * 
 * <pre>
 * 功能描述：CmsCategory 实体类数据访问层接口
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
public interface CmsCategoryDao {
    /**
     * 
     * <pre>
     * 功能描述：新增CmsCategory
     * 使用示范：
     *
     * @param cmsCategory  CmsCategory对象
     * @return 影响行数
     * </pre>
     */
    Integer addCmsCategory(CmsCategory cmsCategory);

    /**
     * 
     * <pre>
     * 功能描述：新增CmsCategoryRole
     * 使用示范：
     *
     * @param cmsCategoryRole  CmsCategoryRole对象
     * @return 影响行数
     * </pre>
     */
    Integer addCmsCategoryRole(CmsCategoryRole cmsCategoryRole);

    /**
     * 
     * <pre>
     * 功能描述：修改CmsCategory
     * 使用示范：
     *
     * @param cmsCategory CmsCategory对象
     * @return 影响行数
     * </pre>
     */
    Integer updateCmsCategory(CmsCategory cmsCategory);

    /**
     * 
     * <pre>
     * 功能描述：修改CmsCategoryRole
     * 使用示范：
     *
     * @param cmsCategoryRole  CmsCategoryRole对象
     * @return 影响行数
     * </pre>
     */
    Integer updateCmsCategoryRole(CmsCategoryRole cmsCategoryRole);

    /**
     * 
     * <pre>
     * 功能描述：获取所有父级节点
     * 使用示范：
     *
     * @param categoryName 栏目名称
     * @return CmsCategory 集合对象
     * </pre>
     */
    List<CmsCategory> getListAllParentMenu(@Param("categoryName") String categoryName);

    /**
     * 
     * <pre>
     * 功能描述：按父节点ID查询对应子节点
     * 使用示范：
     * @param categoryName 栏目名称
     * @param parentId 父节点ID
     * @return CmsCategory 集合对象
     * </pre>
     */
    List<CmsCategory> getListChildMenuByParentId(@Param("parentId") Long parentId,
    @Param("categoryName") String categoryName);

    /**
     * 
     * <pre>
     * 功能描述：查询按categoryName查询CmsCategory列表
     * 使用示范：
     *
     * @param categoryName 栏目名称
     * @return CmsCategory集合对象
     * </pre>
     */
    List<CmsCategory> getCmsCategoryListByCategoryName(@Param("categoryName") String categoryName);

    /**
     * 
     * <pre>
     * 功能描述：通过id获取CmsCategory对象
     * 使用示范：
     *
     * @param id 主键ID
     * @return CmsCategory 对象
     * </pre>
     */
    CmsCategory getCmsCategoryById(Long id);

    /**
     * 
     * <pre>
     * 功能描述：通过categoryId查询权限
     * 使用示范：
     *
     * @param categoryId CmsCategory主键ID
     * @return  CmsCategoryRole 集合
     * </pre>
     */
    List<CmsCategoryRoleVO> getCmsCategoryRoleByCategoryId(Long categoryId);

    /**
     * 
     * <pre>
     * 功能描述：通过roleIds查询权限
     * 使用示范：
     *
     * @param roleIds 角色id集合
     * @return CmsCategoryRole 集合对象
     * </pre>
     */
    List<CmsCategoryRoleVO> getCmsCategoryRoleByRoleIds(List<Long> roleIds);

    /**
     * 
     * <pre>
     * 功能描述：获取总条数
     * 使用示范：
     *@param categoryId CmsCategory 主键ID
     * @return 总条数
     * </pre>
     */
    Integer getCmsCategoryCount(Long categoryId);

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
    Integer deleteCmsCategoryById(Long id);

    /**
     * 
     * <pre>
     * 功能描述：按主键categoryId删除
     * 使用示范：
     *
     * @param cmsCategoryRole CmsCategoryRole 对象
     * @return 影响行数
     * </pre>
     */
    Integer deleteCmsCategoryRoleByCategoryId(CmsCategoryRole cmsCategoryRole);

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
    Integer deleteCmsCategoryByIds(List<Long> ids);

}
