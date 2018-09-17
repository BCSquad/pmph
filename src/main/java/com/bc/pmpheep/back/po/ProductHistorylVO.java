package com.bc.pmpheep.back.po;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 
 * <pre>
 * 功能描述：历史教材通知VO
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017-11-23
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
@SuppressWarnings("serial")
@Alias("ProductHistorylVO")
public class ProductHistorylVO implements Serializable {
    // Materia主键
    private Long      id;
    // 教材名称
    private String    productName;
    // 创建时间
    private Timestamp gmtCreate;
    // 发布机构数
    private Integer   orgCounts;
    // 总条数
    private Integer   count;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }



    /**
     * @return the gmtCreate
     */
    public Timestamp getGmtCreate() {
        return gmtCreate;
    }

    /**
     * @param gmtCreate the gmtCreate to set
     */
    public void setGmtCreate(Timestamp gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * @return the orgCounts
     */
    public Integer getOrgCounts() {
        return orgCounts;
    }

    /**
     * @param orgCounts the orgCounts to set
     */
    public void setOrgCounts(Integer orgCounts) {
        this.orgCounts = orgCounts;
    }

    /**
     * @return the count
     */
    public Integer getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
