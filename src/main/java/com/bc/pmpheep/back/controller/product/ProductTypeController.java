package com.bc.pmpheep.back.controller.product;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.service.ProductTypeService;
import com.bc.pmpheep.back.util.CookiesUtil;
import com.bc.pmpheep.back.vo.MaterialListVO;
import com.bc.pmpheep.back.vo.ProductType;
import com.bc.pmpheep.controller.bean.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 临床决策产品分类 控制层
 */
@Controller
@RequestMapping("productType")
public class ProductTypeController {

    @Autowired
    ProductTypeService productTypeService;

    /**
     * 分页查询 产品分类
     * @param request
     * @param pathType subject：学科分类；content：内容分类
     * @param type_name 分类名称的模糊查询条件
     * @param pageSize
     * @param pageNumber
     * @return
     */
    @RequestMapping("/{type}/list")
    @ResponseBody
    public ResponseBean getSubjectList(HttpServletRequest request
                                    , @PathVariable("type")String pathType
                                    , String type_name
                                    , Integer pageSize
                                    , Integer pageNumber){
        ResponseBean responseBean = new ResponseBean();
        String sessionId = CookiesUtil.getSessionId(request);
        PageParameter<ProductType> pageParameter = new PageParameter<>(pageNumber, pageSize);
        ProductType productType = new ProductType();
        pageParameter.setParameter(productType);
        productType.setType_name(type_name!=null?type_name.trim():null);

        if ("subject".equals(pathType)){
            productType.setTypeType(1);
        }else if("content".equals(pathType)){
            productType.setTypeType(2);
        }else{
            responseBean.setCode(ResponseBean.WRONG_REQ_PARA);
            return responseBean;
        }

        PageResult pageResult = productTypeService.getTypeList(pageParameter,sessionId);

        responseBean.setData(pageResult);

        return responseBean;
    }

    @RequestMapping("/{type}/delete")
    @ResponseBody
    public ResponseBean deleteType(HttpServletRequest request
            , @PathVariable("type")String pathType
            , @RequestParam(value = "id",required = true)Long id){

        ResponseBean responseBean = new ResponseBean();
        String sessionId = CookiesUtil.getSessionId(request);
        ProductType productType = new ProductType();
        productType.setId(id);

        if ("subject".equals(pathType)){
            productType.setTypeType(1);
        }else if("content".equals(pathType)){
            productType.setTypeType(2);
        }else{
            responseBean.setCode(ResponseBean.WRONG_REQ_PARA);
            responseBean.setMsg("未知的分类: "+pathType);
            return responseBean;
        }

        responseBean = productTypeService.deleteTypeById(productType);

        return responseBean;
    }








}
