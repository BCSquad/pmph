package com.bc.pmpheep.back.controller.product;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.po.Product;
import com.bc.pmpheep.back.po.ProductExtension;
import com.bc.pmpheep.back.po.ProductHistorylVO;
import com.bc.pmpheep.back.service.ProductService;
import com.bc.pmpheep.back.util.CookiesUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.SessionUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.ExpertationVO;
import com.bc.pmpheep.back.vo.MateriaHistorylVO;
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
import java.util.List;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping(value = "/product")
public class ProductController {

    // 当前业务类型
    private static final String BUSSINESS_TYPE = "临床决策通知";

    @Autowired
    ProductService productService;

    /**
     * 进入页面后初始化，读取相应数据
     * @param request
     * @param product_type
     * @return
     */
    @RequestMapping("/init")
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "查询详情")
    @ResponseBody
    public ResponseBean productInit(HttpServletRequest request
            , @RequestParam(value = "type",required = true)Long product_type
            , @RequestParam(value = "id" ,required = false)Long id
    ){
        ResponseBean responseBean = new ResponseBean();
        String sessionId = CookiesUtil.getSessionId(request);

        ProductVO product = productService.getProductByType(product_type,id,sessionId);

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
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "保存/发布")
    @ResponseBody
    public ResponseBean save(HttpServletRequest request, @RequestBody ProductVO productVO, @PathVariable("publish")String publish) throws IOException {
        Boolean is_publish=!"noPub".equals(publish);
        //productVO.setIs_published(is_publish);
        String sessionId = CookiesUtil.getSessionId(request);
      /*  String auditorListS = request.getParameter("auditorList");
        String ProductExtensionListS = request.getParameter("ProductExtensionList");*/
       /* Map<String,String[]> map = request.getParameterMap();
        String[] ProductExtensionListS = map.get("ProductExtensionList");*/
        //List<ProductExtension> ProductExtensionList = (List<ProductExtension>) JSON.parseObject (ProductExtensionListS);
        //ResponseBean responseBean = productService.saveProductVO(productVO,sessionId);
        ResponseBean responseBean = productService.saveProductVO(productVO,sessionId,request);
        return responseBean;
    }

    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "查询列表")
    @ResponseBody
    public ResponseBean list(HttpServletRequest request
                             ,String product_name
                             ,Boolean is_published
                             ,Long product_type
                             ,String product_type_list_str
                             ,Boolean is_active
            ,@RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize
            ,@RequestParam(value = "pageNumber",defaultValue = "1") Integer pageNumber){

        ProductVO productVO = new ProductVO();
        productVO.setIs_active(is_active);
        productVO.setProduct_name(product_name);
        productVO.setIs_published(is_published);
        productVO.setProduct_type(product_type);
        productVO.setProduct_type_list_str(StringUtil.notEmpty(product_type_list_str)?product_type_list_str:null);
        PageParameter<ProductVO> pageParameter = new PageParameter<ProductVO>(pageNumber, pageSize);
        pageParameter.setParameter(productVO);
        ResponseBean responseBean = productService.list(pageParameter);

        return responseBean;
    }


    /**
     * 功能描述：发布临床通知
     * 使用示范：
     * @param request
     * @param productId
     * @param orgIds
     * @return
     */
    @RequestMapping(value = "/published",method = RequestMethod.POST)
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "产品通知发布")
    @ResponseBody
    public ResponseBean published(HttpServletRequest request,
                                  @RequestParam("productId") Long productId, @RequestParam("orgIds") List<Long> orgIds/*,
                                  @RequestParam("is_active") Boolean is_active*/){

        String sessionId = CookiesUtil.getSessionId(request);
        try {
            return new ResponseBean(productService.noticePublished(productId,
                    orgIds,/*is_active,*/
                    sessionId));
        } catch (Exception e) {
            return new ResponseBean(e);
        }
    }

    /**
     *
     * <pre>
     * 功能描述：查询历史临床通知列表
     * 使用示范：
     *
     * @param pageNumber 当前页数
     * @param pageSize 当前页条数
     * @param request
     * @return
     * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "查询历史通知列表")
    @RequestMapping(value = "/history", method = RequestMethod.GET)
    public ResponseBean history(
            @RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize,
            HttpServletRequest request) {
        PageParameter<ProductHistorylVO> pageParameter = new PageParameter<>(pageNumber, pageSize);
        ProductHistorylVO productHistorylVO = new ProductHistorylVO();
        pageParameter.setParameter(productHistorylVO);
        String sessionId = CookiesUtil.getSessionId(request);
        return new ResponseBean(productService.listProductHistory(pageParameter, sessionId));
    }

    


}
