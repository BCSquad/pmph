package com.bc.pmpheep.back.po;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;

/**
 * WriterUser 实体类
 * 
 * @author 曾庆峰
 * 
 */
@Alias("WriterUser")
public class WriterUser implements Serializable {
    /**
     * 主键
     */
    private Long    id;
    /**
     * 用户名
     */
    private String  username;
    /**
     * 密码
     */
    private String  password;
    /**
     * 是否禁用
     */
    private Integer isDisabled;
    /**
     * 对应学校id
     */
    private Long    orgId;
    /**
     * 真实姓名
     */
    private String  realname;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 生日
     */
    private Date    birthday;
    /**
     * 教龄
     */
    private Integer experience;
    /**
     * 职务
     */
    private String  position;
    /**
     * 职称
     */
    private String  title;
    /**
     * 传真
     */
    private String  fax;
    /**
     * 手机
     */
    private String  handphone;
    /**
     * 电话
     */
    private String  telephone;
    /**
     * 身份证
     */
    private String  idcard;
    /**
     * 邮箱
     */
    private String  email;
    /**
     * 地址
     */
    private String  address;
    /**
     * 邮编
     */
    private String  postcode;
    /**
     * 级别
     */
    private Integer rank;
    /**
     * 是否通过教师认证
     */
    private Integer isTeather;
    /**
     * 教师资格认证
     */
    private String  cert;
    /**
     * 教师资格认证时间
     */
    private Date    authTime;
    /**
     * 教师认证人类型
     */
    private Integer authUserType;
    /**
     * 教师认证人id
     */
    private Long    authUserId;
    /**
     * 是否作家
     */
    private Integer isWriter;
    /**
     * 是否专家
     */
    private Integer isExpert;
    /**
     * 头像
     */
    private String  avatar;
    /**
     * 备注
     */
    private String  note;
    /**
     * 显示顺序
     */
    private Integer sort;
    /**
     * 是否逻辑删除
     */
    private Integer isDeleted;
    /**
     * 创建时间
     */
    private Date    gmtCreate;
    /**
     * 修改时间
     */
    private Date    gmtUpdate;

    public WriterUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public WriterUser(String username, String password, Integer isDisabled, Long orgId,
    String realname, Integer sex, Date birthday, Integer experience, String position, String title,
    String fax, String handphone, String telephone, String idcard, String email, String address,
    String postcode, Integer rank, Integer isTeather, String cert, Date authTime,
    Integer authUserType, Long authUserId, Integer isWriter, Integer isExpert, String avatar,
    String note, Integer sort, Integer isDeleted, Date gmtCreate, Date gmtUpdate) {
        this.username = username;
        this.password = password;
        this.isDisabled = isDisabled;
        this.orgId = orgId;
        this.realname = realname;
        this.sex = sex;
        this.birthday = birthday;
        this.experience = experience;
        this.position = position;
        this.title = title;
        this.fax = fax;
        this.handphone = handphone;
        this.telephone = telephone;
        this.idcard = idcard;
        this.email = email;
        this.address = address;
        this.postcode = postcode;
        this.rank = rank;
        this.isTeather = isTeather;
        this.cert = cert;
        this.authTime = authTime;
        this.authUserType = authUserType;
        this.authUserId = authUserId;
        this.isWriter = isWriter;
        this.isExpert = isExpert;
        this.avatar = avatar;
        this.note = note;
        this.sort = sort;
        this.isDeleted = isDeleted;
        this.gmtCreate = gmtCreate;
        this.gmtUpdate = gmtUpdate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getHandphone() {
        return handphone;
    }

    public void setHandphone(String handphone) {
        this.handphone = handphone;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getIdcard() {
        return idcard;
    }

    /**
     * @return the isDisabled
     */
    public Integer getIsDisabled() {
        return isDisabled;
    }

    /**
     * @param isDisabled the isDisabled to set
     */
    public void setIsDisabled(Integer isDisabled) {
        this.isDisabled = isDisabled;
    }

    /**
     * @return the isTeather
     */
    public Integer getIsTeather() {
        return isTeather;
    }

    /**
     * @param isTeather the isTeather to set
     */
    public void setIsTeather(Integer isTeather) {
        this.isTeather = isTeather;
    }

    /**
     * @return the isWriter
     */
    public Integer getIsWriter() {
        return isWriter;
    }

    /**
     * @param isWriter the isWriter to set
     */
    public void setIsWriter(Integer isWriter) {
        this.isWriter = isWriter;
    }

    /**
     * @return the isExpert
     */
    public Integer getIsExpert() {
        return isExpert;
    }

    /**
     * @param isExpert the isExpert to set
     */
    public void setIsExpert(Integer isExpert) {
        this.isExpert = isExpert;
    }

    /**
     * @return the isDeleted
     */
    public Integer getIsDeleted() {
        return isDeleted;
    }

    /**
     * @param isDeleted the isDeleted to set
     */
    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getCert() {
        return cert;
    }

    public void setCert(String cert) {
        this.cert = cert;
    }

    public Date getAuthTime() {
        return authTime;
    }

    public void setAuthTime(Date authTime) {
        this.authTime = authTime;
    }

    public Integer getAuthUserType() {
        return authUserType;
    }

    public void setAuthUserType(Integer authUserType) {
        this.authUserType = authUserType;
    }

    public Long getAuthUserId() {
        return authUserId;
    }

    public void setAuthUserId(Long authUserId) {
        this.authUserId = authUserId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtUpdate() {
        return gmtUpdate;
    }

    public void setGmtUpdate(Date gmtUpdate) {
        this.gmtUpdate = gmtUpdate;
    }

    @Override
    public String toString() {
        return "WriterUser [id=" + id + ", username=" + username + ", password=" + password
               + ", isDisabled=" + isDisabled + ", orgId=" + orgId + ", realname=" + realname
               + ", sex=" + sex + ", birthday=" + birthday + ", experience=" + experience
               + ", position=" + position + ", title=" + title + ", fax=" + fax + ", handphone="
               + handphone + ", telephone=" + telephone + ", idcard=" + idcard + ", email=" + email
               + ", address=" + address + ", postcode=" + postcode + ", rank=" + rank
               + ", isTeather=" + isTeather + ", cert=" + cert + ", authTime=" + authTime
               + ", authUserType=" + authUserType + ", authUserId=" + authUserId + ", isWriter="
               + isWriter + ", isExpert=" + isExpert + ", avatar=" + avatar + ", note=" + note
               + ", sort=" + sort + ", isDeleted=" + isDeleted + ", gmtCreate=" + gmtCreate
               + ", gmtUpdate=" + gmtUpdate + "]";
    }

}
