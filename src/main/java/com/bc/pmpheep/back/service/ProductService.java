package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.Material;
import com.bc.pmpheep.back.po.Product;
import com.bc.pmpheep.back.po.ProductHistorylVO;
import com.bc.pmpheep.back.po.ProductOrg;
import com.bc.pmpheep.back.vo.MateriaHistorylVO;
import com.bc.pmpheep.back.vo.OrgExclVO;
import com.bc.pmpheep.back.vo.ProductVO;
import com.bc.pmpheep.controller.bean.ResponseBean;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public interface ProductService {

    /**
     * 查询或创建此类型的临床决策产品 校验登录
     * @param product_type 类型 1.人卫临床助手 2.人卫用药助手 3.人卫中医助手
     * @param sessionId
     * @return
     */
    public ProductVO getProductByType(Long product_type,Long product_id, String sessionId);

    /**
     * 根据id 获取产品数据
     * @param id
     * @return
     */
    public ProductVO getProductById(Long id);

    /**
     * 查询或创建此类型的临床决策产品 不校验登录
     * @param product_type 类型 1.人卫临床助手 2.人卫用药助手 3.人卫中医助手
     * @return
     */
    ProductVO getProductByType(Long product_type,Long id);

    /**
     * 更新附件下载次数
     * @param id
     * @return
     */
    int updateProductAttachmenDownLoadCountsByAttachment(String id);


    /**
     * 插入或者更新临床决策产品及其从属表
     * @param productVO
     * @return
     */
    ResponseBean saveProductVO(ProductVO productVO, String sessionId, HttpServletRequest request) throws IOException;


    ResponseBean list(PageParameter<ProductVO> pageParameter);

    /**
     * 根据productId导出已发布的学校-
     * @param productId
     * @return
     */
    List<OrgExclVO> getOutPutExclOrgByProduct(Long productId);

    /**
     * 临床发布通知
     * @param productId
     * @param orgIds
     * @param sessionId
     * @return
     */
    Integer noticePublished(Long productId, List<Long> orgIds,/*Boolean is_active,*/ String sessionId) throws IOException;

    /**
     * 发布临床-机构
     * @param productOrgList
     * @return
     */
    Integer addProductOrgs(List<ProductOrg> productOrgList);

    /**
     * 根据productId 获取当前已发布的机构
     * @param productId
     * @return
     */
    List<Long> getListProductOrgByProductId(Long productId);

    /**
     * 更新发布状态
     * @param productVO
     * @param sessionId
     * @return
     */
    Integer updateProduct(ProductVO productVO, String sessionId);

    /**
     *
     * @param pageParameter
     * @param sessionId
     * @return
     */
    PageResult<ProductHistorylVO> listProductHistory(PageParameter<ProductHistorylVO> pageParameter, String sessionId);

    /**
     *
     * <pre>
     * 功能描述：获取临床集合
     * 使用示范：
     *
     * &#64;param materialName 教材名称
     * &#64;return
     * </pre>
     */
    List<Product> getListProduct(String productName);


    /**
     * 根据productId 获取产品的名字
     * @param materialId
     * @return
     */
    String getProductNameById(Long materialId);
}
