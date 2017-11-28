package com.bc.pmpheep.back.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.TextbookLog;

/**
 * TextbookLogDao 接口
 * @introduction
 *
 * @author Mryang
 *
 * @createDate 2017年11月27日 下午5:32:53
 *
 */
@Repository
public interface TextbookLogDao {

	/**
	 * 插入textbookLog
	 * @introduction 
	 * @author Mryang
	 * @createDate 2017年11月27日 下午5:33:10
	 * @param textbookLog
	 * @return
	 */
	Integer addTextbookLog(TextbookLog textbookLog) ;
	
	/**
	 * 根据书籍id和更新者分页查询日志总数
	 * @introduction 
	 * @author Mryang
	 * @createDate 2017年11月28日 下午2:04:18
	 * @param pageParameter
	 * @return
	 */
	Integer listTotalTextbookLogByTextBookId(PageParameter<Map<String,Object>> pageParameter) ;
	
	/**
	 * 根据书籍id和更新者分页查询日志
	 * @introduction 
	 * @author Mryang
	 * @createDate 2017年11月28日 下午2:04:22
	 * @param pageParameter
	 * @return
	 */
	List<TextbookLog> listTextbookLogByTextBookId (PageParameter<Map<String,Object>> pageParameter) ;
}
















