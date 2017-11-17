package com.bc.pmpheep.back.po;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

/**
 * Area(区域表（省市县）)实体类 --- Area
 * 
 * @author 曾庆峰
 * 
 */
@SuppressWarnings("serial")
@Alias("Area")
public class Area implements Serializable {
    /**
     * 主键
     */
    private Long    id;
    /**
     * 上级ID
     */
    private Long    parentId;
    /**
     * 区域名称
     */
    private String  areaName;
    /**
     * 显示顺序
     */
    private Integer sort;

    public Area() {

    }

    /**
     * 必传参数构造器
     * 
     * @param parentId
     * @param areaName
     * @param sort
     */
    public Area(Long parentId, String areaName, Integer sort) {
        this.parentId = parentId;
        this.areaName = areaName;
        this.sort = sort;
    }

    public Area(Long id, String areaName) {
        this.id = id;
        this.areaName = areaName;
    }

    /**
     * 单体对象查询快速创建构造器
     * 
     * @param id
     */
    public Area(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "{id:" + id + ", parentId:" + parentId + ", areaName:" + areaName + ", sort:" + sort
               + "}";
    }

}
