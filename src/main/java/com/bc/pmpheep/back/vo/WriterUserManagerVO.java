package com.bc.pmpheep.back.vo;

import java.io.Serializable;
import java.util.Date;

import com.bc.pmpheep.annotation.ExcelHeader;
import org.apache.ibatis.type.Alias;

import com.bc.pmpheep.back.util.ObjectUtil;

/**
 * @author MrYang
 * @CreateDate 2017年9月20日 上午9:19:30
 * 
 **/
@SuppressWarnings("serial")
@Alias("WriterUserManagerVO")
public class WriterUserManagerVO implements Serializable {
    /**
     * 主键
     */
    private Long    id;
    /**
     * 用户名
     */
    @ExcelHeader(header = "账号")
    private String  username;
    /**
     * 用户名以及真实姓名
     */
    //@ExcelHeader(header = "真实姓名")
    private String  name;
    /**
     * 是否禁用
     */
   // @ExcelHeader(header = "是否禁用")
    private Integer isDisabled;
    /**
     * 对应学校id
     */

    private Long    orgId;
    /**
     * 机构名称
     */
    @ExcelHeader(header = "所属机构")
    private String  orgName;
    /**
     * 昵称
     */
    @ExcelHeader(header = "昵称")
    private String  nickname;
    /**
     * 真实姓名
     */
    @ExcelHeader(header = "真实姓名")
    private String  realname;
    /**
     * 职务
     */
    @ExcelHeader(header = "职务")
    private String  position;
    /**
     * 职称
     */
    @ExcelHeader(header = "职称")
    private String  title;
    /**
     * 身份证
     */
    @ExcelHeader(header = "身份证号")
    private String  idcard;
    /**
     * 手机
     */
    @ExcelHeader(header = "手机")
    private String  handphone;
    /**
     * 邮箱
     */
    @ExcelHeader(header = "邮箱")
    private String  email;
    /**
     * 地址
     */
    @ExcelHeader(header = "邮寄地址")
    private String  address;
    /**
     * 级别
     */
    //@ExcelHeader(header = "级别")
    private Integer rank;
    // 认证进度 --- 0=未提交/1=已提交/2=被退回/3=通过

    private Short   progress;
    //@ExcelHeader(header = "认证进度")
    private String progressName;
    // 教师资格证--- 资格证图片的资源地址
   // @ExcelHeader(header = "教师资格证")
    private String  cert;
    /**
     * 级别名称
     */
    @ExcelHeader(header = "用户类型")
    private String  rankName;
    /**
     * 显示顺序
     */
   // @ExcelHeader(header = " 显示顺序")
    private Integer sort;
    /**
     * 小组id
     */
    private Long groupId;
    /**
     * 备注
     */
    @ExcelHeader(header = " 备注")
    private String  note;
    // 条件分页总条数
    private Integer count;
    // 是否教师
    private Boolean isTeacher;

    private Boolean isTop;

    private Date topSort;
    private String declareCount;

    public String getDeclareCount() {
        return declareCount;
    }

    public void setDeclareCount(String declareCount) {
        this.declareCount = declareCount;
    }

    public Boolean getIsTeacher() {
		return isTeacher;
	}

	public void setIsTeacher(Boolean isTeacher) {
		this.isTeacher = isTeacher;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(Integer isDisabled) {
        this.isDisabled = isDisabled;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
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

    public String getHandphone() {
        return handphone;
    }

    public void setHandphone(String handphone) {
        this.handphone = handphone;
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

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getRankName() {
        return rankName;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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
     * @return the progress
     */
    public Short getProgress() {
        return progress;
    }

    /**
     * @param progress the progress to set
     */
    public void setProgress(Short progress) {
        this.progress = progress;
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
		return "WriterUserManagerVO [id=" + id + ", username=" + username + ", name=" + name + ", isDisabled="
				+ isDisabled + ", orgId=" + orgId + ", orgName=" + orgName + ", nickname=" + nickname + ", realname="
				+ realname + ", position=" + position + ", title=" + title + ", idcard=" + idcard + ", handphone="
				+ handphone + ", email=" + email + ", address=" + address + ", rank=" + rank + ", progress=" + progress
				+ ", cert=" + cert + ", rankName=" + rankName + ", sort=" + sort + ", note=" + note + ", count=" + count
				+ ", isTeacher=" + isTeacher + "]";
	}




    public Date getTopSort() {
        return topSort;
    }

    public void setTopSort(Date topSort) {
        this.topSort = topSort;
    }


    public Boolean getIsTop() {
        return isTop;
    }

    public void setIsTop(Boolean isTop) {
        this.isTop = isTop;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getProgressName() {
        return progressName;
    }

    public void setProgressName(String progressName) {
        this.progressName = progressName;
    }
}
