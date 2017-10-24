package com.bc.pmpheep.back.vo;

import java.io.Serializable;
import java.sql.Timestamp;

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
    private String    id;
    // 机构名称
    private String    orgName;
    // 机构代码
    private String    username;
    // 管理员姓名
    private String    realname;
    // 机构类型id
    private String    orgTypeId;
    // 机构类型名称
    private String    orgTypeName;
    // 上级机构id
    private String    parentId;
    // 区域id
    private String    areaId;
    // 所属区域名称
    private String    areaName;
    // 联系人
    private String    countactPerson;
    // 联系电话
    private String    countactPhone;
    // 审核进度
    private Integer   progress;
    // 备注
    private String    note;
    // 显示顺序
    private Integer   sort;
    // 条件分页总条数
    private Integer   count;
    // 创建时间
    private Timestamp gmtCreate;

    public OrgVO() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgTypeId() {
        return orgTypeId;
    }

    public void setOrgTypeId(String orgTypeId) {
        this.orgTypeId = orgTypeId;
    }

    public String getOrgTypeName() {
        return orgTypeName;
    }

    public void setOrgTypeName(String orgTypeName) {
        this.orgTypeName = orgTypeName;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
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
    public String getParentId() {
        return parentId;
    }

    /**
     * @param parentId the parentId to set
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the progress
     */
    public Integer getProgress() {
        return progress;
    }

    /**
     * @param progress the progress to set
     */
    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    /**
     * @return the realname
     */
    public String getRealname() {
        return realname;
    }

    /**
     * @param realname the realname to set
     */
    public void setRealname(String realname) {
        this.realname = realname;
    }

    public Timestamp getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Timestamp gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * @return the countactPerson
     */
    public String getCountactPerson() {
        return countactPerson;
    }

    /**
     * @param countactPerson the countactPerson to set
     */
    public void setCountactPerson(String countactPerson) {
        this.countactPerson = countactPerson;
    }

    /**
     * @return the countactPhone
     */
    public String getCountactPhone() {
        return countactPhone;
    }

    /**
     * @param countactPhone the countactPhone to set
     */
    public void setCountactPhone(String countactPhone) {
        this.countactPhone = countactPhone;
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
        return "{id:" + id + ", orgName:" + orgName + ", username:" + username + ", realname:"
               + realname + ", progress:" + progress + ", orgTypeId:" + orgTypeId
               + ", orgTypeName:" + orgTypeName + ", parentId=" + parentId + ", areaId:" + areaId
               + ", areaName:" + areaName + ", countactPerson:" + countactPerson
               + ", countactPhone:" + countactPhone + ", note:" + note + ", sort:" + sort
               + ", count:" + count + "}";
    }
}
