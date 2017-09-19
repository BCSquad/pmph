package com.bc.pmpheep.back.plugin;

import java.util.List;
import com.bc.pmpheep.back.util.Const;
/**
 *@author MrYang 
 *@CreateDate 2017年9月19日 下午3:39:57
 *
 **/

public class PageTest<T> {
	//当前页码
    private Integer pageNumber = Const.PAGENUMBER;
    //页面大小
    private Integer pageSize   = Const.PAGESIZE;
    //数据总条数
    private Integer total      = Const.PAGETOTAL;
    //总页数
    private Integer pageTotal  ;
    //是否是第一页
    private boolean isFirst;
    //是否是最后一页
    private boolean isLast;
    //数据集
	private List<T> rows;
	
	public PageTest() {
		super();
	}
	
	public Integer getPageNumber() {
		return pageNumber;
	}
	
	public boolean isFirst() {
		return isFirst;
	}

	public void setFirst(boolean isFirst) {
		this.isFirst = isFirst;
	}

	public boolean isLast() {
		return isLast;
	}

	public void setLast(boolean isLast) {
		this.isLast = isLast;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
		if(pageNumber!=null &&pageNumber == 1){
			isFirst=true;
		}
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageSize() {
		return pageSize;
	}
	
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total=total;
		if(null==total||total==0){
			this.pageTotal=0;
			return ;
		}
		if(total%pageSize == 0){
			this.total=total/this.pageSize;
			if(this.total==this.pageNumber){
				this.isLast=true;
			}
			return;
		}
		if(total%pageSize != 0){
			this.pageTotal = (total/this.pageSize)+1;
			if(this.total==this.pageNumber){
				this.isLast=true;
			}
			return ;
		}
	}
	public Integer getPageTotal() {
		return pageTotal;
	}
	public void setPageTotal(Integer pageTotal) {
		this.pageTotal=pageTotal;
	}
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	
}
