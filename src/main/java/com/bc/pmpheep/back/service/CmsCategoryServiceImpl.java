package com.bc.pmpheep.back.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.CmsCategoryDao;
import com.bc.pmpheep.back.po.CmsCategory;
import com.bc.pmpheep.back.po.CmsCategoryRole;
import com.bc.pmpheep.back.util.CollectionUtil;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.CmsCategoryRoleVO;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 
 * <pre>
 * 功能描述：CmsCategoryService 接口实现
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
@Service
public class CmsCategoryServiceImpl implements CmsCategoryService {
    @Autowired
    CmsCategoryDao categoryDao;

    @Override
    public Integer addCmsCategory(CmsCategory cmsCategory, List<Long> permissionId,
    List<Long> authRoleId) throws CheckedServiceException {
        Long cmsCategoryId = this.addCmsCategory(cmsCategory).getId();
        if (ObjectUtil.isNull(cmsCategoryId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.PO_ADD_FAILED, "对象保存失败");
        }
        // if (CollectionUtil.isEmpty(authRoleId)) {
        // throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
        // CheckedExceptionResult.NULL_PARAM, "审核角色ID参数为空");
        // }
        // if (CollectionUtil.isEmpty(permissionId)) {
        // throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
        // CheckedExceptionResult.NULL_PARAM, "操作权限ID参数为空");
        // }
        // 操作权限id集合
        for (Long roleId : permissionId) {
            categoryDao.addCmsCategoryRole(new CmsCategoryRole(cmsCategoryId, roleId,
                                                               Const.CMS_CATEGORY_PERMISSSION_1));
        }
        // 审核角色id集合
        for (Long roleId : authRoleId) {
            categoryDao.addCmsCategoryRole(new CmsCategoryRole(cmsCategoryId, roleId,
                                                               Const.CMS_CATEGORY_PERMISSSION_2));
        }

        return 1;
    }

    @Override
    public CmsCategory addCmsCategory(CmsCategory cmsCategory) throws CheckedServiceException {
        if (ObjectUtil.isNull(cmsCategory)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");

        }
        if (ObjectUtil.isNull(cmsCategory.getParentId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "上级类别id参数为空");

        }
        if (StringUtil.isEmpty(cmsCategory.getPath())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "根节点路径参数为空");
        }
        if (StringUtil.isEmpty(cmsCategory.getCategoryName())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "类别名称参数为空");
        }
        categoryDao.addCmsCategory(cmsCategory);
        return cmsCategory;
    }

    @Override
    public Integer addCmsCategoryRole(CmsCategoryRole cmsCategoryRole)
    throws CheckedServiceException {
        if (ObjectUtil.isNull(cmsCategoryRole)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        if (ObjectUtil.isNull(cmsCategoryRole.getCategoryId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        if (ObjectUtil.isNull(cmsCategoryRole.getRoleId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        if (ObjectUtil.isNull(cmsCategoryRole.getPermissionType())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        return categoryDao.addCmsCategoryRole(cmsCategoryRole);
    }

    @Override
    public Integer updateCmsCategoryRole(CmsCategoryRole cmsCategoryRole)
    throws CheckedServiceException {
        if (ObjectUtil.isNull(cmsCategoryRole)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        if (ObjectUtil.isNull(cmsCategoryRole.getCategoryId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        if (ObjectUtil.isNull(cmsCategoryRole.getRoleId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        if (ObjectUtil.isNull(cmsCategoryRole.getPermissionType())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        return categoryDao.updateCmsCategoryRole(cmsCategoryRole);
    }

    @Override
    public Integer updateCmsCategory(CmsCategory cmsCategory) throws CheckedServiceException {
        if (ObjectUtil.isNull(cmsCategory)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");

        }
        return categoryDao.updateCmsCategory(cmsCategory);
    }

    @Override
    public Integer updateCmsCategoryAndCategoryRole(CmsCategory cmsCategory,
    List<Long> permissionId, List<Long> authRoleId) throws CheckedServiceException {
        if (ObjectUtil.isNull(cmsCategory)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "cmsCategory对象为空");
        }
        if (CollectionUtil.isNotEmpty(permissionId)) {
            categoryDao.deleteCmsCategoryRoleByCategoryId(new CmsCategoryRole(
                                                                              cmsCategory.getId(),
                                                                              Const.CMS_CATEGORY_PERMISSSION_1));
            // 操作权限id集合
            for (Long roleId : permissionId) {
                categoryDao.addCmsCategoryRole(new CmsCategoryRole(cmsCategory.getId(), roleId,
                                                                   Const.CMS_CATEGORY_PERMISSSION_1));
            }
        }
        if (CollectionUtil.isNotEmpty(authRoleId)) {
            categoryDao.deleteCmsCategoryRoleByCategoryId(new CmsCategoryRole(
                                                                              cmsCategory.getId(),
                                                                              Const.CMS_CATEGORY_PERMISSSION_2));
            // 审核角色id集合
            for (Long roleId : authRoleId) {
                categoryDao.addCmsCategoryRole(new CmsCategoryRole(cmsCategory.getId(), roleId,
                                                                   Const.CMS_CATEGORY_PERMISSSION_2));
            }
        }
        return categoryDao.updateCmsCategory(cmsCategory);
    }

    @Override
    public List<CmsCategory> getListAllParentMenu(String categoryName)
    throws CheckedServiceException {
        List<CmsCategory> cmsCategories;
        if (StringUtil.isEmpty(categoryName)) {
            cmsCategories = categoryDao.getListAllParentMenu(null);
        } else {
            cmsCategories = categoryDao.getCmsCategoryListByCategoryName(categoryName);
        }
        for (CmsCategory cmsCategory : cmsCategories) {
            List<CmsCategory> subList =
            this.getListChildMenuByParentId(cmsCategory.getId(), categoryName);
            if (CollectionUtil.isNotEmpty(subList)) {
                cmsCategory.setChildren(subList);
                for (CmsCategory category : subList) {
                    List<CmsCategory> subList1 =
                    this.getListChildMenuByParentId(category.getId(), null);
                    if (CollectionUtil.isNotEmpty(subList1)) {
                        category.setChildren(subList1);
                    }
                }
            }
        }
        return cmsCategories;
    }

    @Override
    public List<CmsCategory> getListChildMenuByParentId(Long parentId, String categoryName)
    throws CheckedServiceException {
        if (ObjectUtil.isNull(parentId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "parentId参数为空");
        }
        return categoryDao.getListChildMenuByParentId(parentId, categoryName);
    }

    @Override
    public List<CmsCategory> getCmsCategoryListByCategoryName(String categoryName)
    throws CheckedServiceException {
        if (StringUtil.isEmpty(categoryName)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");

        }
        return categoryDao.getCmsCategoryListByCategoryName(categoryName);
    }

    @Override
    public CmsCategory getCmsCategoryById(Long id) throws CheckedServiceException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");

        }
        return categoryDao.getCmsCategoryById(id);
    }

    @Override
    public Integer getCmsCategoryCount(Long categoryId) throws CheckedServiceException {
        return categoryDao.getCmsCategoryCount(categoryId);
    }

    @Override
    public Map<String, Object> getCmsCategoryDetail(Long id) throws CheckedServiceException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        CmsCategory cmsCategory = this.getCmsCategoryById(id);
        resultMap.put("cmsCategory", cmsCategory);
        // 按Category查询对应权限
        List<CmsCategoryRoleVO> cmsCategoryRoles = this.getCmsCategoryRoleByCategoryId(id);
        for (CmsCategoryRoleVO cmsCategoryRole : cmsCategoryRoles) {
            if (Const.CMS_CATEGORY_PERMISSSION_1 == cmsCategoryRole.getPermissionType()) {
                resultMap.put("permissionId", cmsCategoryRole.getRoleId());
            } else {
                resultMap.put("authRoleId", cmsCategoryRole.getRoleId());
            }
        }
        return resultMap;
    }

    @Override
    public List<CmsCategoryRoleVO> getCmsCategoryRoleByCategoryId(Long categoryId)
    throws CheckedServiceException {
        if (ObjectUtil.isNull(categoryId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "categoryId对象为空");
        }
        return categoryDao.getCmsCategoryRoleByCategoryId(categoryId);
    }

    @Override
    public List<CmsCategoryRoleVO> getCmsCategoryRoleByRoleIds(List<Long> roleIds)
    throws CheckedServiceException {
        if (CollectionUtil.isEmpty(roleIds)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        return categoryDao.getCmsCategoryRoleByRoleIds(roleIds);
    }

    @Override
    public Integer deleteCmsCategoryById(Long id) throws CheckedServiceException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        Integer count = this.getCmsCategoryCount(id);
        if (0 <= count) {
            this.deleteCmsCategoryRoleByCategoryId(id, null);
            return categoryDao.deleteCmsCategoryById(id);
        } else {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.ILLEGAL_PARAM,
                                              "该栏目下存在发布内容，不能删除！");
        }
    }

    @Override
    public Integer deleteCmsCategoryRoleByCategoryId(Long categoryId, Short permissionType)
    throws CheckedServiceException {
        if (ObjectUtil.isNull(categoryId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "categoryId参数为空");

        }
        if (ObjectUtil.isNull(permissionType)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM,
                                              "permissionType参数为空");

        }
        return categoryDao.deleteCmsCategoryRoleByCategoryId(new CmsCategoryRole(categoryId,
                                                                                 permissionType));
    }

    @Override
    public Integer deleteCmsCategoryByIds(List<Long> ids) throws CheckedServiceException {
        if (CollectionUtil.isEmpty(ids)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        return categoryDao.deleteCmsCategoryByIds(ids);
    }

    @Override
    public Integer batchDeleteCmsCategoryById(List<Long> ids) throws CheckedServiceException {
        if (CollectionUtil.isEmpty(ids)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        Integer count = 0;
        for (Long id : ids) {
            count = this.deleteCmsCategoryById(id);
        }
        return count;
    }
}
