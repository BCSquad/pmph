package com.bc.pmpheep.back.controller.product;

import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.po.Product;
import com.bc.pmpheep.back.service.ProductService;
import com.bc.pmpheep.back.util.CookiesUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.SessionUtil;
import com.bc.pmpheep.back.vo.ProductVO;
import com.bc.pmpheep.controller.bean.ResponseBean;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.PathParam;

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
    @RequestMapping("/save/{publish}")
    @ResponseBody
    public ResponseBean save(HttpServletRequest request, ProductVO productVO, @PathVariable("publish")String publish){
        Boolean is_publish=!"noPub".equals(publish);
        productVO.setIs_published(is_publish);
        String sessionId = CookiesUtil.getSessionId(request);
        ResponseBean responseBean = productService.saveProductVO(productVO,sessionId);
        return responseBean;
    }


}
