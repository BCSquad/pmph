package com.bc.pmpheep.back.plugin;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.type.Alias;

import com.bc.pmpheep.back.util.Const;
/**
 *@author MrYang 
 *@CreateDate 2017年9月19日 下午3:39:57
 *
 **/
@Alias("Page")
public class Page<R,P> {
	//当前页码
    private Integer pageNumber = 1;
    //页面大小
    private Integer pageSize   = Const.PAGESIZE;
    //数据总条数
    private Integer total      = 0;
    //总页数
    private Integer pageTotal  = 0;
    //是否是第一页
    private Boolean isFirst    = true;
    //是否是最后一页
    private Boolean isLast     = true;
    //查询开始页
    private Integer start      = 0; 
    //数据集
	private List<R> rows       = new ArrayList<R>(Const.PAGESIZE);
	//参数对象
	private P parameter ;
	
	public Page() {
		super();
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
		if(this.pageNumber==1){
			isFirst    = true;
		}else{
			isFirst    = false;
		}
		this.start=(this.pageNumber-1)*this.pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
		this.start    = (this.pageNumber-1)*this.pageSize;
		rows          = new ArrayList<R>(this.pageSize);
	}

	public void setTotal(Integer total) {
		this.total = total;
		if(this.total%this.pageSize==0){
			this.pageTotal=this.total/this.pageSize;
		}else{
			this.pageTotal=(this.total/this.pageSize)+1;
		}
		if(this.pageTotal==this.pageNumber){
			isLast     = true;
		}else{
			isLast     = false;
		}
	}

	public void setPageTotal(Integer pageTotal) {
		this.pageTotal = pageTotal;
	}

	public void setFirst(Boolean isFirst) {
		this.isFirst = isFirst;
	}

	public void setLast(Boolean isLast) {
		this.isLast = isLast;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public void setRows(List<R> rows) {
		this.rows = rows;
	}

	public void setParameter(P parameter) {
		this.parameter = parameter;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public Integer getTotal() {
		return total;
	}

	public Integer getPageTotal() {
		return pageTotal;
	}

	public Boolean isFirst() {
		return isFirst;
	}

	public Boolean isLast() {
		return isLast;
	}

	public Integer getStart() {
		return start;
	}

	public List<R> getRows() {
		return rows;
	}

	public P getParameter() {
		return parameter;
	}

	
	
	
}
