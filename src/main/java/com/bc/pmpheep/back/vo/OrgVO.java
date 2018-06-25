package com.bc.pmpheep.back.vo;

import java.io.Serializable;
import java.sql.Timestamp;

import com.bc.pmpheep.annotation.ExcelHeader;
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
    @ExcelHeader(header = "机构名称")
    private String    orgName;
    // 机构代码
    @ExcelHeader(header = "机构代码")
    private String    username;
    // 管理员姓名
    @ExcelHeader(header = "管理员姓名")
    private String    realname;
    // 机构类型id
    private String    orgTypeId;
    // 机构类型名称
    //@ExcelHeader(header = "机构类型名称")
    private String    orgTypeName;
    // 上级机构id
    private String    parentId;
    // 区域id
    private String    areaId;
    // 所属区域名称
   // @ExcelHeader(header = "所属区域名称")
    private String    areaName;
    // 联系人
   // @ExcelHeader(header = "联系人")
    private String    contactPerson;
    // 联系电话
    //@ExcelHeader(header = "联系电话")
    private String    contactPhone;
    // 审核进度

    private Integer   progress;
    @ExcelHeader(header = "审核进度")
    private String progressName;
    /**
     * 手机
     */
    @ExcelHeader(header = "手机")
    private String    handphone;
    /**
     * 邮箱
     */
    @ExcelHeader(header = "邮箱")
    private String    email;
    /**
     * 职务
     */
    @ExcelHeader(header = "职务")
    private String    position;
    /**
     * 职称
     */
    @ExcelHeader(header = " 职称")
    private String    title;
    /**
     * 地址
     */
    @ExcelHeader(header = "  地址")
    private String    address;
    /**
     * 邮编
     */
    @ExcelHeader(header = "  邮编")
    private String    postcode;
    /**
     * 委托书
     */
    @ExcelHeader(header = "  委托书")
    private String    proxy;
    // 备注
    @ExcelHeader(header = "  备注")
    private String    note;
    // 显示顺序
    @ExcelHeader(header = "显示顺序")
    private Integer   sort;
    // 条件分页总条数
    private Integer   count;
    // 创建时间
    private Timestamp gmtCreate;
    /**
     * 问卷ID
     */
    private Long      surveyId;

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
    public String getContactPerson() {
        return contactPerson;
    }

    /**
     * @param countactPerson the countactPerson to set
     */
    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    /**
     * @return the countactPhone
     */
    public String getContactPhone() {
        return contactPhone;
    }

    /**
     * @param countactPhone the countactPhone to set
     */
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    /**
     * @return the handphone
     */
    public String getHandphone() {
        return handphone;
    }

    /**
     * @param handphone the handphone to set
     */
    public void setHandphone(String handphone) {
        this.handphone = handphone;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the position
     */
    public String getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the postcode
     */
    public String getPostcode() {
        return postcode;
    }

    /**
     * @param postcode the postcode to set
     */
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    /**
     * @return the proxy
     */
    public String getProxy() {
        return proxy;
    }

    /**
     * @param proxy the proxy to set
     */
    public void setProxy(String proxy) {
        this.proxy = proxy;
    }

    /**
     * @return the surveyId
     */
    public Long getSurveyId() {
        return surveyId;
    }

    /**
     * @param surveyId the surveyId to set
     */
    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
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
        return "OrgVO {id=" + id + ", orgName=" + orgName + ", username=" + username
               + ", realname=" + realname + ", orgTypeId=" + orgTypeId + ", orgTypeName="
               + orgTypeName + ", parentId=" + parentId + ", areaId=" + areaId + ", areaName="
               + areaName + ", contactPerson=" + contactPerson + ", contactPhone=" + contactPhone
               + ", progress=" + progress + ", handphone=" + handphone + ", email=" + email
               + ", position=" + position + ", title=" + title + ", address=" + address
               + ", postcode=" + postcode + ", note=" + note + ", sort=" + sort + ", count="
               + count + ", gmtCreate=" + gmtCreate + "}";
    }

    public String getProgressName() {
        return progressName;
    }

    public void setProgressName(String progressName) {
        this.progressName = progressName;
    }
}
