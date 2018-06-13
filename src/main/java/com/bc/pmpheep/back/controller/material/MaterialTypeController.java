package com.bc.pmpheep.back.controller.material;

import com.bc.pmpheep.back.po.MaterialType;
import com.bc.pmpheep.back.service.MaterialTypeService;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.controller.bean.ResponseBean;
import com.mchange.lang.LongUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.PathParam;


/**
 * @author LiuDi
 * @CreateDate 2018年06月11日 下午4:10:32
 * 教材分类控制层
 */
@RequestMapping(value = "/materialType")
public class MaterialTypeController {

    @Autowired
    MaterialTypeService materialTypeService;

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
}
