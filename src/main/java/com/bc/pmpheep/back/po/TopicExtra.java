package com.bc.pmpheep.back.po;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

/**
 * 
 * 
 * 功能描述：选题申报额外信息表实体
 * 
 * 
 * 
 * @author (作者) 曾庆峰
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017年12月19日
 * @modify (最后修改时间)
 * @修改人 ：曾庆峰
 * @审核人 ：
 *
 */
@Alias("TopicExtra")
public class TopicExtra implements Serializable {
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 选题id
	 */
	private Long topicId;
	/**
	 * 选题理由
	 */
	private String reason;
	/**
	 * 出版价值
	 */
	private String price;
	/**
	 * 主要内容
	 */
	private String score;

	public TopicExtra() {
		super();
	}

	public TopicExtra(Long topicId, String reason, String price, String score) {
		super();
		this.topicId = topicId;
		this.reason = reason;
		this.price = price;
		this.score = score;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTopicId() {
		return topicId;
	}

	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

}
