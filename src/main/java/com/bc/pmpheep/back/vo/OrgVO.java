package com.bc.pmpheep.back.vo;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

/**
 * @author MrYang
 * @CreateDate 2017年9月26日 上午9:33:25
 * 
 **/
@SuppressWarnings("serial")
@Alias("OrgVO")
public class OrgVO implements Serializable {
    // 主键
    private Long    id;
    // 机构名称
    private String  orgName;
    // 机构类型id
    private Long    orgTypeId;
    // 机构类型名称
    private String  orgTypeName;
    // 上级机构id
    private Long    parentId;
    // 区域id
    private Long    areaId;
    // 所属区域名称
    private String  areaName;
    // 联系人
    private String  countactPerson;
    // 联系电话
    private String  countactPhone;
    // 备注
    private String  note;
    // 显示顺序
    private Integer sort;
    // 条件分页总条数
    private Integer count;

    public OrgVO() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Long getOrgTypeId() {
        return orgTypeId;
    }

    public void setOrgTypeId(Long orgTypeId) {
        this.orgTypeId = orgTypeId;
    }

    public String getOrgTypeName() {
        return orgTypeName;
    }

    public void setOrgTypeName(String orgTypeName) {
        this.orgTypeName = orgTypeName;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getCountactPerson() {
        return countactPerson;
    }

    public void setCountactPerson(String countactPerson) {
        this.countactPerson = countactPerson;
    }

    public String getCountactPhone() {
        return countactPhone;
    }

    public void setCountactPhone(String countactPhone) {
        this.countactPhone = countactPhone;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * @return the parentId
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * @param parentId the parentId to set
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * <pre>
     * 功能描述：
     * 使用示范：
     *
     * @return
     * </pre>
     */
    @Override
    public String toString() {
        return "{id:" + id + ", orgName:" + orgName + ", orgTypeId:" + orgTypeId + ", orgTypeName:"
               + orgTypeName + ", parentId=" + parentId + ", areaId:" + areaId + ", areaName:"
               + areaName + ", countactPerson:" + countactPerson + ", countactPhone:"
               + countactPhone + ", note:" + note + ", sort:" + sort + ", count:" + count + "}";
    }

}
