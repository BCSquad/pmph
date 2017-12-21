package com.bc.pmpheep.back.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.type.Alias;

import com.bc.pmpheep.back.po.TopicExtra;
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
	 * 预计交稿时间
	 */
	private Timestamp deadline;
	/**
	 * 选题来源 0=社策划/1=编辑策划/2=专家策划/3=离退休编审策划/4=上级交办/5=作者投稿
	 */
	private Integer source;
	/**
	 * 估计字数（千字）
	 */
	private Integer wordNumber;
	/**
	 * 预估图数
	 */
	private Integer pictureNumber;
	/**
	 * 学科及专业
	 */
	private String subject;
	/**
	 * 级别 0=低/1=中/2=高
	 */
	private Integer rank;
	/**
	 * 图书类别 0=专著/1=基础理论/2=论文集/3=科普/4=应用技术/5=工具书/6=其他
	 */
	private Integer type;
	/**
	 * 银行账户
	 */
	private Long bankAccountId;
	/**
	 * 作者购书
	 */
	private Integer purchase;
	/**
	 * 作者赞助
	 */
	private Integer sponsorship;
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

	public Integer getPurchase() {
		return purchase;
	}

	public void setPurchase(Integer purchase) {
		this.purchase = purchase;
	}

	public Integer getSponsorship() {
		return sponsorship;
	}

	public void setSponsorship(Integer sponsorship) {
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

}
