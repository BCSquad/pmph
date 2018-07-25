package com.bc.pmpheep.back.dao;

import com.bc.pmpheep.back.po.Product;
import com.bc.pmpheep.back.vo.ProductVO;
import org.apache.ibatis.annotations.Param;

public interface ProductDao {

    /**
     * 根据类型查询一个临床决断产品 及其附表内容
     * @param product_type 类型
     * @param clinicalDecision 附件下载地址需要的前缀，后面拼接附件id
     * @return
     */
    ProductVO queryProductByProductType(@Param(value = "product_type") Long product_type,@Param(value = "uri") String clinicalDecision);

    /**
     * 更新下载次数+1
     * @param id
     * @return
     */
    int updateProductAttachmenDownLoadCountsByAttachment(String id);
}
