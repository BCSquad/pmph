package com.bc.pmpheep.back.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.type.Alias;

import com.bc.pmpheep.back.po.TopicExtra;
import com.bc.pmpheep.back.po.TopicSimilarBook;
import com.bc.pmpheep.back.po.TopicWriter;

/**
 * 
 * 
 * 功能描述：选题申报详情
 * 
 * 
 * 
 * @author (作者) 曾庆峰
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017年12月21日
 * @modify (最后修改时间)
 * @修改人 ：曾庆峰
 * @审核人 ：
 *
 */
@Alias("TopicTextVO")
public class TopicTextVO implements Serializable {
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 作家id
	 */
	private Long userId;
	/**
	 * 书名
	 */
	private String bookname;
	/**
	 * 读者对象 0=医务工作者/1=医学院校师生/2=大众
	 */
	private Integer reader;
	/**
	 * 读者对象
	 */
	private String readType;
	/**
	 * 预计交稿时间
	 */
	private Timestamp deadline;
	/**
	 * 选题来源 0=社策划/1=编辑策划/2=修订/3=离退休编审策划/4=专家推荐/5=上级交办/6=作者投稿
	 */
	private Integer source;
	/**
	 * 选题来源
	 */
	private String[] sourceType;
	/**
	 * 估计字数（千字）
	 */
	private Integer wordNumber;
	/**
	 * 预估图数
	 */
	private Integer pictureNumber;
	private String readerQuantity;
	private String campaign;
	private String salesChannel;
	private String lifecycle;
	private String printAdvise;
	private String priceAdvise;
	private String printNumber;
	private String cost;
	private String minPrintNumber;
	private String benefit;
	private Boolean isRevision;
	private String revisionBookname;
	private String revisionAuthor;
	private Date revisionPublishDate;
	private Integer revisionPrint;
	private Integer revisionStock;
	private String realname;
	private String position;
	private Integer sex;
	private Integer price;
	private Integer positionProfession;
	private String[] positionProfessionName;
	private Integer degree;
	private String[] degreeName;
	private String workplace;
	private String phone;
	private String address;
	private String postcode;
	private String email;
	private Boolean isTranslation;
	private String originalPublisher;
	/**
	 * 学科及专业
	 */
	private String subject;
	/**
	 * 级别 0=低/1=中/2=高
	 */
	private Integer rank;
	/**
	 * 级别
	 */
	private String[] rankType;
	/**
	 * 图书类别 0=专著/1=基础理论/2=教材/3=论文集/4=图谱/5=科普/6=应用技术/7=教辅/8=工具书/9=其他
	 */
	private Integer type;
	/**
	 * 图书类别
	 */
	private String typeName[];
	/**
	 * 银行账户id
	 */
	private Long bankAccountId;
	/**
	 * 开户行
	 */
	private String bank;
	/**
	 * 银行帐号
	 */
	private String accountNumber;
	/**
	 * 作者购书
	 */
	private String purchase;
	/**
	 * 作者赞助
	 */
	private String sponsorship;
	/**
	 * 译稿原书名
	 */
	private String originalBookname;
	/**
	 * 原著作者
	 */
	private String originalAuthor;
	/**
	 * 国籍
	 */
	private String nation;
	/**
	 * 出版年代及版次
	 */
	private String edition;
	/**
	 * 选题申报额外信息
	 */
	private TopicExtra topicExtra;
	/**
	 * 选题申报编者情况
	 */
	private List<TopicWriter> topicWriters;
	/**
	 * 社外同类书情况表
	 */
	private List<TopicSimilarBook> topicSimilarBooks;
	/**
	 * 编辑id
	 */
	private Long editorId;
	/**
	 * 编辑真实姓名
	 */
	private String editorName;
	/**
	 * 编辑用户名
	 */
	private String username;
	/**
	 * 是否由主任受理
	 */
	private Boolean isDirectorHandling;
	/**
	 * 是否由编辑受理
	 */
	private Boolean isEditorHandling;
	/**
	 * 编辑是否接受办理
	 */
	private Boolean isAccepted;
	/**
	 * 审核意见
	 */
	private String authFeedback;

	public List<TopicSimilarBook> getTopicSimilarBooks() {
		return topicSimilarBooks;
	}

	public void setTopicSimilarBooks(List<TopicSimilarBook> topicSimilarBooks) {
		this.topicSimilarBooks = topicSimilarBooks;
	}

	public String getReaderQuantity() {
		return readerQuantity;
	}

	public void setReaderQuantity(String readerQuantity) {
		this.readerQuantity = readerQuantity;
	}

	public String getCampaign() {
		return campaign;
	}

	public void setCampaign(String campaign) {
		this.campaign = campaign;
	}

	public String getSalesChannel() {
		return salesChannel;
	}

	public void setSalesChannel(String salesChannel) {
		this.salesChannel = salesChannel;
	}

	public String getLifecycle() {
		return lifecycle;
	}

	public void setLifecycle(String lifecycle) {
		this.lifecycle = lifecycle;
	}

	public String getPrintAdvise() {
		return printAdvise;
	}

	public void setPrintAdvise(String printAdvise) {
		this.printAdvise = printAdvise;
	}

	public String getPriceAdvise() {
		return priceAdvise;
	}

	public void setPriceAdvise(String priceAdvise) {
		this.priceAdvise = priceAdvise;
	}

	public String getPrintNumber() {
		return printNumber;
	}

	public void setPrintNumber(String printNumber) {
		this.printNumber = printNumber;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public String getMinPrintNumber() {
		return minPrintNumber;
	}

	public void setMinPrintNumber(String minPrintNumber) {
		this.minPrintNumber = minPrintNumber;
	}

	public String getBenefit() {
		return benefit;
	}

	public void setBenefit(String benefit) {
		this.benefit = benefit;
	}

	public Boolean getIsRevision() {
		return isRevision;
	}

	public void setIsRevision(Boolean isRevision) {
		this.isRevision = isRevision;
	}

	public String getRevisionBookname() {
		return revisionBookname;
	}

	public void setRevisionBookname(String revisionBookname) {
		this.revisionBookname = revisionBookname;
	}

	public String getRevisionAuthor() {
		return revisionAuthor;
	}

	public void setRevisionAuthor(String revisionAuthor) {
		this.revisionAuthor = revisionAuthor;
	}

	public Date getRevisionPublishDate() {
		return revisionPublishDate;
	}

	public void setRevisionPublishDate(Date revisionPublishDate) {
		this.revisionPublishDate = revisionPublishDate;
	}

	public Integer getRevisionPrint() {
		return revisionPrint;
	}

	public void setRevisionPrint(Integer revisionPrint) {
		this.revisionPrint = revisionPrint;
	}

	public Integer getRevisionStock() {
		return revisionStock;
	}

	public Boolean getIsTranslation() {
		return isTranslation;
	}

	public void setIsTranslation(Boolean isTranslation) {
		this.isTranslation = isTranslation;
	}

	public void setRevisionStock(Integer revisionStock) {
		this.revisionStock = revisionStock;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getPositionProfession() {
		return positionProfession;
	}

	public void setPositionProfession(Integer positionProfession) {
		this.positionProfession = positionProfession;
	}

	public String getOriginalPublisher() {
		return originalPublisher;
	}

	public void setOriginalPublisher(String originalPublisher) {
		this.originalPublisher = originalPublisher;
	}

	public String[] getPositionProfessionName() {
		return positionProfessionName;
	}

	public void setPositionProfessionName(String[] positionProfessionName) {
		this.positionProfessionName = positionProfessionName;
	}

	public Integer getDegree() {
		return degree;
	}

	public void setDegree(Integer degree) {
		this.degree = degree;
	}

	public String[] getDegreeName() {
		return degreeName;
	}

	public void setDegreeName(String[] degreeName) {
		this.degreeName = degreeName;
	}

	public String getWorkplace() {
		return workplace;
	}

	public void setWorkplace(String workplace) {
		this.workplace = workplace;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEditorName() {
		return editorName;
	}

	public void setEditorName(String editorName) {
		this.editorName = editorName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getBookname() {
		return bookname;
	}

	public void setBookname(String bookname) {
		this.bookname = bookname;
	}

	public Integer getReader() {
		return reader;
	}

	public void setReader(Integer reader) {
		this.reader = reader;
	}

	public Timestamp getDeadline() {
		return deadline;
	}

	public void setDeadline(Timestamp deadline) {
		this.deadline = deadline;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public Integer getWordNumber() {
		return wordNumber;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public void setWordNumber(Integer wordNumber) {
		this.wordNumber = wordNumber;
	}

	public Integer getPictureNumber() {
		return pictureNumber;
	}

	public void setPictureNumber(Integer pictureNumber) {
		this.pictureNumber = pictureNumber;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getBankAccountId() {
		return bankAccountId;
	}

	public void setBankAccountId(Long bankAccountId) {
		this.bankAccountId = bankAccountId;
	}

	public String getPurchase() {
		return purchase;
	}

	public void setPurchase(String purchase) {
		this.purchase = purchase;
	}

	public String getSponsorship() {
		return sponsorship;
	}

	public void setSponsorship(String sponsorship) {
		this.sponsorship = sponsorship;
	}

	public String getOriginalBookname() {
		return originalBookname;
	}

	public void setOriginalBookname(String originalBookname) {
		this.originalBookname = originalBookname;
	}

	public String getOriginalAuthor() {
		return originalAuthor;
	}

	public void setOriginalAuthor(String originalAuthor) {
		this.originalAuthor = originalAuthor;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}

	public TopicExtra getTopicExtra() {
		return topicExtra;
	}

	public void setTopicExtra(TopicExtra topicExtra) {
		this.topicExtra = topicExtra;
	}

	public List<TopicWriter> getTopicWriters() {
		return topicWriters;
	}

	public void setTopicWriters(List<TopicWriter> topicWriters) {
		this.topicWriters = topicWriters;
	}

	public String getReadType() {
		return readType;
	}

	public void setReadType(String readType) {
		this.readType = readType;
	}

	public String[] getSourceType() {
		return sourceType;
	}

	public void setSourceType(String[] sourceType) {
		this.sourceType = sourceType;
	}

	public String[] getTypeName() {
		return typeName;
	}

	public void setTypeName(String[] typeName) {
		this.typeName = typeName;
	}

	public Long getEditorId() {
		return editorId;
	}

	public void setEditorId(Long editorId) {
		this.editorId = editorId;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public Boolean getIsDirectorHandling() {
		return isDirectorHandling;
	}

	public void setIsDirectorHandling(Boolean isDirectorHandling) {
		this.isDirectorHandling = isDirectorHandling;
	}

	public Boolean getIsEditorHandling() {
		return isEditorHandling;
	}

	public void setIsEditorHandling(Boolean isEditorHandling) {
		this.isEditorHandling = isEditorHandling;
	}

	public Boolean getIsAccepted() {
		return isAccepted;
	}

	public void setIsAccepted(Boolean isAccepted) {
		this.isAccepted = isAccepted;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String[] getRankType() {
		return rankType;
	}

	public void setRankType(String[] rankType) {
		this.rankType = rankType;
	}

	public String getAuthFeedback() {
		return authFeedback;
	}

	public void setAuthFeedback(String authFeedback) {
		this.authFeedback = authFeedback;
	}

}
