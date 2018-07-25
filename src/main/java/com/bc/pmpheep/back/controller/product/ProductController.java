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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

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

    @RequestMapping("/save")
    @ResponseBody
    public ResponseBean save(HttpServletRequest request,ProductVO productVO){
        ResponseBean responseBean = new ResponseBean();
        

        return responseBean;
    }

}
