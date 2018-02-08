package com.bc.pmpheep.back.service;

import java.util.List;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.BookVedio;
import com.bc.pmpheep.back.vo.BookVedioVO;
import com.bc.pmpheep.service.exception.CheckedServiceException;





public interface BookVedioService {

	/**
	 * 动态更新
	 * @introduction 
	 * @author Mryang
	 * @createDate 2018年2月6日 下午5:10:58
	 * @param bookVedio
	 * @return
	 * @throws CheckedServiceException
	 */
	Integer updateBookVedio(BookVedio bookVedio) throws CheckedServiceException;
		
	/**
	 * 根据文件id删除BookVedio
	 * 2018年2月6日 上午11:11:36 
	 * @param id
	 * @return
	 */
	Integer deleteBookVedioByIds(List<Long> ids) throws CheckedServiceException;
	
	/**
	 * 获取书籍视频列表
	 * @introduction 
	 * @author Mryang
	 * @createDate 2018年2月6日 下午3:29:39
	 * @return
	 */
	PageResult<BookVedioVO>getList(Integer pageSize,Integer pageNumber,String bookName);
	
	

}