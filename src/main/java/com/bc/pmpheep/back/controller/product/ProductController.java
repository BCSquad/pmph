package com.bc.pmpheep.back.controller.product;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.po.Product;
import com.bc.pmpheep.back.service.ProductService;
import com.bc.pmpheep.back.util.CookiesUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.SessionUtil;
import com.bc.pmpheep.back.vo.ExpertationVO;
import com.bc.pmpheep.back.vo.ProductVO;
import com.bc.pmpheep.controller.bean.ResponseBean;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.PathParam;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping(value = "/product")
public class ProductController {

    @Autowired
    ProductService productService;

    /**
     * 进入页面后初始化，读取相应数据
     * @param request
     * @param product_type
     * @return
     */
    @RequestMapping("/init")
    @ResponseBody
    public ResponseBean productInit(HttpServletRequest request
            , @RequestParam(value = "type",required = true)Long product_type){
        ResponseBean responseBean = new ResponseBean();
        String sessionId = CookiesUtil.getSessionId(request);

        ProductVO product = productService.getProductByType(product_type,sessionId);

        responseBean.setData(product);

        return responseBean;
    }


    /**
     * 保存/发布
     * @param request
     * @param productVO
     * @param publish 仅当此处为 “noPub” ，才是暂存，其他或不填都是发布
     * @return
     */
    @RequestMapping(value="/save/{publish}",method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean save(HttpServletRequest request, @RequestBody ProductVO productVO, @PathVariable("publish")String publish) throws IOException {
        Boolean is_publish=!"noPub".equals(publish);
        productVO.setIs_published(is_publish);
        String sessionId = CookiesUtil.getSessionId(request);
        ResponseBean responseBean = productService.saveProductVO(productVO,sessionId,request);
        return responseBean;
    }

    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean list(HttpServletRequest request
                             ,String product_name
                             ,Boolean is_published
            ,@RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize
            ,@RequestParam(value = "pageNumber",defaultValue = "1") Integer pageNumber){

        ProductVO productVO = new ProductVO();
        productVO.setProduct_name(product_name);
        productVO.setIs_published(is_published);
        PageParameter<ProductVO> pageParameter = new PageParameter<ProductVO>(pageNumber, pageSize);
        pageParameter.setParameter(productVO);
        ResponseBean responseBean = productService.list(pageParameter);
        return responseBean;
    }



    


}
