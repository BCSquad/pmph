package com.bc.pmpheep.back.service;

import java.util.List;
import java.util.Map;

import com.bc.pmpheep.back.po.CmsCategory;
import com.bc.pmpheep.back.po.CmsCategoryRole;
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
     * 功能描述：新增CmsCategory
     * 使用示范：
     *
     * @param cmsCategory  CmsCategory对象
     * @param permissionId  操作权限id集合
     * @param authRoleId  审核角色id集合
     * @return 影响行数 
     * @throws CheckedServiceException
     * </pre>
     */
    Integer addCmsCategory(CmsCategory cmsCategory, List<Long> permissionId, List<Long> authRoleId)
    throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：新增CmsCategory
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
     * 功能描述：新增CmsCategoryRole
     * 使用示范：
     *
     * @param cmsCategoryRole  CmsCategoryRole对象
     * @return 影响行数
     * @throws CheckedServiceException
     * </pre>
     */
    Integer addCmsCategoryRole(CmsCategoryRole cmsCategoryRole) throws CheckedServiceException;

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
     * 功能描述：修改CmsCategory，CmsCategoryRole
     * 使用示范：
     * @param cmsCategory 对象
     * @param permissionId  操作权限id集合
     * @param authRoleId  审核角色id集合
     * @return 影响行数 
     * @throws CheckedServiceException
     * </pre>
     */
    Integer updateCmsCategoryAndCategoryRole(CmsCategory cmsCategory, List<Long> permissionId,
    List<Long> authRoleId) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：修改CmsCategoryRole
     * 使用示范：
     *
     * @param cmsCategoryRole  CmsCategoryRole对象
     * @return 影响行数
     * @throws CheckedServiceException
     * </pre>
     */
    Integer updateCmsCategoryRole(CmsCategoryRole cmsCategoryRole) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：获取所有父级节点
     * 使用示范：
     * @param categoryName 栏目名称
     * @return CmsCategory 集合对象
     * @throws CheckedServiceException
     * </pre>
     */
    List<CmsCategory> getListAllParentMenu(String categoryName) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：按父节点ID查询对应子节点
     * 使用示范：
     * @param categoryName 栏目名称
     * @param parentId 父节点ID
     * @return CmsCategory 集合对象
     * @throws CheckedServiceException
     * </pre>
     */
    List<CmsCategory> getListChildMenuByParentId(Long parentId, String categoryName)
    throws CheckedServiceException;

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

    Map<String, Object> getCmsCategoryDetail(Long id) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：获取总条数
     * 使用示范：
     *@param categoryId CmsCategory 主键ID
     * @return 总条数
     * </pre>
     */
    Integer getCmsCategoryCount(Long categoryId) throws CheckedServiceException;

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
    List<CmsCategoryRole> getCmsCategoryRoleByCategoryId(Long categoryId)
    throws CheckedServiceException;

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
     * 功能描述：按主键Id集合批量删除
     * 使用示范：
     *
     * @param ids 主键id集合
     * @return 影响行数
     * @throws CheckedServiceException
     * </pre>
     */
    Integer batchDeleteCmsCategoryById(List<Long> ids) throws CheckedServiceException;

    /**
     * 
     * <pre>
     * 功能描述：按主键categoryId删除
     * 使用示范：
     *
     * @param categoryId 内容栏目主键ID
     * @param permissionType 权限
     * @return 影响行数
     * @throws CheckedServiceException
     * </pre>
     */
    Integer deleteCmsCategoryRoleByCategoryId(Long categoryId, Short permissionType)
    throws CheckedServiceException;

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
