package com.bc.pmpheep.back.dao;

import org.springframework.stereotype.Repository;
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
}
