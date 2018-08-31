package com.bc.pmpheep.back.vo;


import com.bc.pmpheep.annotation.ExcelHeader;
import com.bc.pmpheep.back.po.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.ibatis.type.Alias;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

/**
 * 临床决策-申报表
 */

@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
@Alias("ExpertationVO")
public class ExpertationVO implements Serializable {


    private Long	id	;	//	20
    private Long	product_id	;	//	20
    private Long	expert_type	;	//	20	申报类型（1=临床/2=用药/3=中医）
    private Long	user_id	;	//	20	作家姓名
    @ExcelHeader(header = " 作家姓名")
    private String	realname	;	//	20
    private String	sex	;	//	3
    private Date	birthday	;	//	0
    private Long	experience	;	//	3	教龄
    @ExcelHeader(header = "工作单位")
    private String	org_name	;	//	60	工作单位
    private String unitName; // 申报单位

    private long org_id; // 申报单位 为0 代表出版社
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
    private short	idtype	;	//	3	证件类型
    private String	idcard	;	//	40	证件号码
    private String	telephone	;	//	30	联系电话
    private String	fax	;	//	50	传真
    private Boolean	is_dispensed	;	//	1	服从调剂
    private String	expertise	;	//	50	专业特长
    private Integer	online_progress	;	//	3	审核进度
    @ExcelHeader(header = "审核进度")
    private String onlineProgressName ;
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

    private String unit_advise_online;//所在单位意见文字
    private String product_name; //
    private Long auditor_id; //审核人id 查询用 传入当前登录人id
    @ExcelHeader(header = "账号")
    private String username; //账户
    private Boolean is_staging;
    private short degree;
    private short education; //学历 （数字代号）
    private String banknumber; //银行卡号
    private String bankaddress;//银行地址（开户行）
    private String declare_name; //申报单位名称
    private String syllabus_name; // 附件名称

    private Boolean amIAnAuditor; // 当前登录人是否是此单审核人
    private List<Long> followingAuditor; //当部门领导登录时，其管辖下所有人员的id列表，用于查询这些人审核的申请，供领导查看

    private List<ProductType> productSubjectTypeList; //学科分类
    private List<ProductType> productProfessionTypeList1; //专业分类

    private List<ProductType> productContentTypeList; //内容分类
    @ExcelHeader(header = "学科分类",cellType = "2")
    private String productSubjectTypeStr; //学科分类 excel导出字符串
    @ExcelHeader(header = "内容分类",cellType = "2")
    private String productContentTypeStr; //内容分类 excel导出字符串
    @ExcelHeader(header = "专业分类",cellType = "2")
    private String productProfessionTypeStr; //专业分类 excel导出字符串

    private List<DecEduExp> DecEduExpList; // 主要学习经历
    private List<DecWorkExp> DecWorkExpList; //主要工作经历
    private List<DecAcade> DecAcadeList; //主要学术兼职
    //private List<DecTextbook> DecTextbookList;
    private List<DecTextbookPmph> DecTextbookPmphList;  // 人卫社教材编写情况
    private List<DecMonograph> DecMonographList; //主编学术专著情况
    private List<DecNationalPlan> DecNationalPlanList;
    private List<DecExtension> DecExtensionList; //扩展项
    private List<DecEditorBook> DecEditorBookList;//主编或参编图书情况

    private List<DecProfessionAward> DecProfessionAwardList; // 本专业获奖情况
    private List<DecArticlePublished>  DecArticlePublishedList; // 文章发表情况

    private List<ProductProfessionType> ProductProfessionTypeList; //申报专业分类

   // private String schoolStauts;
    //private String pmphStauts;
    private String startCommitDate;
    private String endCommitDate;
    private String sql;
    private boolean isFinalResult;

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



    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
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

    public short getIdtype() {
        return idtype;
    }

    public void setIdtype(short idtype) {
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

    public Integer getOnline_progress() {
        return online_progress;
    }

    public void setOnline_progress(Integer online_progress) {
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

    public Object setProductSubjectTypeStr() {
        if(this.productSubjectTypeList!=null && this.productSubjectTypeList.size()>0){
            //this.productSubjectTypeStr = "=\"\""+"&\"1.aaa.\"&CHAR(10)"+"&\"2.bbb.\"&CHAR(10)&\"3.ccc. \"";
            this.productSubjectTypeStr = "\"\"";
            for (int i =0;i<productSubjectTypeList.size();i++) {
                ProductType p = productSubjectTypeList.get(i);
                this.productSubjectTypeStr += "&\""+(i+1)+"."+p.getType_name()+"\"&CHAR(10)";
            }
        }
        return null;
    }

    public String getProductContentTypeStr() {

        return productContentTypeStr;
    }

    public Object setProductContentTypeStr() {
        if(this.productContentTypeList!=null && this.productContentTypeList.size()>0){
            //this.productSubjectTypeStr = "=\"\""+"&\"1.aaa.\"&CHAR(10)"+"&\"2.bbb.\"&CHAR(10)&\"3.ccc. \"";
            this.productContentTypeStr = "\"\"";
            for (int i =0;i<productContentTypeList.size();i++) {
                ProductType p = productContentTypeList.get(i);
                this.productContentTypeStr += "&\""+(i+1)+"."+p.getType_name()+"\"&CHAR(10)";
            }
        }
        return null;
    }
    public Object setProductProfessionTypeStr() {
        if(this.productProfessionTypeList1!=null && this.productProfessionTypeList1.size()>0){
            //this.productSubjectTypeStr = "=\"\""+"&\"1.aaa.\"&CHAR(10)"+"&\"2.bbb.\"&CHAR(10)&\"3.ccc. \"";
            this.productProfessionTypeStr = "\"\"";
            for (int i =0;i<productProfessionTypeList1.size();i++) {
                ProductType p = productProfessionTypeList1.get(i);
                this.productProfessionTypeStr += "&\""+(i+1)+"."+p.getType_name()+"\"&CHAR(10)";
            }
        }
        return null;
    }

    public void setExcelTypeStr(){
        this.setProductSubjectTypeStr();
        this.setProductContentTypeStr();
        this.setProductProfessionTypeStr();
    }

    public void setProductSubjectTypeStr(String productSubjectTypeStr) {
        this.productSubjectTypeStr = productSubjectTypeStr;
    }

    public void setProductContentTypeStr(String productContentTypeStr) {
        this.productContentTypeStr = productContentTypeStr;
    }

    public List<DecEduExp> getDecEduExpList() {
        return DecEduExpList;
    }

    public void setDecEduExpList(List<DecEduExp> decEduExpList) {
        DecEduExpList = decEduExpList;
    }

    public List<DecWorkExp> getDecWorkExpList() {
        return DecWorkExpList;
    }

    public void setDecWorkExpList(List<DecWorkExp> decWorkExpList) {
        DecWorkExpList = decWorkExpList;
    }

    public List<DecAcade> getDecAcadeList() {
        return DecAcadeList;
    }

    public void setDecAcadeList(List<DecAcade> decAcadeList) {
        DecAcadeList = decAcadeList;
    }

    /*public List<DecTextbook> getDecTextbookList() {
        return DecTextbookList;
    }

    public void setDecTextbookList(List<DecTextbook> decTextbookList) {
        DecTextbookList = decTextbookList;
    }*/

    public List<DecMonograph> getDecMonographList() {
        return DecMonographList;
    }

    public void setDecMonographList(List<DecMonograph> decMonographList) {
        DecMonographList = decMonographList;
    }

    public List<DecNationalPlan> getDecNationalPlanList() {
        return DecNationalPlanList;
    }

    public void setDecNationalPlanList(List<DecNationalPlan> decNationalPlanList) {
        DecNationalPlanList = decNationalPlanList;
    }

    public Boolean getIs_staging() {
        return is_staging;
    }

    public void setIs_staging(Boolean is_staging) {
        this.is_staging = is_staging;
    }

    public short getDegree() {
        return degree;
    }

    public void setDegree(short degree) {
        this.degree = degree;
    }

    public List<DecExtension> getDecExtensionList() {
        return DecExtensionList;
    }

    public void setDecExtensionList(List<DecExtension> decExtensionList) {
        DecExtensionList = decExtensionList;
    }

    public List<DecEditorBook> getDecEditorBookList() {
        return DecEditorBookList;
    }

    public void setDecEditorBookList(List<DecEditorBook> decEditorBookList) {
        DecEditorBookList = decEditorBookList;
    }

    public short getEducation() {
        return education;
    }

    public void setEducation(short education) {
        this.education = education;
    }

    public String getBanknumber() {
        return banknumber;
    }

    public void setBanknumber(String banknumber) {
        this.banknumber = banknumber;
    }

    public String getBankaddress() {
        return bankaddress;
    }

    public void setBankaddress(String bankaddress) {
        this.bankaddress = bankaddress;
    }

    public Long getExperience() {
        return experience;
    }

    public void setExperience(Long experience) {
        this.experience = experience;
    }

    public String getDeclare_name() {
        return declare_name;
    }

    public void setDeclare_name(String declare_name) {
        this.declare_name = declare_name;
    }

    public String getSyllabus_name() {
        return syllabus_name;
    }

    public void setSyllabus_name(String syllabus_name) {
        this.syllabus_name = syllabus_name;
    }

    public long getOrg_id() {
        return org_id;
    }

    public void setOrg_id(long org_id) {
        this.org_id = org_id;
    }

    public String getOnlineProgressName() {
        return onlineProgressName;
    }

    public void setOnlineProgressName(String onlineProgressName) {
        this.onlineProgressName = onlineProgressName;
    }

    public List<DecTextbookPmph> getDecTextbookPmphList() {
        return DecTextbookPmphList;
    }

    public void setDecTextbookPmphList(List<DecTextbookPmph> decTextbookPmphList) {
        DecTextbookPmphList = decTextbookPmphList;
    }

    public Boolean getAmIAnAuditor() {
        return amIAnAuditor;
    }

    public void setAmIAnAuditor(Boolean amIAnAuditor) {
        this.amIAnAuditor = amIAnAuditor;
    }

    public List<DecProfessionAward> getDecProfessionAwardList() {
        return DecProfessionAwardList;
    }

    public void setDecProfessionAwardList(List<DecProfessionAward> decProfessionAwardList) {
        DecProfessionAwardList = decProfessionAwardList;
    }

    public List<DecArticlePublished> getDecArticlePublishedList() {
        return DecArticlePublishedList;
    }

    public void setDecArticlePublishedList(List<DecArticlePublished> decArticlePublishedList) {
        DecArticlePublishedList = decArticlePublishedList;
    }

    public List<Long> getFollowingAuditor() {
        return followingAuditor;
    }

    public void setFollowingAuditor(List<Long> followingAuditor) {
        this.followingAuditor = followingAuditor;
    }

    public List<ProductType> getProductProfessionTypeList1() {
        return productProfessionTypeList1;
    }

    public void setProductProfessionTypeList1(List<ProductType> productProfessionTypeList1) {
        this.productProfessionTypeList1 = productProfessionTypeList1;
    }

    public String getProductProfessionTypeStr() {
        return productProfessionTypeStr;
    }

    public void setProductProfessionTypeStr(String productProfessionTypeStr) {
        this.productProfessionTypeStr = productProfessionTypeStr;
    }


    public List<ProductProfessionType> getProductProfessionTypeList() {
        return ProductProfessionTypeList;
    }

    public void setProductProfessionTypeList(List<ProductProfessionType> productProfessionTypeList) {
        ProductProfessionTypeList = productProfessionTypeList;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getUnit_advise_online() {
        return unit_advise_online;
    }

    public void setUnit_advise_online(String unit_advise_online) {
        this.unit_advise_online = unit_advise_online;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

//    public String getSchoolStauts() {
//        return schoolStauts;
//    }
//
//    public void setSchoolStauts(String schoolStauts) {
//        this.schoolStauts = schoolStauts;
//    }
//
//    public String getPmphStauts() {
//        return pmphStauts;
//    }
//
//    public void setPmphStauts(String pmphStauts) {
//        this.pmphStauts = pmphStauts;
//    }

    public String getStartCommitDate() {
        return startCommitDate;
    }

    public void setStartCommitDate(String startCommitDate) {
        this.startCommitDate = startCommitDate;
    }

    public String getEndCommitDate() {
        return endCommitDate;
    }

    public void setEndCommitDate(String endCommitDate) {
        this.endCommitDate = endCommitDate;
    }

    public boolean isFinalResult() {
        return isFinalResult;
    }

    public void setFinalResult(boolean finalResult) {
        isFinalResult = finalResult;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
