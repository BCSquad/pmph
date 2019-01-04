package com.bc.pmpheep.back.vo;

import com.bc.pmpheep.annotation.ExcelHeader;
import org.apache.ibatis.type.Alias;

/**
 * 用户积分表实体类VO
 * @author tyc
 * @date 2017年12月28日 下午14:08:10
 */
@SuppressWarnings("serial")
@Alias("WriterPointVO")
public class WriterPointVO implements java.io.Serializable{

	//主键
	private Long id;
	//用户id
	private Long userId;
	//真实姓名
	@ExcelHeader(header = "用户姓名")
	private String realname;
	//用户名
	@ExcelHeader(header = "用户账号")
	private String username;
	//消费积分合计
	@ExcelHeader(header = "兑换总积分")
	private Integer loss;
	//获取积分合计
	@ExcelHeader(header = "获得总积分")
	private Integer gain;
	//当前总积分
	@ExcelHeader(header = "当前总积分")
	private Integer total;
	//条件分页总条数分页查询
    private Integer count;
    //页面查询条件（状态）
    private Integer status;
	
	//构造器
	public WriterPointVO(){
	}
	
	public WriterPointVO(Long id){
		super();
		this.id = id;
	}
	
	public WriterPointVO(Long id, Long userId, Integer total, Integer gain, 
			Integer loss){
		super();
		this.id = id;
		this.userId = userId;
		this.total = total;
		this.gain = gain;
		this.loss = loss;
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

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getGain() {
		return gain;
	}

	public void setGain(Integer gain) {
		this.gain = gain;
	}

	public Integer getLoss() {
		return loss;
	}

	public void setLoss(Integer loss) {
		this.loss = loss;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "WriterPointVO [id=" + id + ", userId=" + userId + ", total="
				+ total + ", gain=" + gain + ", loss=" + loss + ", count="
				+ count + ", status=" + status + ", username=" + username
				+ ", realname=" + realname + "]";
	}
}
