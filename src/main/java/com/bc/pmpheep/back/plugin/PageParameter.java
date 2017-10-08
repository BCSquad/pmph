package com.bc.pmpheep.back.plugin;


import org.apache.ibatis.type.Alias;
import com.bc.pmpheep.back.util.Const;
/**
 * 
 * 分页参数
 *
 * @author Mryang
 *
 * @createDate 2017年10月8日 下午10:15:12
 *
 */
@Alias("PageParameter")
public class PageParameter<T> {
	//当前页码
    private Integer pageNumber = 1;
    //页面大小
    private Integer pageSize   = Const.PAGESIZE;
    //查询开始页
    private Integer start      = 0; 
    //参数对象
	private T parameter ;
	
	public PageParameter() {
		super();
	}
	public PageParameter(Integer pageNumber, Integer pageSize ) {
		super();
		if(null != pageNumber && pageNumber > 0){
			this.pageNumber = pageNumber;
		}
		if(null != pageSize   && pageSize >0  ){
			this.pageSize = pageSize;
		}
		this.start    = (this.pageNumber-1)*this.pageSize;
	}
	public PageParameter(Integer pageNumber, Integer pageSize,
			T parameter) {
		super();
		if(null != pageNumber && pageNumber > 0){
			this.pageNumber = pageNumber;
		}
		if(null != pageSize   && pageSize >0  ){
			this.pageSize = pageSize;
		}
		this.parameter = parameter;
		this.start    = (this.pageNumber-1)*this.pageSize;
	}
	public Integer getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(Integer pageNumber) {
		if(null != pageNumber && pageNumber > 0){
			this.pageNumber = pageNumber;
		}
		this.start    = (this.pageNumber-1)*this.pageSize;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		if(null != pageSize   && pageSize >0  ){
			this.pageSize = pageSize;
		}
		this.start    = (this.pageNumber-1)*this.pageSize;
	}
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public T getParameter() {
		return parameter;
	}
	public void setParameter(T parameter) {
		this.parameter = parameter;
	}
	
	
}