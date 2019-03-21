package com.bc.pmpheep.back.controller.cms;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.po.CmsCategory;
import com.bc.pmpheep.back.service.CmsCategoryService;
import com.bc.pmpheep.controller.bean.ResponseBean;

/**
 * 
 * <pre>
 * 功能描述：栏目设置控制器
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017-11-2
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
@Controller
@RequestMapping(value = "/cms")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class CmsColumnSetController {
    @Autowired
    CmsCategoryService          cmsCategoryService;
    // 当前业务类型
    private static final String BUSSINESS_TYPE = "栏目类型";

    /**
     * 
     * <pre>
     * 功能描述：栏目设置 列表查询
     * 使用示范：
     *
     * @param categoryName 栏目名称
     * @return CmsCategory 对象集合
     * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "查询栏目类型列表")
    @RequestMapping(value = "/set", method = RequestMethod.GET)
    public ResponseBean set(@RequestParam("categoryName") String categoryName) {
        return new ResponseBean(cmsCategoryService.getListAllParentMenu(categoryName));
    }

    /**
     * 
     * <pre>
     * 功能描述：保存CmsCategory
     * 使用示范：
     *
     * @param cmsCategory  CmsCategory栏目对象
     * @param permissionId 操作权限id集合
     * @param authRoleId 审核角色id集合
     * @return 影响行数 
     * </pre>
     */
    @ResponseBody
    @RequestMapping(value = "/set/new", method = RequestMethod.POST)
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "保存栏目类型")
    public ResponseBean saveCmsCategory(CmsCategory cmsCategory,
    @RequestParam("permissionId") List<Long> permissionId,
    @RequestParam("authRoleId") List<Long> authRoleId) {
        return new ResponseBean(cmsCategoryService.addCmsCategory(cmsCategory,
                                                                  permissionId,
                                                                  authRoleId));
    }

    /**
     * 
     * <pre>
     * 功能描述：查找带回
     * 使用示范：
     *  
     * @param categoryId CmsCategory 主键ID
     * @return Map<String,Object>
     * </pre>
     */
    @ResponseBody
    @RequestMapping(value = "/set/detail", method = RequestMethod.GET)
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "查询栏目类型详情")
    public ResponseBean getCmsCategoryDetail(@RequestParam("categoryId") Long categoryId) {
        return new ResponseBean(cmsCategoryService.getCmsCategoryDetail(categoryId));
    }

    /**
     * 
     * <pre>
     * 功能描述：更新CmsCategory,CategoryRole
     * 使用示范：
     *  
     * @param categoryId CmsCategory 主键ID
     * @return 影响行数
     * </pre>
     */
    @ResponseBody
    @RequestMapping(value = "/set/update", method = RequestMethod.PUT)
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "更新栏目类型")
    public ResponseBean updateCmsCategoryAndCategoryRole(CmsCategory cmsCategory,
    @RequestParam("permissionId") List<Long> permissionId,
    @RequestParam("authRoleId") List<Long> authRoleId) {
        return new ResponseBean(cmsCategoryService.updateCmsCategoryAndCategoryRole(cmsCategory,
                                                                                    permissionId,
                                                                                    authRoleId));
    }

    /**
     * 
     * <pre>
     * 功能描述：删除CmsCategory
     * 使用示范：
     *  
     * @param id CmsCategory 主键ID
     * @return 影响行数
     * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "删除栏目类型")
    @RequestMapping(value = "/set/{id}/category", method = RequestMethod.DELETE)
    public ResponseBean deleteCmsCategoryById(@PathVariable("id") Long id) {
        return new ResponseBean(cmsCategoryService.deleteCmsCategoryById(id));
    }

    /**
     * 
     * <pre>
     * 功能描述：批量删除CmsCategory
     * 使用示范：
     *  
     * @param id CmsCategory 主键ID集合
     * @return 影响行数
     * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "批量删除栏目类型")
    @RequestMapping(value = "/set/category", method = RequestMethod.DELETE)
    public ResponseBean deleteCmsCategoryByIds(@RequestParam("ids") List<Long> ids) {
        return new ResponseBean(cmsCategoryService.deleteCmsCategoryByIds(ids));
    }
}
