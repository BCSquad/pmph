package com.bc.pmpheep.back.dao;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.*;
import com.bc.pmpheep.back.vo.OrgExclVO;
import com.bc.pmpheep.back.vo.ProductVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface ProductDao {

    /**
     * 根据类型查询一个临床决断产品 及其附表内容
     * @param product_type 类型
     * @param clinicalDecision 附件下载地址需要的前缀，后面拼接附件id
     * @return
     */
    ProductVO queryProductByProductType(@Param(value = "product_type") Long product_type,@Param(value = "product_id") Long product_id);

    /**
     * 根据主键查询
     * @param product_id
     * @return
     */
    ProductVO queryProductById(Long product_id);

    /**
     * 更新下载次数+1
     * @param id
     * @return
     */
    int updateProductAttachmenDownLoadCountsByAttachment(String id);

    /**
     * 新增或修改主表
     * insert on duplicate key update 主表product 其中 unique键除主键外 还有product_type
     * keyProperty设为id 若插入 则将生成的id传回到productVO的id中 便于附表关联之
     * @param productVO
     * @return
     */
    int saveProduct(ProductVO productVO);

    /**
     * 为了保留历史申报里的关联扩展项填写内容
     * 扩展项要伪删除，先按产品分类全部伪删除，再将传入id的insert on duplicate key update
     * @param productExtensionList
     * @return
     */
    int saveProductExtension(List<ProductExtension> productExtensionList);

    /**
     * 逻辑删除
     * 为了保留历史申报里的关联扩展项填写内容
     * 扩展项要伪删除，先按产品分类全部伪删除，再将传入id的insert on duplicate key update
     * @param product_id
     * @return
     */
    int deleteProductExtension(Long product_id);

    /**
     * 删除审核人的中间关联表，真删除
     * @param productId
     * @return
     */
    int deleteProductAuditorsByProductId(Long productId);

    /**
     * 插入审核人 ，若主键冲突（已有的）什么也不做
     * @param auditorList
     * @return
     */
    int saveProductAuditors(List<ProductAuditor> auditorList);

    /**
     *
     * @param productId
     * @return
     */
    int deleteProductAttachmentByProductId(Long productId);

    /**
     *  插入附件 ，若主键冲突（已有的）什么也不做
     * @param productAttachmentList
     * @return
     */
    int saveProductAttachments(List<ProductAttachment> productAttachmentList);

    int queryListCount(PageParameter<ProductVO> pageParameter);

    List<ProductVO> queryList(PageParameter<ProductVO> pageParameter);

    /**
     * 单独创建一个附件 用于附件上传到mdb时给出id
     * @param productAttachment
     * @return
     */
    int createAttachment(ProductAttachment productAttachment);

    /**
     * 根据productId导出已发布的学校
     * @param productId
     * @return
     */
    List<OrgExclVO> getOutPutExclOrgByProduct(Long productId);

    /**
     * 发布临床-机构
     * @param productOrgList
     * @return
     */
    Integer addProductOrgs(List<ProductOrg> productOrgList);

    /**
     * 跟新发布状态
     * @param productVO
     * @return
     */
    Integer updateProduct(ProductVO productVO);

    /**
     * 根据临床id 获取临床-机构信息
     * @param productId
     * @return
     */
    List<Long> getListProductOrgByProductId(Long productId);

    /**
     * 获取历史
     * @param pageParameter
     * @return
     */
    List<ProductHistorylVO> listProductHistory(PageParameter<ProductHistorylVO> pageParameter);

    /**
     * 获取临床集合
     * @param productName
     * @return
     */
    List<Product> getListProduct(String productName);


    /**
     * 根据产品id 获取产品的名字
     * @param materialId
     * @return
     */
    String getProductNameById(Long materialId);

    /**
     * 根据产品id 获取到审核人
     * @param productId
     * @return
     */
    List<Long> getAuthorList(Long productId);

    /**
     *根据
     * @param useridList
     * @return
     */
    Set<String> getAuthorOpenid(List<Long> useridList);


    /**
     * 获取到所有审核人的名字
     * @param productId
     * @return
     */
    String getAllAuthorName(Long productId);

    /**
     * 获取是否勾选了 当前产品公告 is_new
     * @param id
     * @return
     */
    Boolean getIsActiveByProductId(Long id);
}
