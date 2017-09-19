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
@SuppressWarnings("serial")
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
    private Integer isTeacher;
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
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
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
     * @return the orgId
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * @param orgId the orgId to set
     */
    public void setOrgId(Long orgId) {
        this.orgId = orgId;
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

    /**
     * @return the sex
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * @param sex the sex to set
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * @return the birthday
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * @param birthday the birthday to set
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * @return the experience
     */
    public Integer getExperience() {
        return experience;
    }

    /**
     * @param experience the experience to set
     */
    public void setExperience(Integer experience) {
        this.experience = experience;
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
     * @return the fax
     */
    public String getFax() {
        return fax;
    }

    /**
     * @param fax the fax to set
     */
    public void setFax(String fax) {
        this.fax = fax;
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
     * @return the telephone
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * @param telephone the telephone to set
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * @return the idcard
     */
    public String getIdcard() {
        return idcard;
    }

    /**
     * @param idcard the idcard to set
     */
    public void setIdcard(String idcard) {
        this.idcard = idcard;
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
     * @return the rank
     */
    public Integer getRank() {
        return rank;
    }

    /**
     * @param rank the rank to set
     */
    public void setRank(Integer rank) {
        this.rank = rank;
    }

    /**
     * @return the cert
     */
    public String getCert() {
        return cert;
    }

    /**
     * @param cert the cert to set
     */
    public void setCert(String cert) {
        this.cert = cert;
    }

    /**
     * @return the authTime
     */
    public Date getAuthTime() {
        return authTime;
    }

    /**
     * @param authTime the authTime to set
     */
    public void setAuthTime(Date authTime) {
        this.authTime = authTime;
    }

    /**
     * @return the authUserType
     */
    public Integer getAuthUserType() {
        return authUserType;
    }

    /**
     * @param authUserType the authUserType to set
     */
    public void setAuthUserType(Integer authUserType) {
        this.authUserType = authUserType;
    }

    /**
     * @return the authUserId
     */
    public Long getAuthUserId() {
        return authUserId;
    }

    /**
     * @param authUserId the authUserId to set
     */
    public void setAuthUserId(Long authUserId) {
        this.authUserId = authUserId;
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
     * @return the avatar
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * @param avatar the avatar to set
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * @return the note
     */
    public String getNote() {
        return note;
    }

    /**
     * @param note the note to set
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * @return the sort
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * @param sort the sort to set
     */
    public void setSort(Integer sort) {
        this.sort = sort;
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

    /**
     * @return the gmtCreate
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * @param gmtCreate the gmtCreate to set
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * @return the gmtUpdate
     */
    public Date getGmtUpdate() {
        return gmtUpdate;
    }

    /**
     * @return the isTeacher
     */
    public Integer getIsTeacher() {
        return isTeacher;
    }

    /**
     * @param isTeacher the isTeacher to set
     */
    public void setIsTeacher(Integer isTeacher) {
        this.isTeacher = isTeacher;
    }

    /**
     * @param gmtUpdate the gmtUpdate to set
     */
    public void setGmtUpdate(Date gmtUpdate) {
        this.gmtUpdate = gmtUpdate;
    }

    public WriterUser() {
    }

    public WriterUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * @param id
     * @param username
     * @param password
     * @param isDisabled
     * @param orgId
     * @param realname
     * @param sex
     * @param birthday
     * @param experience
     * @param position
     * @param title
     * @param fax
     * @param handphone
     * @param telephone
     * @param idcard
     * @param email
     * @param address
     * @param postcode
     * @param rank
     * @param isTeather
     * @param cert
     * @param authTime
     * @param authUserType
     * @param authUserId
     * @param isWriter
     * @param isExpert
     * @param avatar
     * @param note
     * @param sort
     * @param isDeleted
     * @param gmtCreate
     * @param gmtUpdate
     */
    public WriterUser(Long id, String username, String password, Integer isDisabled, Long orgId,
    String realname, Integer sex, Date birthday, Integer experience, String position, String title,
    String fax, String handphone, String telephone, String idcard, String email, String address,
    String postcode, Integer rank, Integer isTeacher, String cert, Date authTime,
    Integer authUserType, Long authUserId, Integer isWriter, Integer isExpert, String avatar,
    String note, Integer sort, Integer isDeleted, Date gmtCreate, Date gmtUpdate) {
        super();
        this.id = id;
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
        this.isTeacher = isTeacher;
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

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "WriterUser [id=" + id + ", username=" + username + ", password=" + password
               + ", isDisabled=" + isDisabled + ", orgId=" + orgId + ", realname=" + realname
               + ", sex=" + sex + ", birthday=" + birthday + ", experience=" + experience
               + ", position=" + position + ", title=" + title + ", fax=" + fax + ", handphone="
               + handphone + ", telephone=" + telephone + ", idcard=" + idcard + ", email=" + email
               + ", address=" + address + ", postcode=" + postcode + ", rank=" + rank
               + ", isTeacher=" + isTeacher + ", cert=" + cert + ", authTime=" + authTime
               + ", authUserType=" + authUserType + ", authUserId=" + authUserId + ", isWriter="
               + isWriter + ", isExpert=" + isExpert + ", avatar=" + avatar + ", note=" + note
               + ", sort=" + sort + ", isDeleted=" + isDeleted + ", gmtCreate=" + gmtCreate
               + ", gmtUpdate=" + gmtUpdate + "]";
    }

}
