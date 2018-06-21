package com.bc.pmpheep.back.controller.material;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.po.MaterialType;
import com.bc.pmpheep.back.service.MaterialTypeService;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.controller.bean.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.PathParam;


/**
 * @author LiuDi
 * @CreateDate 2018年06月11日 下午4:10:32
 * 教材分类控制层
 */
@RequestMapping(value = "/materialType")
@Controller
public class MaterialTypeController {

    @Autowired
    MaterialTypeService materialTypeService;

    // 当前业务类型
    private static final String BUSSINESS_TYPE = "教材分类";

    /**
     * 在某分类下新增分类
     * @param request
     *  parentId 父级分类id
     * typeName 分类名称
     * note 分类备注
     * @return
     */
    @RequestMapping(value = "addbelow",method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean addMarterialType(HttpServletRequest request
            ,@ModelAttribute MaterialType type){
        type.setNote(StringUtil.isEmpty(type.getNote())?type.getTypeName():type.getNote());
        type.setSort(type.getSort()!=null?type.getSort():999);

        MaterialType ParentType = materialTypeService.getMaterialTypeById(type.getParentId());
        String path = ParentType.getPath() + "-" + ParentType.getId();
        type.setPath(path);

        MaterialType resultType = materialTypeService.addMaterialType(type);

        return new  ResponseBean(resultType);
    }

    @RequestMapping(value = "update",method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean updateMaterialType(HttpServletRequest request
            ,@ModelAttribute MaterialType type){
        Integer count = materialTypeService.updateMaterialType(type);
        return new  ResponseBean(type);
    }

    /**
     * 根据父级id获取下一级所有教材分类 parentId为null 获取整个教材分类树（根节点为0）
     * @param parentId
     */
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "根据父级id获取下一级所有教材分类")
    @ResponseBody
    public ResponseBean tree(@RequestParam(value = "parentId",defaultValue = "0") Long parentId) {
        return new ResponseBean(materialTypeService.lazyListMaterialType(parentId));
    }

    /**
     * 删除该分类以及下属分类
     *
     * @author Mryang
     * @createDate 2017年9月26日 下午3:38:14
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "删除分类")
    @ResponseBody
    public ResponseBean delete(Long id) {
        return new ResponseBean(materialTypeService.deleteMaterialTypeById(id));
    }



}
