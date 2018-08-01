package com.bc.pmpheep.back.vo;


import com.bc.pmpheep.annotation.ExcelHeader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

/**
 * 临床决策-申报表
 */
public class ExpertationVO {

    private Long	id	;	//	20
    private Long	product_id	;	//	20
    private Long	expert_type	;	//	20	申报类型（1=临床/2=用药/3=中医）
    private Long	user_id	;	//	20	作家姓名
    @ExcelHeader(header = " 作家姓名")
    private String	realname	;	//	20
    private Boolean	sex	;	//	3
    private Date	birthday	;	//	0
    private Boolean	experience	;	//	3	教龄
    @ExcelHeader(header = "工作单位")
    private String	org_name	;	//	60	工作单位
    @ExcelHeader(header = "职务")
    private String	position	;	//	36	职务
    @ExcelHeader(header = "职称")
    private String	title	;	//	30	职称
    private String	address	;	//	50	联系地址
    private String	postcode	;	//	20	邮编
    @ExcelHeader(header = "手机")
    private String	handphone	;	//	25	手机
    @ExcelHeader(header = "邮箱")
    private String	email	;	//	40	邮箱
    private Boolean	idtype	;	//	3	证件类型
    private String	idcard	;	//	40	证件号码
    private String	telephone	;	//	30	联系电话
    private String	fax	;	//	50	传真
    private Boolean	is_dispensed	;	//	1	服从调剂
    private String	expertise	;	//	50	专业特长
    private Boolean	online_progress	;	//	3	审核进度
    private Timestamp auth_date	;	//	0	审核通过时间
    private String	return_cause	;	//	100	退回原因
    private Boolean	is_deleted	;	//	1	是否被逻辑删除t
    private Timestamp	gmt_create	;	//	0	创建时间
    private Timestamp	gmt_update	;	//	0	修改时间
    private Timestamp	commit_date	;	//	0	提交时间
    private String	remark	;	//	100	备注
    private String	sub_classification	;	//	100	学科分类
    private String	cont_classification	;	//	100	内容分类
    private String unit_advise; //单位意见（扫描件上传于mongodb的id）
    private String product_name; //
    private Long auditor_id; //审核人id 查询用 传入当前登录人id
    @ExcelHeader(header = "账号")
    private String username; //账户

    private List<ProductType> productSubjectTypeList; //学科分类

    private List<ProductType> productContentTypeList; //内容分类
    @ExcelHeader(header = "学科分类",cellType = "2")
    private String productSubjectTypeStr; //学科分类 excel导出字符串
    @ExcelHeader(header = "内容分类",cellType = "2")
    private String productContentTypeStr; //内容分类 excel导出字符串

    public ExpertationVO() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public Long getExpert_type() {
        return expert_type;
    }

    public void setExpert_type(Long expert_type) {
        this.expert_type = expert_type;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getRealname() {
        return realname!=null?realname.trim():null;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Boolean getExperience() {
        return experience;
    }

    public void setExperience(Boolean experience) {
        this.experience = experience;
    }

    public String getOrg_name() {
        return org_name;
    }

    public void setOrg_name(String org_name) {
        this.org_name = org_name;
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

    public Boolean getIdtype() {
        return idtype;
    }

    public void setIdtype(Boolean idtype) {
        this.idtype = idtype;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public Boolean getIs_dispensed() {
        return is_dispensed;
    }

    public void setIs_dispensed(Boolean is_dispensed) {
        this.is_dispensed = is_dispensed;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    public Boolean getOnline_progress() {
        return online_progress;
    }

    public void setOnline_progress(Boolean online_progress) {
        this.online_progress = online_progress;
    }

    public Timestamp getAuth_date() {
        return auth_date;
    }

    public void setAuth_date(Timestamp auth_date) {
        this.auth_date = auth_date;
    }

    public String getReturn_cause() {
        return return_cause;
    }

    public void setReturn_cause(String return_cause) {
        this.return_cause = return_cause;
    }

    public Boolean getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(Boolean is_deleted) {
        this.is_deleted = is_deleted;
    }

    public Timestamp getGmt_create() {
        return gmt_create;
    }

    public void setGmt_create(Timestamp gmt_create) {
        this.gmt_create = gmt_create;
    }

    public Timestamp getGmt_update() {
        return gmt_update;
    }

    public void setGmt_update(Timestamp gmt_update) {
        this.gmt_update = gmt_update;
    }

    public Timestamp getCommit_date() {
        return commit_date;
    }

    public void setCommit_date(Timestamp commit_date) {
        this.commit_date = commit_date;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSub_classification() {
        return sub_classification;
    }

    public void setSub_classification(String sub_classification) {
        this.sub_classification = sub_classification;
    }

    public String getCont_classification() {
        return cont_classification;
    }

    public void setCont_classification(String cont_classification) {
        this.cont_classification = cont_classification;
    }

    public String getUsername() {
        return username!=null?username.trim():null;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<ProductType> getProductSubjectTypeList() {
        return productSubjectTypeList;
    }

    public void setProductSubjectTypeList(List<ProductType> productSubjectTypeList) {
        this.productSubjectTypeList = productSubjectTypeList;
    }

    public List<ProductType> getProductContentTypeList() {
        return productContentTypeList;
    }

    public void setProductContentTypeList(List<ProductType> productContentTypeList) {
        this.productContentTypeList = productContentTypeList;
    }

    public Long getAuditor_id() {
        return auditor_id;
    }

    public void setAuditor_id(Long auditor_id) {
        this.auditor_id = auditor_id;
    }

    public String getUnit_advise() {
        return unit_advise;
    }

    public void setUnit_advise(String unit_advise) {
        this.unit_advise = unit_advise;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProductSubjectTypeStr() {



        return productSubjectTypeStr;
    }

    public void setProductSubjectTypeStr() {
        if(this.productSubjectTypeList!=null && this.productSubjectTypeList.size()>0){
            //this.productSubjectTypeStr = "=\"\""+"&\"1.aaa.\"&CHAR(10)"+"&\"2.bbb.\"&CHAR(10)&\"3.ccc. \"";
            this.productSubjectTypeStr = "\"\"";
            for (int i =0;i<productSubjectTypeList.size();i++) {
                ProductType p = productSubjectTypeList.get(i);
                this.productSubjectTypeStr += "&\""+(i+1)+"."+p.getType_name()+"\"&CHAR(10)";
            }
        }
    }

    public String getProductContentTypeStr() {

        return productContentTypeStr;
    }

    public void setProductContentTypeStr() {
        if(this.productContentTypeList!=null && this.productContentTypeList.size()>0){
            //this.productSubjectTypeStr = "=\"\""+"&\"1.aaa.\"&CHAR(10)"+"&\"2.bbb.\"&CHAR(10)&\"3.ccc. \"";
            this.productContentTypeStr = "\"\"";
            for (int i =0;i<productContentTypeList.size();i++) {
                ProductType p = productContentTypeList.get(i);
                this.productContentTypeStr += "&\""+(i+1)+"."+p.getType_name()+"\"&CHAR(10)";
            }
        }
    }

    public void setExcelTypeStr(){
        this.setProductSubjectTypeStr();
        this.setProductContentTypeStr();
    }

}
