package com.test;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * WriterProfile entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "writer_profile", catalog = "pmph_imesp_db", uniqueConstraints = @UniqueConstraint(columnNames = "user_id"))
public class WriterProfile implements java.io.Serializable {

	// Fields

	private Long id;
	private Long userId;
	private String profile;
	private String tag;

	// Constructors

	/** default constructor */
	public WriterProfile() {
	}

	/** minimal constructor */
	public WriterProfile(Long userId) {
		this.userId = userId;
	}

	/** full constructor */
	public WriterProfile(Long userId, String profile, String tag) {
		this.userId = userId;
		this.profile = profile;
		this.tag = tag;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "user_id", unique = true, nullable = false)
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "profile", length = 3000)
	public String getProfile() {
		return this.profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	@Column(name = "tag", length = 200)
	public String getTag() {
		return this.tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

}